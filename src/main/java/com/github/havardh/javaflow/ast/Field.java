package com.github.havardh.javaflow.ast;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

import com.github.havardh.javaflow.model.CanonicalName;

public class Field {

  private final boolean isNullable;
  private final String name;
  private final Type type;

  public Field(boolean isNullable, String name, Type type) {
    this.isNullable = isNullable;
    this.type = requireNonNull(type);
    this.name = requireNonNull(name);
  }

  public Type getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public boolean isNullable() {
    return isNullable;
  }

  public String getPackageName() {
    return type.getPackageName();
  }

  public CanonicalName getCanonicalName() {
    return type.getCanonicalName();
  }


  @Override
  public String toString() {
    return format("%s: %s", name, getCanonicalName());
  }
}
