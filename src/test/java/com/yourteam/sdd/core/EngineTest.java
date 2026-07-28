package com.yourteam.sdd.core;

import com.yourteam.sdd.model.DuplicatePair;
import com.yourteam.sdd.model.MethodModel;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EngineTest {

    @Test
    public void testIdenticalSequenceLevenshteinScore() {
        List<String> tokensA = Arrays.asList("BlockStmt", "ExpressionStmt", "ReturnStmt");
        List<String> tokensB = Arrays.asList("BlockStmt", "ExpressionStmt", "ReturnStmt");

        MethodModel m1 = new MethodModel("methodA", "Test.java", 10, tokensA);
        MethodModel m2 = new MethodModel("methodB", "Test.java", 20, tokensB);

        SimilarityAlgorithm algorithm = new LevenshteinSimilarity();
        double score = algorithm.compare(m1, m2);

        // Identical structural sequences must yield a 100% match (1.0)
        assertEquals(1.0, score, 0.001);
    }

    @Test
    public void testDifferentSequenceLevenshteinScore() {
        List<String> tokensA = Arrays.asList("BlockStmt", "IfStmt", "ReturnStmt");
        List<String> tokensB = Arrays.asList("BlockStmt", "WhileStmt", "ExpressionStmt", "ReturnStmt");

        MethodModel m1 = new MethodModel("methodA", "Test.java", 10, tokensA);
        MethodModel m2 = new MethodModel("methodB", "Test.java", 20, tokensB);

        SimilarityAlgorithm algorithm = new LevenshteinSimilarity();
        double score = algorithm.compare(m1, m2);

        // Different structures should yield a score lower than 1.0
        assertTrue(score < 1.0);
        assertTrue(score >= 0.0);
    }

    @Test
    public void testDuplicateDetectorThresholdFiltering() {
        List<String> tokensA = Arrays.asList("BlockStmt", "ReturnStmt");
        List<String> tokensB = Arrays.asList("BlockStmt", "ReturnStmt");
        List<String> tokensC = Arrays.asList("BlockStmt", "IfStmt", "ForStmt", "ReturnStmt", "ThrowStmt");

        MethodModel m1 = new MethodModel("exactMatch1", "A.java", 1, tokensA);
        MethodModel m2 = new MethodModel("exactMatch2", "B.java", 1, tokensB);
        MethodModel m3 = new MethodModel("unrelated", "C.java", 1, tokensC);

        List<MethodModel> methods = Arrays.asList(m1, m2, m3);

        DuplicateDetector detector = new DuplicateDetector(new LevenshteinSimilarity());
        
        // Run with a strict 90% threshold
        List<DuplicatePair> results = detector.findDuplicates(methods, 0.90);

        // Should find exactly one pair (m1 and m2)
        assertEquals(1, results.size());
        assertEquals("exactMatch1", results.get(0).getFirst().getMethodName());
        assertEquals("exactMatch2", results.get(0).getSecond().getMethodName());
    }
}