package com.github.havardh.javaflow.model;

public class ModelWithNotMatchingGetterType {
  private int integerField;

  public String getIntegerField() {
    return "1";
  }
}

