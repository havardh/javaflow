package no.havard.javaflow.model.builders;

import java.util.ArrayList;
import java.util.List;

import no.havard.javaflow.model.CanonicalName;
import no.havard.javaflow.model.Enum;

public class EnumBuilder implements Builder<Enum> {

  private String packageName;
  private String name;
  private List<String> values = new ArrayList<>();

  private EnumBuilder() {
  }

  public static EnumBuilder enumBuilder() {
    return new EnumBuilder();
  }

  public EnumBuilder withPackageName(String packageName) {
    this.packageName = packageName;
    return this;
  }

  public EnumBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public EnumBuilder withEnumValue(String name) {
    this.values.add(name);
    return this;
  }

  public Enum build() {
    return new Enum(new CanonicalName(packageName, name), values);
  }

}