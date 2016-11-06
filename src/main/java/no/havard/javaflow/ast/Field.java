package no.havard.javaflow.ast;

import static no.havard.javaflow.phases.writer.flow.JavaFlowTypeConversion.toFlow;

public class Field {

  private final boolean isNullable;
  private final String name;
  private final Type type;

  public Field(boolean isNullable, String name, Type type) {
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

  public boolean isNullable() {
    return isNullable;
  }

  public String getPackageName() {
    return type.getPackageName();
  }

}
