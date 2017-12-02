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
  private final List<Field> fields;

  /**
   * Create a {@code Class} with the given name, optional parent and
   * list of member fields.
   *
   * @param name the name of the class
   * @param parent optional parent of the type
   * @param fields the list of member fields
   */
  public Class(CanonicalName name, Parent parent, List<Field> fields) {
    super(name);
    this.parent = parent;
    this.fields = fields;
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

  /**
   * Get the {@code Parent} reference
   *
   * @return optional parent reference
   */
  public Optional<Parent> getParent() {
    return Optional.ofNullable(parent);
  }
}
