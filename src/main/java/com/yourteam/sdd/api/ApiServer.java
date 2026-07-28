package com.yourteam.sdd.api;

import com.yourteam.sdd.core.AstMethodParser;
import com.yourteam.sdd.core.DuplicateDetector;
import com.yourteam.sdd.core.LevenshteinSimilarity;
import com.yourteam.sdd.core.ProjectScanner;
import com.yourteam.sdd.exceptions.InvalidProjectPathException;
import com.yourteam.sdd.exceptions.NoJavaFilesFoundException;
import com.yourteam.sdd.model.DuplicatePair;
import com.yourteam.sdd.model.MethodModel;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ApiServer {

    public static void main(String[] args) {
        // Simplified Javalin startup to resolve the version mismatch
        Javalin app = Javalin.create().start(7070);

        app.get("/health", ctx -> ctx.json(new HealthResponse("ok")));
        app.post("/scan", ApiServer::handleScan);
    }

    private static void handleScan(Context ctx) {
        try {
            ScanRequest req = ctx.bodyAsClass(ScanRequest.class);
            String path = req.path;
            double threshold = req.threshold > 0 ? req.threshold : 0.75;

            ProjectScanner scanner = new ProjectScanner();
            AstMethodParser parser = new AstMethodParser();
            DuplicateDetector detector = new DuplicateDetector(new LevenshteinSimilarity());

            List<File> javaFiles = scanner.scan(path);
            List<MethodModel> allMethods = parser.parseFiles(javaFiles);
            List<DuplicatePair> duplicates = detector.findDuplicates(allMethods, threshold);

            List<DuplicateResponse> duplicateResponses = new ArrayList<>();
            for (DuplicatePair pair : duplicates) {
                MethodModel first = pair.getFirst();
                MethodModel second = pair.getSecond();
                duplicateResponses.add(new DuplicateResponse(
                        first.getSignatureLabel(),
                        second.getSignatureLabel(),
                        pair.getSimilarityScore()
                ));
            }

            ctx.json(new ScanResponse(duplicateResponses));

        } catch (InvalidProjectPathException | NoJavaFilesFoundException e) {
            ctx.status(400).json(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(new ErrorResponse("Internal server error: " + e.getMessage()));
        }
    }

    // --- DTO Classes for JSON Serialization ---

    public static class ScanRequest {
        public String path;
        public double threshold;
    }

    public static class ScanResponse {
        public List<DuplicateResponse> duplicates;
        public ScanResponse(List<DuplicateResponse> duplicates) {
            this.duplicates = duplicates;
        }
    }

    public static class DuplicateResponse {
        public String methodA;
        public String methodB;
        public double score;

        public DuplicateResponse(String methodA, String methodB, double score) {
            this.methodA = methodA;
            this.methodB = methodB;
            this.score = score;
        }
    }

    public static class ErrorResponse {
        public String error;
        public ErrorResponse(String error) {
            this.error = error;
        }
    }

    public static class HealthResponse {
        public String status;
        public HealthResponse(String status) {
            this.status = status;
        }
    }
}