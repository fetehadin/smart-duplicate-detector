package com.yourteam.sdd.core;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.yourteam.sdd.model.MethodModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class AstMethodParser {

    // Added to handle batch parsing from the scanners
    public List<MethodModel> parseFiles(List<File> javaFiles) {
        List<MethodModel> allMethods = new ArrayList<>();
        for (File file : javaFiles) {
            try {
                allMethods.addAll(parse(file));
            } catch (FileNotFoundException e) {
                System.err.println("Warning: Could not read file " + file.getPath());
            }
        }
        return allMethods;
    }

    public List<MethodModel> parse(File javaFile) throws FileNotFoundException {
        List<MethodModel> extractedMethods = new ArrayList<>();
        
        CompilationUnit cu = StaticJavaParser.parse(javaFile);

        cu.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(MethodDeclaration method, Void arg) {
                super.visit(method, arg);
                
                String name = method.getNameAsString();
                int line = method.getBegin().isPresent() ? method.getBegin().get().line : -1;
                
                List<String> structureTokens = normalizeStructure(method);
                
                extractedMethods.add(new MethodModel(name, javaFile.getPath(), line, structureTokens));
            }
        }, null);

        return extractedMethods;
    }

    private List<String> normalizeStructure(MethodDeclaration method) {
        List<String> nodeTypes = new ArrayList<>();
        
        method.findAll(Node.class).forEach(node -> {
            nodeTypes.add(node.getClass().getSimpleName());
        });
        
        return nodeTypes;
    }
}