package sdd.exceptions;

/**
 * Thrown when the scanner finishes walking a valid folder but finds
 * no .java files anywhere inside it.
 */
public class NoJavaFilesFoundException extends Exception {

    public NoJavaFilesFoundException(String message) {
        super(message);
    }
}
