package com.github.havardh.javaflow.exceptions;

import static java.lang.String.format;

import com.github.havardh.javaflow.model.CanonicalName;

public class FieldGettersMismatchException extends RuntimeException {
  public FieldGettersMismatchException(CanonicalName className, String message) {
    super(format("Model {%s} is not a pure DTO.\n", className) + message);
  }
}

