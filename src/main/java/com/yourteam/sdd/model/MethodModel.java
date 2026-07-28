package com.yourteam.sdd.model;

import java.util.List;

public class MethodModel {
    private final String methodName;
    private final String filePath;
    private final int lineNumber;
    private final List<String> bodyTokens;

    public MethodModel(String methodName, String filePath, int lineNumber, List<String> bodyTokens) {
        this.methodName = methodName;
        this.filePath = filePath;
        this.lineNumber = lineNumber;
        this.bodyTokens = bodyTokens;
    }

    public String getMethodName() { return methodName; }
    public String getFilePath() { return filePath; }
    public int getLineNumber() { return lineNumber; }
    public List<String> getBodyTokens() { return bodyTokens; }

    // Restored for CLI and API output formatting
    public String getSignatureLabel() {
        return new java.io.File(filePath).getName() + ":" + lineNumber + " -> " + methodName;
    }
}