package com.github.havardh.javaflow.ast;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

import com.github.havardh.javaflow.model.CanonicalName;

/**
 * Internal representation of a member field
 */
public class Field {

  private final boolean isNullable;
  private final boolean isIgnored;
  private final String name;
  private final Type type;

  /**
   * Create a {@code Field} with the name, type and flag for nullable
   *
   * @param isNullable flag which is true when the type is nullable
   * @param name the name of the field
   * @param type the type of the field
   */
  public Field(boolean isNullable, boolean isIgnored, String name, Type type) {
    this.isNullable = isNullable;
    this.isIgnored = isIgnored;
    this.type = requireNonNull(type);
    this.name = requireNonNull(name);
  }

  /**
   * Get the type of the field
   *
   * @return the type of the field
   */
  public Type getType() {
    return type;
  }

  /**
   * Get the name of the field
   *
   * @return the name of the field
   */
  public String getName() {
    return name;
  }

  /**
   * Check if the field is nullable
   *
   * @return true if the field is nullable
   */
  public boolean isNullable() {
    return isNullable;
  }

  /**
   * Check if the field is ignored by json serialization
   *
   * @return true if the field is ignored
   */
  public boolean isIgnored() {
    return isIgnored;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return format("%s: %s", name, type.getCanonicalName());
  }
}
