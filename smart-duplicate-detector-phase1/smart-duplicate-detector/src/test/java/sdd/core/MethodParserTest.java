package sdd.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import sdd.model.MethodModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MethodParserTest {

    private final MethodParser parser = new MethodParser();

    @TempDir
    Path tempDir;

    @Test
    void findsSingleMethodWithCorrectNameAndLine() throws IOException {
        String source = """
                package com.example;

                public class Foo {
                    public int add(int a, int b) {
                        return a + b;
                    }
                }
                """;
        Path file = writeFile("Foo.java", source);

        List<MethodModel> methods = parser.parse(file.toFile());

        assertEquals(1, methods.size());
        assertEquals("add", methods.get(0).getMethodName());
        assertEquals(4, methods.get(0).getLineNumber());
    }

    @Test
    void findsMultipleMethodsInOneFile() throws IOException {
        String source = """
                public class Foo {
                    public void first() {
                        int x = 1;
                    }

                    public void second() {
                        int y = 2;
                    }
                }
                """;
        Path file = writeFile("Foo.java", source);

        List<MethodModel> methods = parser.parse(file.toFile());

        assertEquals(2, methods.size());
    }

    @Test
    void doesNotMistakeIfForAMethod() throws IOException {
        String source = """
                public class Foo {
                    public void run() {
                        if (true) {
                            System.out.println("hi");
                        }
                    }
                }
                """;
        Path file = writeFile("Foo.java", source);

        List<MethodModel> methods = parser.parse(file.toFile());

        assertEquals(1, methods.size());
        assertEquals("run", methods.get(0).getMethodName());
    }

    @Test
    void bodyTokensDoNotIncludeTheSignatureLine() throws IOException {
        String source = """
                public class Foo {
                    public void run() {
                        int x = 1;
                    }
                }
                """;
        Path file = writeFile("Foo.java", source);

        List<MethodModel> methods = parser.parse(file.toFile());

        assertFalse(methods.get(0).getBodyTokens().contains("run"));
    }

    private Path writeFile(String name, String content) throws IOException {
        Path file = tempDir.resolve(name);
        Files.writeString(file, content);
        return file;
    }
}
