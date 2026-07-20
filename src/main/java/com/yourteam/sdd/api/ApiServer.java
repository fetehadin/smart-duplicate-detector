package com.yourteam.sdd.api;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.yourteam.sdd.core.DuplicateDetector;
import com.yourteam.sdd.core.JaccardSimilarity;
import com.yourteam.sdd.core.MethodParser;
import com.yourteam.sdd.core.ProjectScanner;
import com.yourteam.sdd.exceptions.InvalidProjectPathException;
import com.yourteam.sdd.exceptions.NoJavaFilesFoundException;
import com.yourteam.sdd.model.DuplicatePair;
import com.yourteam.sdd.model.MethodModel;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

/**
 * Starts the HTTP API and static web interface for duplicate detection.
 */
public final class ApiServer {

    private static final int PORT = 7070;
    private static final double DEFAULT_THRESHOLD = 0.75;

    private ApiServer() {
    }

    /**
     * Starts the server on port 7000.
     *
     * @param args command-line arguments, currently unused
     */
    public static void main(String[] args) {
        createApp().start(PORT);
    }

    static Javalin createApp() {
        Javalin app = Javalin.create(config ->
                config.staticFiles.add("/public", Location.CLASSPATH));

        app.get("/api/health", ctx -> ctx.json(new HealthResponse("ok")));
        app.post("/api/scan", ctx -> {
            ScanRequest request = ctx.bodyAsClass(ScanRequest.class);
            ctx.json(scan(request));
        });

        app.exception(InvalidProjectPathException.class,
                (exception, ctx) -> ctx.status(400).json(new ErrorResponse(exception.getMessage())));
        app.exception(NoJavaFilesFoundException.class,
                (exception, ctx) -> ctx.status(400).json(new ErrorResponse(exception.getMessage())));
        app.exception(IllegalArgumentException.class,
                (exception, ctx) -> ctx.status(400).json(new ErrorResponse(exception.getMessage())));
        app.exception(Exception.class,
                (exception, ctx) -> ctx.status(500).json(new ErrorResponse("Unable to scan the project.")));

        return app;
    }

    private static ScanResponse scan(ScanRequest request)
            throws InvalidProjectPathException, NoJavaFilesFoundException {
        if (request == null || request.path() == null || request.path().isBlank()) {
            throw new IllegalArgumentException("path is required");
        }

        double threshold = request.threshold() == null ? DEFAULT_THRESHOLD : request.threshold();
        ProjectScanner scanner = new ProjectScanner();
        MethodParser parser = new MethodParser();
        DuplicateDetector detector = new DuplicateDetector(new JaccardSimilarity(), threshold);

        List<Path> files = scanner.scan(request.path());
        List<MethodModel> methods = parser.parseFiles(files);
        List<DuplicatePair> duplicates = detector.findDuplicates(methods);

        Set<String> distinctFiles = new HashSet<>();
        methods.forEach(method -> distinctFiles.add(method.getFilePath()));

        List<DuplicateResponse> results = duplicates.stream()
                .map(ApiServer::toResponse)
                .toList();

        return new ScanResponse(request.path(), methods.size(), distinctFiles.size(), results);
    }

    private static DuplicateResponse toResponse(DuplicatePair pair) {
        MethodModel first = pair.getFirst();
        MethodModel second = pair.getSecond();
        return new DuplicateResponse(
                first.getSignatureLabel(), first.getFilePath(), first.getLineNumber(),
                second.getSignatureLabel(), second.getFilePath(), second.getLineNumber(),
                pair.getSimilarityPercent());
    }

    private record ScanRequest(String path, Double threshold) {
    }

    private record ScanResponse(String path, int methodCount, int fileCount,
                                List<DuplicateResponse> duplicates) {
    }

    private record DuplicateResponse(String firstMethod, String firstFile, int firstLine,
                                     String secondMethod, String secondFile, int secondLine,
                                     int similarityPercent) {
    }

    private record HealthResponse(String status) {
    }

    private record ErrorResponse(String error) {
    }
}
