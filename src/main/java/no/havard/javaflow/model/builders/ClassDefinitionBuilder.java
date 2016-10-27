package no.havard.javaflow.model.builders;

import static no.havard.javaflow.model.Type.list;
import static no.havard.javaflow.model.Type.map;
import static no.havard.javaflow.model.Type.object;
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
import no.havard.javaflow.model.Type;

public class ClassDefinitionBuilder implements Builder<ClassDefinition> {

  private static Map<String, String> BUILTIN = Collections.unmodifiableMap(Stream.of(
      entry("Boolean", "java.lang"),
      entry("Byte", "java.lang"),
      entry("Character", "java.lang"),
      entry("Double", "java.lang"),
      entry("Float", "java.lang"),
      entry("Integer", "java.lang"),
      entry("Long", "java.lang"),
      entry("Short", "java.lang"),
      entry("String", "java.lang")
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

  public ClassDefinitionBuilder withField(com.github.javaparser.ast.type.Type type, String name) {
    Type t = of(type);
    String packageName = resolvePackageName(t.getName());

    this.fields.add(new FieldDefinition(packageName, t, name));
    return this;
  }

  public ClassDefinition build() {
    if (parent == null) {
      return new ClassDefinition(packageName, name, fields);
    } else {
      return new ClassDefinition(packageName, name, resolvePackageName(parent), parent, fields);
    }
  }

  private String resolvePackageName(String type) {
    return imports.getOrDefault(type, BUILTIN.getOrDefault(type, this.packageName));
  }

  public Type of(com.github.javaparser.ast.type.Type type) {
    String typeLiteral = type.toString();

    if (isList(typeLiteral)) {
      String valueType = extractType(typeLiteral);
      return list("Array", resolvePackageName(valueType) + "." + valueType);
    }

    if (isMap(typeLiteral)) {
      String keyType = extractKeyType(typeLiteral);
      String valType = extractValueType(typeLiteral);

      return map(
          "Map",
          resolvePackageName(keyType) + "." + keyType,
          resolvePackageName(valType) + "." + valType
      );
    }

    return object(typeLiteral);
  }

  private static boolean isList(String typeLiteral) {
    return typeLiteral.startsWith("List<") && typeLiteral.endsWith(">");
  }

  private static boolean isMap(String typeLiteral) {
    return typeLiteral.startsWith("Map<") && typeLiteral.endsWith(">");
  }

  private static String extractType(String typeLiteral) {
    return typeLiteral.substring(5, typeLiteral.length() - 1);
  }

  private static String extractValueType(String typeLiteral) {
    int index = typeLiteral.indexOf(",");
    return typeLiteral.substring(4, index);
  }

  private static String extractKeyType(String typeLiteral) {
    int index = typeLiteral.indexOf(",");
    return typeLiteral.substring(index+2, typeLiteral.length() - 1);
  }
}
