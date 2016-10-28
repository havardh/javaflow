package no.havard.javaflow.model.builders;

import static no.havard.javaflow.phases.reader.java.TypeFactory.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.havard.javaflow.model.CanonicalName;
import no.havard.javaflow.model.ClassDefinition;
import no.havard.javaflow.model.FieldDefinition;

import com.github.javaparser.ast.type.Type;

public class ClassDefinitionBuilder implements Builder<ClassDefinition> {

  private String packageName;
  private String name;
  private String parent;
  private List<FieldDefinition> fields = new ArrayList<>();
  private Map<String, String> imports = new HashMap<>();

  private ClassDefinitionBuilder() {
  }

  public static ClassDefinitionBuilder classDefinitionBuilder() {
    return new ClassDefinitionBuilder();
  }

  public ClassDefinitionBuilder withPackageName(String packageName) {
    this.packageName = packageName;
    return this;
  }

  public ClassDefinitionBuilder withImport(String name, String packageName) {
    imports.put(name, packageName);
    return this;
  }

  public ClassDefinitionBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public ClassDefinitionBuilder withParent(String parent) {
    this.parent = parent;
    return this;
  }

  public ClassDefinitionBuilder withField(boolean isNullable, Type type, String name) {
    this.fields.add(new FieldDefinition(
        isNullable,
        name,
        factory(packageName, imports).of(type)
    ));
    return this;
  }

  public ClassDefinition build() {
    if (parent == null) {
      return new ClassDefinition(
          new CanonicalName(packageName, name),
          fields
      );
    } else {
      return new ClassDefinition(
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
