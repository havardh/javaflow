package no.havard.javaflow.ast.builders;

import static no.havard.javaflow.model.CanonicalName.object;

import java.util.ArrayList;
import java.util.List;

import no.havard.javaflow.ast.Class;
import no.havard.javaflow.ast.Field;
import no.havard.javaflow.ast.Parent;

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
    return new Class(object(packageName, name), parent, fields);
  }

}
