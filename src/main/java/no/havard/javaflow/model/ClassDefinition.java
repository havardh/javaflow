package no.havard.javaflow.model;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

import java.util.List;

public class ClassDefinition extends Definition {

  private final List<FieldDefinition> fieldDefinitions;

  public ClassDefinition(String name, List<FieldDefinition> definitions) {
    super(name);
    this.fieldDefinitions = definitions;
  }

  public List<FieldDefinition> getFieldDefinitions() {
    return fieldDefinitions;
  }

  @Override
  public String toString() {
    return format("export type %s {\n  %s,\n};", this.name, fieldDefinitions.stream().map(FieldDefinition::toString)
        .collect
        (joining(",\n  ")));
  }
}
