package com.yourteam.sdd.core;

import com.yourteam.sdd.exceptions.InvalidProjectPathException;
import com.yourteam.sdd.exceptions.NoJavaFilesFoundException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProjectScanner {
    
    public List<File> scan(String rootPath) throws InvalidProjectPathException, NoJavaFilesFoundException {
        File root = new File(rootPath);
        if (!root.exists() || !root.isDirectory()) {
            throw new InvalidProjectPathException("Invalid project path: " + rootPath);
        }
        
        List<File> javaFiles = new ArrayList<>();
        findJavaFiles(root, javaFiles);
        
        if (javaFiles.isEmpty()) {
            throw new NoJavaFilesFoundException("No .java files found in " + rootPath);
        }
        
        return javaFiles;
    }

    private void findJavaFiles(File dir, List<File> javaFiles) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    findJavaFiles(file, javaFiles);
                } else if (file.getName().endsWith(".java")) {
                    javaFiles.add(file);
                }
            }
        }
    }
}