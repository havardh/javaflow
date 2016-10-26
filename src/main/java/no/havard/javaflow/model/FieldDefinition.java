package no.havard.javaflow.model;

import static java.lang.String.format;

import no.havard.javaflow.convertion.JavaFlowTypeConversion;

public class FieldDefinition {

  private final String packageName;
  private final Type type;
  private final String name;

  public FieldDefinition(String packageName, Type type, String name) {
    this.packageName = packageName;
    this.type = type;
    this.name = name;
  }

  public String getType() {
    return type.toString();
  }

  public String getName() {
    return name;
  }

  public String getCanonicalName() {
    return format("%s.%s", packageName, type);
  }

  @Override
  public String toString() {
    return format("%s: %s", name, JavaFlowTypeConversion.toFlow(getCanonicalName(), getType()));
  }

  public String getPackageName() {
    return packageName;
  }
}
