package sdd.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import sdd.exceptions.InvalidProjectPathException;
import sdd.exceptions.NoJavaFilesFoundException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectScannerTest {

    private final ProjectScanner scanner = new ProjectScanner();

    @TempDir
    Path tempDir;

    @Test
    void throwsOnNullPath() {
        assertThrows(InvalidProjectPathException.class, () -> scanner.scan(null));
    }

    @Test
    void throwsOnBlankPath() {
        assertThrows(InvalidProjectPathException.class, () -> scanner.scan("   "));
    }

    @Test
    void throwsOnNonExistentPath() {
        assertThrows(InvalidProjectPathException.class, () -> scanner.scan("/no/such/path/here"));
    }

    @Test
    void throwsWhenFolderHasNoJavaFiles() throws IOException {
        Files.createFile(tempDir.resolve("notes.txt"));

        assertThrows(NoJavaFilesFoundException.class, () -> scanner.scan(tempDir.toString()));
    }

    @Test
    void findsJavaFilesRecursively() throws Exception {
        Files.createFile(tempDir.resolve("Top.java"));
        Path sub = Files.createDirectory(tempDir.resolve("sub"));
        Files.createFile(sub.resolve("Nested.java"));
        Files.createFile(tempDir.resolve("ignored.txt"));

        List<File> found = scanner.scan(tempDir.toString());

        assertEquals(2, found.size());
    }
}
