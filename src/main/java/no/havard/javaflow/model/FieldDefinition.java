package no.havard.javaflow.model;

import static java.lang.String.format;

import no.havard.javaflow.convertion.JavaFlowTypeConversion;

public class FieldDefinition {

  private final String name;
  private final Type type;

  public FieldDefinition(String name, Type type) {
    this.type = type;
    this.name = name;
  }

  public Type getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public String getFlowType() {
    return JavaFlowTypeConversion.toFlow(type.getCanonicalName(), type.getName());
  }

  public String getPackageName() {
    return type.getPackageName();
  }

  @Override
  public String toString() {
    return format("%s: %s", name, getType());
  }
}
