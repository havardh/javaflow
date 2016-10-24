package no.havard.javaflow.model.builders;

import static no.havard.javaflow.util.Maps.entriesToMap;
import static no.havard.javaflow.util.Maps.entry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import no.havard.javaflow.model.ClassDefinition;
import no.havard.javaflow.model.FieldDefinition;

public class ClassDefinitionBuilder implements Builder<ClassDefinition> {

  private static Map<String, String> BUILTIN = Collections.unmodifiableMap(Stream.of(
      entry("String", "java.util")
  ).collect(entriesToMap()));

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

  public ClassDefinitionBuilder withField(String type, String name) {
    String packageName = resolvePackageName(type);
    this.fields.add(new FieldDefinition(packageName, type, name));
    return this;
  }

  public ClassDefinition build() {
    if (parent == null) {
      return new ClassDefinition(packageName, name, fields);
    } else {
      return new ClassDefinition(packageName, name, parent, fields);
    }
  }

  private String resolvePackageName(String type) {
    return imports.getOrDefault(type, BUILTIN.getOrDefault(type, this.packageName));
  }
}
