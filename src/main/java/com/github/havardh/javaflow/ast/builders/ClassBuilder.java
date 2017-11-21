package com.github.havardh.javaflow.ast.builders;

import java.util.ArrayList;
import java.util.List;

import com.github.havardh.javaflow.ast.Class;
import com.github.havardh.javaflow.ast.Field;
import com.github.havardh.javaflow.ast.Parent;
import com.github.havardh.javaflow.model.CanonicalName;

public class ClassBuilder implements Builder<Class> {

  private String packageName;
  private String name;
  private Parent parent;
  private List<Field> fields = new ArrayList<>();

  private ClassBuilder() {
  }

  public static ClassBuilder classBuilder() {
    return new ClassBuilder();
  }

  public ClassBuilder withPackageName(String packageName) {
    this.packageName = packageName;
    return this;
  }

  public ClassBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public ClassBuilder withParent(Parent parent) {
    this.parent = parent;
    return this;
  }

  public ClassBuilder withField(Field field) {
    this.fields.add(field);
    return this;
  }

  public Class build() {
    return new Class(CanonicalName.object(packageName, name), parent, fields);
  }

}
