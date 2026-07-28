package com.yourteam.sdd.core;

import com.yourteam.sdd.model.MethodModel;

/**
 * The interface for scoring method similarity.
 */
public interface SimilarityAlgorithm {
    double compare(MethodModel a, MethodModel b);
}