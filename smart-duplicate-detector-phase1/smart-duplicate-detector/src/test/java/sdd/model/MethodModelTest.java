package sdd.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MethodModelTest {

    @Test
    void gettersReturnValuesPassedToConstructor() {
        List<String> tokens = List.of("if", "(", "x", ")", "{", "}");
        MethodModel model = new MethodModel("doSomething", "Foo.java", 10, tokens);

        assertEquals("doSomething", model.getMethodName());
        assertEquals("Foo.java", model.getFilePath());
        assertEquals(10, model.getLineNumber());
        assertEquals(tokens, model.getBodyTokens());
    }

    @Test
    void bodyTokensListCannotBeModifiedAfterConstruction() {
        MethodModel model = new MethodModel("foo", "Foo.java", 1, List.of("a", "b"));

        assertThrows(UnsupportedOperationException.class, () -> model.getBodyTokens().add("c"));
    }
}
