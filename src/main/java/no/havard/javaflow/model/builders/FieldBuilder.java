package no.havard.javaflow.model.builders;

import no.havard.javaflow.model.Field;
import no.havard.javaflow.model.Type;

public final class FieldBuilder {
  private boolean isNullable;
  private String name;
  private Type type;

  private FieldBuilder() {
  }

  public static FieldBuilder fieldBuilder() {
    return new FieldBuilder();
  }

  public FieldBuilder withIsNullable(boolean isNullable) {
    this.isNullable = isNullable;
    return this;
  }

  public FieldBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public FieldBuilder withType(Type type) {
    this.type = type;
    return this;
  }

  public Field build() {
    return new Field(isNullable, name, type);
  }
}

