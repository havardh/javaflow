package no.havard.javaflow.model;

import static java.lang.String.format;

import static no.havard.javaflow.JavaFlowTypeConversion.toFlow;

public class FieldDefinition {

  private final boolean isNullable;
  private final String name;
  private final Type type;

  public FieldDefinition(boolean isNullable, String name, Type type) {
    this.isNullable = isNullable;
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
    return toFlow(type.getCanonicalName());
  }

  public String getPackageName() {
    return type.getPackageName();
  }

  @Override
  public String toString() {
    return format(
        "%s: %s%s",
        name,
        isNullable ? "?" : "",
        getType()
    );
  }
}
