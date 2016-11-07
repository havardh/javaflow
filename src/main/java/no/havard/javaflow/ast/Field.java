package no.havard.javaflow.ast;

import no.havard.javaflow.model.CanonicalName;

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

  public boolean isNullable() {
    return isNullable;
  }

  public String getPackageName() {
    return type.getPackageName();
  }

  public CanonicalName getCanonicalName() {
    return type.getCanonicalName();
  }
}
