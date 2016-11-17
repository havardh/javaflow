package no.havard.javaflow.exceptions;

import static java.lang.String.format;

import java.util.List;
import java.util.Map;

import no.havard.javaflow.ast.Field;
import no.havard.javaflow.ast.Type;

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

