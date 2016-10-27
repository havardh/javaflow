package no.havard.javaflow.model;

public class Parent {

  private final CanonicalName name;
  private Definition reference;

  public Parent(CanonicalName name) {
    this.name = name;
  }

  public String getName() {
    return name.getName();
  }

  public String getCanonicalName() {
    return name.getCanonicalName();
  }

  public Definition getReference() {
    return reference;
  }

  public void setReference(Definition reference) {
    this.reference = reference;
  }
}
