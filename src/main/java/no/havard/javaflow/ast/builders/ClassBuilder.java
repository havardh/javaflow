package no.havard.javaflow.ast.builders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.havard.javaflow.model.CanonicalName;
import no.havard.javaflow.ast.Class;
import no.havard.javaflow.ast.Field;
import no.havard.javaflow.ast.Parent;

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
    CanonicalName canonicalName = new CanonicalName(packageName, name);
    CanonicalName parentCanonicalName = new CanonicalName(resolvePackageName(parent), parent);

    return new Class(
        canonicalName,
        parent != null ? new Parent(parentCanonicalName) : null,
        fields
    );
  }

  private String resolvePackageName(String type) {
    return imports.getOrDefault(type, this.packageName);
  }

}
