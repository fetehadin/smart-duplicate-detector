package com.yourteam.sdd.exceptions;

/**
 * Thrown when a scan of an otherwise valid directory turns up
 * zero .java files.
 */
public class NoJavaFilesFoundException extends Exception {

    public NoJavaFilesFoundException(String path) {
        super("No .java files found under: '" + path + "'.");
    }
}
