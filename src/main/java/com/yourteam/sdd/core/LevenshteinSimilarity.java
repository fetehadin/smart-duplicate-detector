package com.yourteam.sdd.core;

import com.yourteam.sdd.model.MethodModel;
import java.util.List;

public class LevenshteinSimilarity implements SimilarityAlgorithm {

    // Assign higher weights to control flow and logic nodes
    private int getNodeWeight(String nodeName) {
        switch (nodeName) {
            case "IfStmt":
            case "ForStmt":
            case "WhileStmt":
            case "DoStmt":
            case "SwitchStmt":
            case "TryStmt":
            case "CatchClause":
                return 10; // 10x penalty for core business logic mismatches
            case "MethodCallExpr":
                return 2;  // Slight bump, but shouldn't dilute control flow
            default:
                return 1;  // Standard declarations and boilerplate
        }
    }

    @Override
    public double compare(MethodModel m1, MethodModel m2) {
        List<String> tokens1 = m1.getBodyTokens();
        List<String> tokens2 = m2.getBodyTokens();

        int len1 = tokens1.size();
        int len2 = tokens2.size();

        if (len1 == 0 && len2 == 0) return 1.0;
        if (len1 == 0 || len2 == 0) return 0.0;

        int[][] dp = new int[len1 + 1][len2 + 1];

        // Initialize base cases using our node weights
        dp[0][0] = 0;
        for (int i = 1; i <= len1; i++) {
            dp[i][0] = dp[i - 1][0] + getNodeWeight(tokens1.get(i - 1));
        }
        for (int j = 1; j <= len2; j++) {
            dp[0][j] = dp[0][j - 1] + getNodeWeight(tokens2.get(j - 1));
        }

        // Compute the weighted matrix
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                String t1 = tokens1.get(i - 1);
                String t2 = tokens2.get(j - 1);

                int weight1 = getNodeWeight(t1);
                int weight2 = getNodeWeight(t2);

                // If tokens match, cost is 0. If they don't, penalty is based on the heaviest node
                int substitutionCost = t1.equals(t2) ? 0 : Math.max(weight1, weight2);

                int delete = dp[i - 1][j] + weight1;
                int insert = dp[i][j - 1] + weight2;
                int substitute = dp[i - 1][j - 1] + substitutionCost;

                dp[i][j] = Math.min(delete, Math.min(insert, substitute));
            }
        }

        // Calculate maximum possible weight to find the percentage
        int totalWeight1 = tokens1.stream().mapToInt(this::getNodeWeight).sum();
        int totalWeight2 = tokens2.stream().mapToInt(this::getNodeWeight).sum();
        int maxPossibleDistance = Math.max(totalWeight1, totalWeight2);

        int actualDistance = dp[len1][len2];
        double similarity = 1.0 - ((double) actualDistance / maxPossibleDistance);
        
        return Math.max(0.0, similarity);
    }
}