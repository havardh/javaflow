package com.github.havardh.javaflow.ast;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Optional;

import com.github.havardh.javaflow.model.CanonicalName;
import com.github.havardh.javaflow.util.Lists;

public class Class extends Type {

  private final Parent parent;
  private final List<Field> fields;

  public Class(CanonicalName name, Parent parent, List<Field> fields) {
    super(name);
    this.parent = parent;
    this.fields = fields;
  }

  public List<Field> getFields() {
    return Lists.concat(getParentFields(), fields);
  }

  private List<Field> getParentFields() {
    return  getParent().map(Parent::getFields).orElse(emptyList());
  }

  public Optional<Parent> getParent() {
    return Optional.ofNullable(parent);
  }
}
