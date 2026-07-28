package sdd.exceptions;

/**
 * Thrown when the folder path given to the scanner doesn't exist,
 * isn't a folder, or wasn't provided at all.
 */
public class InvalidProjectPathException extends Exception {

    public InvalidProjectPathException(String message) {
        super(message);
    }
}
