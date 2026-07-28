package com.yourteam.sdd.core;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.Statement; // NEW IMPORT
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.yourteam.sdd.model.MethodModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AstMethodParser {

    // Ignore methods with fewer than 3 actual statements (filters out simple getters/setters)
    private static final int MIN_STATEMENT_COUNT = 3;

    public List<MethodModel> parseFiles(List<File> javaFiles) {
        List<MethodModel> allMethods = new ArrayList<>();

        for (File file : javaFiles) {
            try {
                CompilationUnit cu = StaticJavaParser.parse(file);
                
                cu.accept(new VoidVisitorAdapter<Void>() {
                    @Override
                    public void visit(MethodDeclaration md, Void arg) {
                        super.visit(md, arg);
                        
                        // Count actual executable statements, not microscopic syntax nodes
                        int statementCount = md.findAll(Statement.class).size();

                        // FILTER: Only add methods that contain enough business logic
                        if (statementCount >= MIN_STATEMENT_COUNT) {
                            
                            List<String> tokens = new ArrayList<>();
                            md.walk(node -> tokens.add(node.getClass().getSimpleName()));

                            int lineNumber = md.getBegin().isPresent() ? md.getBegin().get().line : 0;
                            allMethods.add(new MethodModel(md.getNameAsString(), file.getName(), lineNumber, tokens));
                        }
                    }
                }, null);
                
            } catch (Exception e) {
                System.err.println("Could not parse file: " + file.getName());
            }
        }
        return allMethods;
    }
}