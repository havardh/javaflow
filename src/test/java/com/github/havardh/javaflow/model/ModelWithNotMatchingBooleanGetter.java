package com.github.havardh.javaflow.model;

public class ModelWithNotMatchingBooleanGetter {
  private boolean booleanField;

  public boolean isBooleanFields() {
    return booleanField;
  }
}

