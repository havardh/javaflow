package no.havard.javaflow.model;

import no.havard.javaflow.convertion.JavaFlowTypeConversion;

import com.github.javaparser.ast.type.Type;

public class FieldDefinition {

  private final String packageName;
  private final Type type;
  private final String name;

  public FieldDefinition(String packageName, Type type, String name) {
    this.packageName = packageName;
    this.type = type;
    this.name = name;
  }

  public Type getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return String.format("%s: %s", name, JavaFlowTypeConversion.toFlow(type));
  }

  public String getPackageName() {
    return packageName;
  }
}
