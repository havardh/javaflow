package no.havard.javaflow.model.builders;

import no.havard.javaflow.model.FieldDefinition;
import no.havard.javaflow.model.Type;

public final class FieldDefinitionBuilder {
  private boolean isNullable;
  private String name;
  private Type type;

  private FieldDefinitionBuilder() {
  }

  public static FieldDefinitionBuilder fieldDefinition() {
    return new FieldDefinitionBuilder();
  }

  public FieldDefinitionBuilder withIsNullable(boolean isNullable) {
    this.isNullable = isNullable;
    return this;
  }

  public FieldDefinitionBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public FieldDefinitionBuilder withType(Type type) {
    this.type = type;
    return this;
  }

  public FieldDefinition build() {
    return new FieldDefinition(isNullable, name, type);
  }
}

