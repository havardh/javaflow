package com.github.havardh.javaflow.ast;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Optional;

import com.github.havardh.javaflow.model.CanonicalName;
import com.github.havardh.javaflow.util.Lists;

/**
 * Internal representation of a class
 */
public class Class extends Type {

  private final Parent parent;
  private final List<Class> innerClasses;
  private final List<Field> fields;
  private final List<Method> getters;

  /**
   * Create a {@code Class} with the given name, optional parent and
   * list of member fields.
   *
   * @param name the name of the class
   * @param parent optional parent of the type
   * @param innerClasses the list of inner classes
   * @param fields the list of member fields
   * @param getters the list of getters in the class
   */
  public Class(CanonicalName name, Parent parent, List<Class> innerClasses, List<Field> fields, List<Method> getters) {
    super(name);
    this.parent = parent;
    this.innerClasses = innerClasses;
    this.fields = fields;
    this.getters = getters;
  }

  /**
   * Get list of {@code Class} which are inner classes
   * @return list of inner classes
   */
  public List<Class> getInnerClasses() {
    return innerClasses;
  }

  /**
   * Get the list of fields including the fields of all parents
   *
   * @return the list of all fields for the type
   */
  public List<Field> getFields() {
    return Lists.concat(getParentFields(), fields);
  }

  private List<Field> getParentFields() {
    return  getParent().map(Parent::getFields).orElse(emptyList());
  }

  private List<Method> getParentGetters() {
    return  getParent().map(Parent::getGetters).orElse(emptyList());
  }

  /**
   * Get the {@code Parent} reference
   *
   * @return optional parent reference
   */
  public Optional<Parent> getParent() {
    return Optional.ofNullable(parent);
  }

  /**
   * Get list of {@code Method} which are getters
   * @return list of getters
   */
  public List<Method> getGetters() {
    return Lists.concat(getParentGetters(), getters);
  }
}
