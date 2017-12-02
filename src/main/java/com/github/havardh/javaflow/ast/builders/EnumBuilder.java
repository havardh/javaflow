package com.github.havardh.javaflow.ast.builders;

import java.util.ArrayList;
import java.util.List;

import com.github.havardh.javaflow.ast.Enum;
import com.github.havardh.javaflow.model.CanonicalName;

/**
 * Builder for {@code Enum} objects.
 */
public class EnumBuilder implements Builder<Enum> {

  private String packageName;
  private String name;
  private List<String> values = new ArrayList<>();

  private EnumBuilder() {
  }

  /**
   * Create an empty {@code EnumBuilder}.
   *
   * @return an empty {@code EnumBuilder}
   */
  public static EnumBuilder enumBuilder() {
    return new EnumBuilder();
  }

  /**
   * Set the package name
   *
   * @param packageName the package name
   * @return the builder for method chaining
   */
  public EnumBuilder withPackageName(String packageName) {
    this.packageName = packageName;
    return this;
  }

  /**
   * Set the name
   *
   * @param name the name of the enum
   * @return the builder for method chaining
   */
  public EnumBuilder withName(String name) {
    this.name = name;
    return this;
  }

  /**
   * Add a valid enum value
   *
   * @param name the valid enum value to add
   * @return the builder for method chaining
   */
  public EnumBuilder withEnumValue(String name) {
    this.values.add(name);
    return this;
  }

  /**
   * Build a {@code Enum} with the current configuration
   *
   * @return the {@code Enum}
   */
  public Enum build() {
    return new Enum(CanonicalName.object(packageName, name), values);
  }

}