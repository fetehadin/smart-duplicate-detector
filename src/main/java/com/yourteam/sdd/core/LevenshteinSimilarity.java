package com.yourteam.sdd.core;

import com.yourteam.sdd.model.MethodModel;
import java.util.List;

/**
 * Compares two methods using the Levenshtein Distance algorithm.
 * Evaluates the exact sequence of AST node types.
 */
public class LevenshteinSimilarity implements SimilarityAlgorithm {

    @Override
    public double compare(MethodModel a, MethodModel b) {
        List<String> seqA = a.getBodyTokens();
        List<String> seqB = b.getBodyTokens();

        int lenA = seqA.size();
        int lenB = seqB.size();

        if (lenA == 0 && lenB == 0) {
            return 1.0; 
        }
        if (lenA == 0 || lenB == 0) {
            return 0.0;
        }

        int[][] dp = new int[lenA + 1][lenB + 1];

        for (int i = 0; i <= lenA; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= lenB; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= lenA; i++) {
            for (int j = 1; j <= lenB; j++) {
                int cost = seqA.get(i - 1).equals(seqB.get(j - 1)) ? 0 : 1;

                dp[i][j] = Math.min(
                    Math.min(
                        dp[i - 1][j] + 1,        // Deletion
                        dp[i][j - 1] + 1         // Insertion
                    ), 
                    dp[i - 1][j - 1] + cost      // Substitution
                );
            }
        }

        int distance = dp[lenA][lenB];
        int maxLen = Math.max(lenA, lenB);
        return 1.0 - ((double) distance / maxLen);
    }
}