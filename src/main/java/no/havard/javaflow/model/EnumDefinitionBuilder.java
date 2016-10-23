package no.havard.javaflow.model;

import java.util.ArrayList;
import java.util.List;

public class EnumDefinitionBuilder implements Builder<EnumDefinition> {

  private String name;
  private List<String> values = new ArrayList<>();

  private EnumDefinitionBuilder() {
  }

  public static EnumDefinitionBuilder enumDefinitionBuilder() {
    return new EnumDefinitionBuilder();
  }

  public EnumDefinitionBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public EnumDefinitionBuilder withEnumValue(String name) {
    this.values.add(name);
    return this;
  }

  public EnumDefinition build() {
    return new EnumDefinition(name, values);
  }

}