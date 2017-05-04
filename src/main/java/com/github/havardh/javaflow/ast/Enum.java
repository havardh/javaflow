package com.github.havardh.javaflow.ast;

import java.util.List;

import com.github.havardh.javaflow.model.CanonicalName;

public class Enum extends Type {

  private final List<String> values;

  public Enum(CanonicalName name, List<String> values) {
    super(name);
    this.values = values;
  }

  public List<String> getValues() {
    return values;
  }
}
