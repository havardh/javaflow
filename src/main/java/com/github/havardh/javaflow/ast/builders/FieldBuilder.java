package com.github.havardh.javaflow.ast.builders;

import com.github.havardh.javaflow.ast.Field;
import com.github.havardh.javaflow.ast.Type;

/**
 * Builder for {@code Field} objects
 */
public final class FieldBuilder {
  private boolean isNullable;
  private String name;
  private Type type;
  private boolean isIgnored;

  private FieldBuilder() {
  }

  /**
   * Create an empty field builder
   *
   * @return the empty field builder
   */
  public static FieldBuilder fieldBuilder() {
    return new FieldBuilder();
  }

  /**
   * Set the isNullable value
   *
   * @param isNullable set to true if the type is nullable
   * @return the builder for method chaining
   */
  public FieldBuilder withIsNullable(boolean isNullable) {
    this.isNullable = isNullable;
    return this;
  }

  /**
   * Set the isIgnored value
   *
   * @param isIgnored set to true if the field is ignored
   *
   * @return the builder for method chaining
   */
  public FieldBuilder withIsIgnored(boolean isIgnored) {
    this.isIgnored = isIgnored;
    return this;
  }

  /**
   * Set the name
   *
   * @param name the name of the field
   * @return the builder for method chaining
   */
  public FieldBuilder withName(String name) {
    this.name = name;
    return this;
  }

  /**
   * Set the type
   *
   * @param type the type of the field
   * @return the builder for method chaining
   */
  public FieldBuilder withType(Type type) {
    this.type = type;
    return this;
  }

  /**
   * Build a {@code Field} with the current configuration
   *
   * @return the {@code Field}
   */
  public Field build() {
    return new Field(isNullable, isIgnored, name, type);
  }
}

