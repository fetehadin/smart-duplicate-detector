package sdd.model;

/**
 * Represents two methods that were compared and found to be similar,
 * along with the score that was computed for that pair.
 * Built in Phase 2 by the DuplicateDetector; the model itself has no
 * detection logic of its own — it only carries the result.
 */
public final class DuplicatePair {

    private final MethodModel first;
    private final MethodModel second;
    private final double similarityScore; // stored as a fraction, 0.0 - 1.0

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

    /** Similarity as a fraction between 0.0 and 1.0. Multiply by 100 to display as a percentage. */
    public double getSimilarityScore() {
        return similarityScore;
    }

    @Override
    public String toString() {
        return String.format("%.1f%% similar: %s <-> %s", similarityScore * 100, first, second);
    }
}
