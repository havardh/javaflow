package com.github.havardh.javaflow.exceptions;

import static com.github.havardh.javaflow.testutil.Assertions.assertStringEqual;

import org.junit.jupiter.api.Test;

import com.github.havardh.javaflow.model.CanonicalName;

public class FieldGettersMismatchExceptionTest {

  private static CanonicalName CANONICAL_NAME = CanonicalName.fromString("com.github.havardh.javaflow.Type");

  @Test
  public void shouldPrintMessageWithMissingTypesDefinitions() {
    FieldGettersMismatchException exception = new FieldGettersMismatchException(CANONICAL_NAME, "an error messaage");

    assertStringEqual(
        exception.getMessage(),
        "Model {com.github.havardh.javaflow.Type} is not a pure DTO.",
        "an error messaage"
    );
  }
}
