package com.yourteam.sdd.core;

import com.yourteam.sdd.model.MethodModel;

import java.util.HashSet;
import java.util.Set;

/**
 * Jaccard index over each method's token set: |A ∩ B| / |A ∪ B|.
 * Simple, symmetric, and cheap enough to run all-pairs on a
 * few thousand methods.
 */
public class JaccardSimilarity implements SimilarityAlgorithm {

    @Override
    public double score(MethodModel a, MethodModel b) {
        Set<String> tokensA = a.getTokens();
        Set<String> tokensB = b.getTokens();

        if (tokensA.isEmpty() && tokensB.isEmpty()) {
            return 0.0;
        }

        Set<String> intersection = new HashSet<>(tokensA);
        intersection.retainAll(tokensB);

        Set<String> union = new HashSet<>(tokensA);
        union.addAll(tokensB);

        if (union.isEmpty()) {
            return 0.0;
        }

        return (double) intersection.size() / union.size();
    }
}
