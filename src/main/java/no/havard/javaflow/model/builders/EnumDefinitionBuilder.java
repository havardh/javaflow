package no.havard.javaflow.model.builders;

import java.util.ArrayList;
import java.util.List;

import no.havard.javaflow.model.CanonicalName;
import no.havard.javaflow.model.EnumDefinition;

public class EnumDefinitionBuilder implements Builder<EnumDefinition> {

  private String packageName;
  private String name;
  private List<String> values = new ArrayList<>();

  private EnumDefinitionBuilder() {
  }

  public static EnumDefinitionBuilder enumDefinitionBuilder() {
    return new EnumDefinitionBuilder();
  }

  public EnumDefinitionBuilder withPackageName(String packageName) {
    this.packageName = packageName;
    return this;
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
    return new EnumDefinition(new CanonicalName(packageName, name), values);
  }

}