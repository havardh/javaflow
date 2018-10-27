package com.github.havardh.javaflow.model;

public class ModelWithAnonymousClass {
  private Member field = new Member() {
    @Override
    public String toString() {
      return "Custom";
    }
  };

  public Member getField() {
    return field;
  }
}

