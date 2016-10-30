package no.havard.javaflow.model.builders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.havard.javaflow.model.CanonicalName;
import no.havard.javaflow.model.Class;
import no.havard.javaflow.model.Field;

public class ClassBuilder implements Builder<Class> {

  private String packageName;
  private String name;
  private String parent;
  private List<Field> fields = new ArrayList<>();
  private Map<String, String> imports = new HashMap<>();

  private ClassBuilder() {
  }

  public static ClassBuilder classBuilder() {
    return new ClassBuilder();
  }

  public ClassBuilder withPackageName(String packageName) {
    this.packageName = packageName;
    return this;
  }

  public ClassBuilder withImport(String name, String packageName) {
    imports.put(name, packageName);
    return this;
  }

  public ClassBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public ClassBuilder withParent(String parent) {
    this.parent = parent;
    return this;
  }

  public ClassBuilder withField(Field field) {
    this.fields.add(field);
    return this;
  }

  public Class build() {
    if (parent == null) {
      return new Class(
          new CanonicalName(packageName, name),
          fields
      );
    } else {
      return new Class(
          new CanonicalName(packageName, name),
          new CanonicalName(resolvePackageName(parent), parent),
          fields
      );
    }
  }

  private String resolvePackageName(String type) {
    return imports.getOrDefault(type, this.packageName);
  }

}
