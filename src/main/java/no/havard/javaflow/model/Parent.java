package no.havard.javaflow.model;

public class Parent {

  private final String name;
  private Definition reference;

  public Parent(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public Definition getReference() {
    return reference;
  }

  public void setReference(Definition reference) {
    this.reference = reference;
  }
}

