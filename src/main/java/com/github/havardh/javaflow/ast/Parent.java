package com.github.havardh.javaflow.ast;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import java.lang.*;
import java.util.List;

import com.github.havardh.javaflow.model.CanonicalName;

public class Parent extends Type {

  private Class reference;

  public Parent(CanonicalName name) {
    super(name);
  }

  public List<Field> getFields() {
    return ofNullable(reference).map(Class::getFields).orElse(emptyList());
  }

  public void setReference(Class reference) {
    this.reference = reference;
  }

  public Class getReference() {
    return reference;
  }

}
