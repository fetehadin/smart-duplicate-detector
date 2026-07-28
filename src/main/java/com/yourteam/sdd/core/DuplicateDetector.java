package com.yourteam.sdd.core;

import com.yourteam.sdd.model.DuplicatePair;
import com.yourteam.sdd.model.MethodModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Compares all methods against each other using a provided similarity algorithm
 * and flags pairs that meet or exceed a configurable threshold score.
 */
public class DuplicateDetector {

    private final SimilarityAlgorithm algorithm;

    public DuplicateDetector(SimilarityAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public List<DuplicatePair> findDuplicates(List<MethodModel> methods, double threshold) {
        List<DuplicatePair> duplicates = new ArrayList<>();

        int size = methods.size();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                MethodModel methodA = methods.get(i);
                MethodModel methodB = methods.get(j);

                if (methodA.getFilePath().equals(methodB.getFilePath()) && 
                    methodA.getLineNumber() == methodB.getLineNumber()) {
                    continue;
                }

                double score = algorithm.compare(methodA, methodB);

                if (score >= threshold) {
                    duplicates.add(new DuplicatePair(methodA, methodB, score));
                }
            }
        }

        return duplicates;
    }
}