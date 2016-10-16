package no.havard.javaflow.model;

import no.havard.javaflow.convertion.JavaFlowTypeConversion;

import com.github.javaparser.ast.type.Type;

public class FieldDefinition {

  private final Type type;
  private final String name;

  public FieldDefinition(Type type, String name) {
    this.type = type;
    this.name = name;
  }

  public Type getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return String.format("%s: %s", name, JavaFlowTypeConversion.toFlow(type));
  }
}
