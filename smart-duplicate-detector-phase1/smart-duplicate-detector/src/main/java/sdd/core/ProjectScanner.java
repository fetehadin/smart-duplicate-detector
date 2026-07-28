package sdd.core;

import sdd.exceptions.InvalidProjectPathException;
import sdd.exceptions.NoJavaFilesFoundException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Walks a project folder and finds every .java file inside it,
 * including subfolders. This is the first step of the pipeline —
 * it doesn't look inside the files, it only locates them.
 */
public class ProjectScanner {

    /**
     * @param rootPath path to the folder to scan
     * @return every .java file found, recursively
     * @throws InvalidProjectPathException if the path is empty, missing, or not a folder
     * @throws NoJavaFilesFoundException if the folder exists but contains no .java files
     */
    public List<File> scan(String rootPath) throws InvalidProjectPathException, NoJavaFilesFoundException {
        if (rootPath == null || rootPath.isBlank()) {
            throw new InvalidProjectPathException("Project path cannot be empty.");
        }

        File root = new File(rootPath);
        if (!root.exists()) {
            throw new InvalidProjectPathException("Path does not exist: " + rootPath);
        }
        if (!root.isDirectory()) {
            throw new InvalidProjectPathException("Path is not a folder: " + rootPath);
        }

        List<File> javaFiles = new ArrayList<>();
        collectJavaFiles(root, javaFiles);

        if (javaFiles.isEmpty()) {
            throw new NoJavaFilesFoundException("No .java files found under: " + rootPath);
        }

        return javaFiles;
    }

    private void collectJavaFiles(File folder, List<File> results) {
        File[] entries = folder.listFiles();
        if (entries == null) {
            return; // not readable, skip rather than crash the whole scan
        }
        for (File entry : entries) {
            if (entry.isDirectory()) {
                collectJavaFiles(entry, results);
            } else if (entry.getName().endsWith(".java")) {
                results.add(entry);
            }
        }
    }
}
