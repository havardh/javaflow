package com.github.havardh.javaflow.ast.builders;

import java.util.ArrayList;
import java.util.List;

import com.github.havardh.javaflow.ast.Class;
import com.github.havardh.javaflow.ast.Field;
import com.github.havardh.javaflow.ast.Parent;
import com.github.havardh.javaflow.model.CanonicalName;

/**
 * Builder for {@codet Class} objects.
 */
public class ClassBuilder implements Builder<Class> {

  private String packageName;
  private String name;
  private Parent parent;
  private List<Field> fields = new ArrayList<>();

  private ClassBuilder() {
  }

  /**
   * Create an empty {@code ClassBuilder}.
   *
   * @return an empty {@code ClassBuilder}
   */
  public static ClassBuilder classBuilder() {
    return new ClassBuilder();
  }

  /**
   * Set the package name
   *
   * @param packageName the package name
   * @return the builder for method chaining
   */
  public ClassBuilder withPackageName(String packageName) {
    this.packageName = packageName;
    return this;
  }

  /**
   * Set the name
   *
   * @param name the of the {@code Class}
   * @return the builder for method chaining
   */
  public ClassBuilder withName(String name) {
    this.name = name;
    return this;
  }

  /**
   * Set the parent link
   *
   * @param parent the parent link for the {@coda Class}
   * @return the builder for method chaining
   */
  public ClassBuilder withParent(Parent parent) {
    this.parent = parent;
    return this;
  }

  /**
   * Add a {@code Field} to class builder
   *
   * @param field a {@code Field}
   * @return the builder for method chaining
   */
  public ClassBuilder withField(Field field) {
    this.fields.add(field);
    return this;
  }

  /**
   * Build a {@code Class} with the builder configuration
   *
   * @return the {@code Class}
   */
  public Class build() {
    return new Class(CanonicalName.object(packageName, name), parent, fields);
  }

}
