package com.yourteam.sdd.core;

import com.yourteam.sdd.exceptions.InvalidProjectPathException;
import com.yourteam.sdd.exceptions.NoJavaFilesFoundException;
import com.yourteam.sdd.model.DuplicatePair;
import com.yourteam.sdd.model.MethodModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

    @Test
    public void testEndToEndPipelineWithPlantedDuplicates(@TempDir Path tempDir) throws IOException, InvalidProjectPathException, NoJavaFilesFoundException {
        // 1. Create two separate temporary Java files with structurally identical methods
        File fileA = tempDir.resolve("ServiceA.java").toFile();
        try (FileWriter writer = new FileWriter(fileA)) {
            writer.write(
                "public class ServiceA {\n" +
                "    public void processOrder() {\n" +
                "        int total = 100;\n" +
                "        if (total > 50) {\n" +
                "            System.out.println(total);\n" +
                "        }\n" +
                "    }\n" +
                "}\n"
            );
        }

        File fileB = tempDir.resolve("ServiceB.java").toFile();
        try (FileWriter writer = new FileWriter(fileB)) {
            writer.write(
                "public class ServiceB {\n" +
                "    public void handleTransaction() {\n" +
                "        int amount = 200;\n" +
                "        if (amount > 50) {\n" +
                "            System.out.println(amount);\n" +
                "        }\n" +
                "    }\n" +
                "}\n"
            );
        }

        // 2. Run the actual pipeline: Scanner -> Parser -> Detector
        ProjectScanner scanner = new ProjectScanner();
        AstMethodParser parser = new AstMethodParser();
        DuplicateDetector detector = new DuplicateDetector(new LevenshteinSimilarity());

        List<File> scannedFiles = scanner.scan(tempDir.toString());
        assertEquals(2, scannedFiles.size(), "Scanner should find both test java files.");

        List<MethodModel> parsedMethods = parser.parseFiles(scannedFiles);
        assertEquals(2, parsedMethods.size(), "Parser should extract 2 methods total.");

        // 3. Detect duplicates with a 75% structural threshold
        List<DuplicatePair> duplicates = detector.findDuplicates(parsedMethods, 0.75);

        // 4. Verify the integration successfully flagged the match across files
        assertFalse(duplicates.isEmpty(), "Engine should detect the structural duplicate between ServiceA and ServiceB.");
        
        DuplicatePair match = duplicates.get(0);
        assertTrue(match.getSimilarityScore() >= 0.75, "Similarity score should meet or exceed the threshold.");
    }
}