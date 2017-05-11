package com.github.havardh.javaflow.exceptions;

import static java.lang.String.format;

import java.util.List;
import java.util.Map;

import com.github.havardh.javaflow.ast.Field;
import com.github.havardh.javaflow.ast.Type;

/**
 * An exception type to be thrown if a type is used is not found in the
 * input model or in the context of JavaFlow.
 */
public class MissingTypeException extends RuntimeException {

  private final Map<Type, List<Field>> types;

  /**
   * Creates a missing type exception
   *
   * @param types a map from type to fields with missing types
   */
  public MissingTypeException(Map<Type, List<Field>> types) {
    super(format("Could not find types: %s", types));
    this.types = types;
  }

  /**
   * Get details of exception
   *
   * @return the map from type to fields with missing types
   */
  public Map<Type, List<Field>> getTypes() {
    return types;
  }
}

