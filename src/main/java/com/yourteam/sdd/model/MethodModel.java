package com.yourteam.sdd.model;

import java.util.Collections;
import java.util.Set;

/**
 * Immutable representation of a single method extracted from source.
 * The {@code tokens} set is the bag-of-tokens used for similarity
 * comparison (method body identifiers, literals, operators — whatever
 * MethodParser decides to extract).
 */
public final class MethodModel {

    private final String methodName;
    private final String className;
    private final String filePath;
    private final int lineNumber;
    private final Set<String> tokens;

    public MethodModel(String methodName, String className, String filePath,
                        int lineNumber, Set<String> tokens) {
        this.methodName = methodName;
        this.className = className;
        this.filePath = filePath;
        this.lineNumber = lineNumber;
        this.tokens = Collections.unmodifiableSet(tokens);
    }

    public String getMethodName() {
        return methodName;
    }

    public String getClassName() {
        return className;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public Set<String> getTokens() {
        return tokens;
    }

    /** e.g. "calculateDiscount(Order)" */
    public String getSignatureLabel() {
        return methodName;
    }

    @Override
    public String toString() {
        return className + "." + methodName + " (" + filePath + ":" + lineNumber + ")";
    }
}
