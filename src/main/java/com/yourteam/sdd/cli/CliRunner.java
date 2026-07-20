package com.yourteam.sdd.cli;

import com.yourteam.sdd.core.DuplicateDetector;
import com.yourteam.sdd.core.JaccardSimilarity;
import com.yourteam.sdd.core.MethodParser;
import com.yourteam.sdd.core.ProjectScanner;
import com.yourteam.sdd.exceptions.InvalidProjectPathException;
import com.yourteam.sdd.exceptions.NoJavaFilesFoundException;
import com.yourteam.sdd.model.DuplicatePair;
import com.yourteam.sdd.model.MethodModel;
import com.yourteam.sdd.report.ConsoleReport;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Usage:
 *   java -jar smart-duplicate-detector.jar --path ./path/to/project [--threshold 0.75]
 */
public class CliRunner {

    private static final double DEFAULT_THRESHOLD = 0.75;

    public static void main(String[] args) {
        String path = null;
        double threshold = DEFAULT_THRESHOLD;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--path" -> path = args[++i];
                case "--threshold" -> threshold = Double.parseDouble(args[++i]);
                default -> { /* ignore unknown flags for now */ }
            }
        }

        if (path == null) {
            System.err.println("Usage: --path <project-dir> [--threshold 0.0-1.0]");
            System.exit(1);
        }

        try {
            run(path, threshold);
        } catch (InvalidProjectPathException | NoJavaFilesFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private static void run(String path, double threshold)
            throws InvalidProjectPathException, NoJavaFilesFoundException {

        ProjectScanner scanner = new ProjectScanner();
        MethodParser parser = new MethodParser();
        DuplicateDetector detector = new DuplicateDetector(new JaccardSimilarity(), threshold);
        ConsoleReport report = new ConsoleReport();

        report.reportScanStart(path);

        List<Path> files = scanner.scan(path);
        List<MethodModel> methods = parser.parseFiles(files);

        Set<String> distinctFiles = new HashSet<>();
        methods.forEach(m -> distinctFiles.add(m.getFilePath()));

        report.reportMethodsFound(methods.size(), distinctFiles.size());

        List<DuplicatePair> duplicates = detector.findDuplicates(methods);
        report.reportDuplicates(duplicates);
    }
}
