package com.yourteam.sdd.core;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.yourteam.sdd.model.MethodModel;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Parses .java files with JavaParser and extracts every method
 * into a MethodModel, tokenizing each method body for later
 * similarity comparison.
 */
public class MethodParser {

    /**
     * Parses a single file and returns a MethodModel for every
     * method declared in it. Files that fail to parse are skipped
     * (logged to stderr) rather than aborting the whole scan.
     */
    public List<MethodModel> parseFile(Path javaFile) {
        List<MethodModel> results = new ArrayList<>();

        CompilationUnit cu;
        try {
            cu = StaticJavaParser.parse(javaFile);
        } catch (IOException | com.github.javaparser.ParseProblemException e) {
            System.err.println("Skipping unparseable file: " + javaFile + " (" + e.getMessage() + ")");
            return results;
        }

        List<MethodDeclaration> methods = cu.findAll(MethodDeclaration.class);

        for (MethodDeclaration method : methods) {
            String className = method.findAncestor(ClassOrInterfaceDeclaration.class)
                    .map(c -> c.getNameAsString())
                    .orElse("(anonymous)");

            int line = method.getBegin().map(p -> p.line).orElse(-1);

            Set<String> tokens = tokenize(method);

            results.add(new MethodModel(
                    method.getNameAsString(),
                    className,
                    javaFile.toString(),
                    line,
                    tokens
            ));
        }

        return results;
    }

    /**
     * Convenience overload for parsing several files at once.
     */
    public List<MethodModel> parseFiles(List<Path> javaFiles) {
        List<MethodModel> all = new ArrayList<>();
        for (Path file : javaFiles) {
            all.addAll(parseFile(file));
        }
        return all;
    }

    /**
     * Builds a bag-of-tokens for a method: parameter types, every
     * identifier referenced in the body, and every simple name used.
     * This is deliberately structure-light — Jaccard similarity over
     * this set catches renamed-but-equivalent logic, which is the
     * whole point of the tool.
     */
    private Set<String> tokenize(MethodDeclaration method) {
        Set<String> tokens = new HashSet<>();

        method.getParameters().forEach(p -> tokens.add("param:" + p.getType().asString()));
        tokens.add("returns:" + method.getType().asString());

        method.findAll(NameExpr.class).forEach(n -> tokens.add("id:" + n.getNameAsString()));
        method.findAll(SimpleName.class).forEach(n -> tokens.add("name:" + n.asString()));

        method.getBody().ifPresent(body ->
                body.findAll(com.github.javaparser.ast.expr.MethodCallExpr.class)
                        .forEach(call -> tokens.add("call:" + call.getNameAsString()))
        );

        return tokens;
    }
}
