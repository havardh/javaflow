package com.github.havardh.javaflow.ast;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import java.util.List;

import com.github.havardh.javaflow.model.CanonicalName;

/**
 * Internal representation of a parent link
 *
 * A {@code Parent} link is created during the parsing phase
 * with the canonical name of the referenced type. During the
 * {@code InheritanceTransformer} phase the {@code reference}
 * to the actual internal representation for the link is updated.
 */
public class Parent extends Type {

  private Class reference;

  /**
   * Create a parent link for a {@code CanonicalName}
   *
   * @param name the canonical name to refer to
   */
  public Parent(CanonicalName name) {
    super(name);
  }

  public List<Field> getFields() {
    return ofNullable(reference).map(Class::getFields).orElse(emptyList());
  }

  public List<Method> getGetters() {
    return ofNullable(reference).map(Class::getGetters).orElse(emptyList());
  }

  /**
   * Set reference
   *
   * @param reference the class of the type to reference
   */
  public void setReference(Class reference) {
    this.reference = reference;
  }

  /**
   * Get the {@code Class} referenced to
   *
   * @return the {@code Class} reference
   */
  public Class getReference() {
    return reference;
  }

}
