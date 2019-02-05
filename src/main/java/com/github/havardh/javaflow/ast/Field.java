package com.github.havardh.javaflow.ast;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

import com.github.havardh.javaflow.model.CanonicalName;

/**
 * Internal representation of a member field
 */
public class Field {

  private final boolean isNullable;
  private final boolean isIgnored;
  private final String jsonName;
  private final String name;
  private final Type type;

  /**
   * Create a {@code Field} with the name, type and flag for nullable
   *
   * @param isNullable flag which is true when the type is nullable
   * @param isIgnored flag which is true when the field is ignored
   * @param jsonName the json property name
   * @param name the name of the field
   * @param type the type of the field
   */
  public Field(boolean isNullable, boolean isIgnored, String jsonName, String name, Type type) {
    this.isNullable = isNullable;
    this.isIgnored = isIgnored;
    this.jsonName = jsonName;
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
   * Get the json property name of the field
   *
   * @return the json property nam of the field
   */
  public String getJsonName() {
    return jsonName;
  }

  /**
   * Get the name of the field as it would appear in json
   *
   * Gets the json overridden name if set and defaults to
   * the name of the field.
   *
   * @return the name of the field
   */
  public String getJsonNameOrName() {
    return ofNullable(jsonName).orElse(name);
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
