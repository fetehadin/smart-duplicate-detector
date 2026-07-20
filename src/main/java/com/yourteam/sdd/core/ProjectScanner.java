package com.yourteam.sdd.core;

import com.yourteam.sdd.exceptions.InvalidProjectPathException;
import com.yourteam.sdd.exceptions.NoJavaFilesFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Recursively finds all .java files under a given project root.
 */
public class ProjectScanner {

    /**
     * @param projectPath root directory to scan
     * @return list of .java file paths found under projectPath
     * @throws InvalidProjectPathException if the path doesn't exist or isn't a directory
     * @throws NoJavaFilesFoundException   if the scan finds zero .java files
     */
    public List<Path> scan(String projectPath)
            throws InvalidProjectPathException, NoJavaFilesFoundException {

        Path root = Paths.get(projectPath);

        if (!Files.exists(root) || !Files.isDirectory(root)) {
            throw new InvalidProjectPathException(projectPath);
        }

        List<Path> javaFiles;
        try (Stream<Path> walk = Files.walk(root)) {
            javaFiles = walk
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".java"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new InvalidProjectPathException(projectPath, e);
        }

        if (javaFiles.isEmpty()) {
            throw new NoJavaFilesFoundException(projectPath);
        }

        return javaFiles;
    }
}
