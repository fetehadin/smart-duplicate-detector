package com.yourteam.sdd.core;

import com.yourteam.sdd.model.DuplicatePair;
import com.yourteam.sdd.model.MethodModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Runs an all-pairs comparison over a list of methods using a
 * pluggable SimilarityAlgorithm and returns pairs whose score
 * meets or exceeds the given threshold.
 *
 * All-pairs is O(n^2); fine for a single project's method count,
 * but if this ever needs to scale to huge monorepos, bucket by
 * a cheap pre-filter (e.g. token-count range) before comparing.
 */
public class DuplicateDetector {

    private final SimilarityAlgorithm algorithm;
    private final double threshold;

    public DuplicateDetector(SimilarityAlgorithm algorithm, double threshold) {
        if (threshold < 0.0 || threshold > 1.0) {
            throw new IllegalArgumentException("threshold must be between 0.0 and 1.0");
        }
        this.algorithm = algorithm;
        this.threshold = threshold;
    }

    public List<DuplicatePair> findDuplicates(List<MethodModel> methods) {
        List<DuplicatePair> duplicates = new ArrayList<>();

        for (int i = 0; i < methods.size(); i++) {
            for (int j = i + 1; j < methods.size(); j++) {
                MethodModel a = methods.get(i);
                MethodModel b = methods.get(j);

                double score = algorithm.score(a, b);
                if (score >= threshold) {
                    duplicates.add(new DuplicatePair(a, b, score));
                }
            }
        }

        duplicates.sort((p1, p2) -> Double.compare(p2.getSimilarityScore(), p1.getSimilarityScore()));
        return duplicates;
    }
}
