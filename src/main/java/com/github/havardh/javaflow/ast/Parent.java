package com.github.havardh.javaflow.ast;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import java.lang.*;
import java.util.List;

import com.github.havardh.javaflow.model.CanonicalName;

public class Parent {

  private final CanonicalName name;
  private Class reference;

  public Parent(CanonicalName name) {
    this.name = name;
  }

  public String getName() {
    return name.getName();
  }

  public String getCanonicalName() {
    return name.getCanonicalName();
  }

  public Class getReference() {
    return reference;
  }

  public List<Field> getFields() {
    return ofNullable(reference).map(Class::getFields).orElse(emptyList());
  }

  public void setReference(Class reference) {
    this.reference = reference;
  }
}
