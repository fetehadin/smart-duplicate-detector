package com.yourteam.sdd.core;

import com.yourteam.sdd.model.MethodModel;

/**
 * Strategy interface for scoring how similar two methods are.
 * Implementations return a score in [0.0, 1.0], where 1.0 is
 * an exact token-set match.
 */
public interface SimilarityAlgorithm {

    double score(MethodModel a, MethodModel b);
}
