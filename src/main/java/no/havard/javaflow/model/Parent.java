package no.havard.javaflow.model;

import static java.lang.String.format;

public class Parent {

  private final String packageName;
  private final String name;
  private Definition reference;

  public Parent(String packageName, String name) {
    this.packageName = packageName;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getCanonicalName() {
    return format("%s.%s", packageName, name);
  }

  public Definition getReference() {
    return reference;
  }

  public void setReference(Definition reference) {
    this.reference = reference;
  }
}
