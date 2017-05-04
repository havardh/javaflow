package com.github.havardh.javaflow.exceptions;

import static java.lang.String.format;

import java.util.List;
import java.util.Map;

import com.github.havardh.javaflow.ast.Field;
import com.github.havardh.javaflow.ast.Type;

public class MissingTypeException extends RuntimeException {

  private final Map<Type, List<Field>> types;

  public MissingTypeException(Map<Type, List<Field>> types) {
    super(format("Could not find types: %s", types));
    this.types = types;
  }

  public Map<Type, List<Field>> getTypes() {
    return types;
  }
}

