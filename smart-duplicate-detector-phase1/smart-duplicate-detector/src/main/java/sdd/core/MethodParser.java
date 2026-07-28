package sdd.core;

import sdd.model.MethodModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extracts methods from a Java source file.
 *
 * This is a deliberately lightweight, fully self-written parser — it does
 * not use a parsing library. It recognizes the common, single-line method
 * signature style and pairs it with brace counting to find the method body.
 *
 * Known limitations (acceptable for this project's scope):
 *   - The method signature and its opening '{' must be on the same line.
 *   - Constructors are not currently detected (no return type before the name).
 *   - Highly unusual formatting (e.g. a signature split across many lines)
 *     will be skipped rather than mis-parsed.
 * These trade-offs keep the parser simple enough to fully explain, which
 * matters more for this project than handling every possible Java style.
 */
public class MethodParser {

    // Matches lines like:
    //   public double calculateDiscount(Order order) {
    //   private static void run(int a, String b) throws Exception {
    // Group 5 captures the method name.
    private static final Pattern METHOD_SIGNATURE = Pattern.compile(
            "^\\s*(public|private|protected)?\\s*(static\\s+)?(final\\s+)?(synchronized\\s+)?" +
                    "[\\w<>\\[\\],.]+\\s+(\\w+)\\s*\\([^)]*\\)\\s*(throws\\s+[\\w,\\s.]+)?\\s*\\{.*$"
    );

    private static final Pattern TOKEN_PATTERN = Pattern.compile(
            "[A-Za-z_$][A-Za-z0-9_$]*|\"[^\"]*\"|\\d+\\.?\\d*|[{}()\\[\\];,.]|[+\\-*/%=<>!&|^~]+"
    );

    /**
     * @param javaFile the source file to parse
     * @return every method found in the file, in the order they appear
     * @throws IOException if the file can't be read
     */
    public List<MethodModel> parse(File javaFile) throws IOException {
        List<String> lines = Files.readAllLines(javaFile.toPath());
        List<MethodModel> methods = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            Matcher matcher = METHOD_SIGNATURE.matcher(lines.get(i));
            if (!matcher.matches()) {
                continue;
            }

            String methodName = matcher.group(5);
            int declarationLine = i + 1; // humans count lines starting at 1

            String body = extractBody(lines, i);
            if (body == null) {
                continue; // braces never balanced — skip rather than guess
            }

            List<String> tokens = tokenize(body);
            methods.add(new MethodModel(methodName, javaFile.getPath(), declarationLine, tokens));
        }

        return methods;
    }

    /**
     * Given the line the method declaration starts on, reads forward until
     * the braces balance back to zero, and returns everything in between
     * (not including the declaration line itself).
     */
    private String extractBody(List<String> lines, int startLine) {
        StringBuilder body = new StringBuilder();
        int braceCount = 0;
        boolean started = false;

        for (int i = startLine; i < lines.size(); i++) {
            String line = lines.get(i);
            for (char c : line.toCharArray()) {
                if (c == '{') {
                    braceCount++;
                    started = true;
                } else if (c == '}') {
                    braceCount--;
                }
            }
            if (i > startLine) {
                body.append(line).append('\n');
            }
            if (started && braceCount == 0) {
                return body.toString();
            }
        }
        return null; // reached end of file without closing every brace
    }

    private List<String> tokenize(String body) {
        List<String> tokens = new ArrayList<>();
        Matcher matcher = TOKEN_PATTERN.matcher(body);
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        return tokens;
    }
}
