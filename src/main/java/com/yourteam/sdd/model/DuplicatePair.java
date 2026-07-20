package com.yourteam.sdd.model;

/**
 * A pair of methods whose similarity score met or exceeded the
 * detector's threshold, plus the score itself (0.0 - 1.0).
 */
public final class DuplicatePair {

    private final MethodModel first;
    private final MethodModel second;
    private final double similarityScore;

    public DuplicatePair(MethodModel first, MethodModel second, double similarityScore) {
        this.first = first;
        this.second = second;
        this.similarityScore = similarityScore;
    }

    public MethodModel getFirst() {
        return first;
    }

    public MethodModel getSecond() {
        return second;
    }

    public double getSimilarityScore() {
        return similarityScore;
    }

    public int getSimilarityPercent() {
        return (int) Math.round(similarityScore * 100);
    }

    @Override
    public String toString() {
        return String.format("DUPLICATE FOUND (%d%% similar)%n→ %-25s %s:%d%n→ %-25s %s:%d",
                getSimilarityPercent(),
                first.getSignatureLabel(), first.getFilePath(), first.getLineNumber(),
                second.getSignatureLabel(), second.getFilePath(), second.getLineNumber());
    }
}
