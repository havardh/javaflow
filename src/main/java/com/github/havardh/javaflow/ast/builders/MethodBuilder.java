package com.github.havardh.javaflow.ast.builders;

import com.github.havardh.javaflow.ast.Method;
import com.github.havardh.javaflow.ast.Type;

public class MethodBuilder implements Builder<Method> {

  private String name;
  private Type type;

  public static MethodBuilder methodBuilder() {
    return new MethodBuilder();
  }

  public MethodBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public MethodBuilder withType(Type type) {
    this.type = type;
    return this;
  }

  /**
   * Build a {@code Method} with the builder configuration
   *
   * @return the {@code Method}
   */
  @Override
  public Method build() {
    return new Method(name, type);
  }
}