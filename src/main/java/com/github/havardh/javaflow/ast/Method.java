package com.github.havardh.javaflow.ast;

public class Method {
  private final String name;
  private final Type type;

  public Method(String name, Type type) {
    this.name = name;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public Type getType() {
    return type;
  }
}
