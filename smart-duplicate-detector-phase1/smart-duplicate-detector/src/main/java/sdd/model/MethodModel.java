package sdd.model;

import java.util.Collections;
import java.util.List;

/**
 * Immutable representation of a single Java method found during a scan.
 * Once constructed, a MethodModel cannot be changed — this keeps every
 * part of the system that reads a method's data safe from accidental
 * mutation elsewhere.
 */
public final class MethodModel {

    private final String methodName;
    private final String filePath;
    private final int lineNumber;
    private final List<String> bodyTokens;

    public MethodModel(String methodName, String filePath, int lineNumber, List<String> bodyTokens) {
        this.methodName = methodName;
        this.filePath = filePath;
        this.lineNumber = lineNumber;
        this.bodyTokens = Collections.unmodifiableList(bodyTokens);
    }

    public String getMethodName() {
        return methodName;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * The tokenized method body (keywords, identifiers, symbols, literals),
     * in source order. Used later by the similarity scorer.
     */
    public List<String> getBodyTokens() {
        return bodyTokens;
    }

    @Override
    public String toString() {
        return methodName + "(" + filePath + ":" + lineNumber + ") [" + bodyTokens.size() + " tokens]";
    }
}
