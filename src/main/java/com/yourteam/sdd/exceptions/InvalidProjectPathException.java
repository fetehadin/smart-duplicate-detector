package com.yourteam.sdd.exceptions;

/**
 * Thrown when the path supplied to the scanner does not exist
 * or is not a directory.
 */
public class InvalidProjectPathException extends Exception {

    public InvalidProjectPathException(String path) {
        super("Invalid project path: '" + path + "' does not exist or is not a directory.");
    }

    public InvalidProjectPathException(String path, Throwable cause) {
        super("Invalid project path: '" + path + "' does not exist or is not a directory.", cause);
    }
}
