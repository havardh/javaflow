package no.havard.javaflow.convertion;

import static no.havard.javaflow.model.Type.list;
import static no.havard.javaflow.model.Type.map;
import static no.havard.javaflow.model.Type.object;
import static no.havard.javaflow.util.Maps.entriesToMap;
import static no.havard.javaflow.util.Maps.entry;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

import no.havard.javaflow.model.CanonicalName;
import no.havard.javaflow.model.Type;

import com.github.javaparser.ast.type.PrimitiveType;

public class TypeFactory {

  private static Map<String, String> BUILTIN = Collections.unmodifiableMap(Stream.of(
      entry("Boolean", "java.lang"),
      entry("Byte", "java.lang"),
      entry("Character", "java.lang"),
      entry("Double", "java.lang"),
      entry("Float", "java.lang"),
      entry("Integer", "java.lang"),
      entry("Long", "java.lang"),
      entry("Short", "java.lang"),
      entry("String", "java.lang"),

      entry("List", "java.util"),
      entry("Map", "java.util")
  ).collect(entriesToMap()));

  private final String packageName;
  private final Map<String, String> imports;

  private TypeFactory(String packageName, Map<String, String> imports) {
    this.packageName = packageName;
    this.imports = imports;
  }

  public static TypeFactory factory(
      String packageName,
      Map<String, String> imports
  ) {
    return new TypeFactory(packageName, imports);
  }

  private String resolvePackageName(String type) {
    return imports.getOrDefault(type, BUILTIN.getOrDefault(type, this.packageName));
  }

  public Type of(com.github.javaparser.ast.type.Type type) {
    String typeLiteral = type.toString();

    if (isList(typeLiteral)) {
      String tagType = extractTagType(typeLiteral);
      String valType = extractType(typeLiteral);
      return list(
          new CanonicalName(resolvePackageName(tagType), tagType),
          new CanonicalName(resolvePackageName(valType),  valType)
      );
    }

    if (isMap(typeLiteral)) {
      String tagType = extractTagType(typeLiteral);
      String keyType = extractKeyType(typeLiteral);
      String valType = extractValueType(typeLiteral);

      return map(
          new CanonicalName(resolvePackageName(tagType), tagType),
          new CanonicalName(resolvePackageName(keyType), keyType),
          new CanonicalName(resolvePackageName(valType), valType)
      );
    }

    if (type instanceof PrimitiveType || typeLiteral.equals("char[]")) {
      return object(new CanonicalName(typeLiteral));
    } else {
      return object(new CanonicalName(resolvePackageName(typeLiteral), typeLiteral));
    }
  }

  private static boolean isList(String typeLiteral) {
    return typeLiteral.startsWith("List<") && typeLiteral.endsWith(">");
  }

  private static boolean isMap(String typeLiteral) {
    return typeLiteral.startsWith("Map<") && typeLiteral.endsWith(">");
  }

  private static String extractTagType(String typeLiteral) {
    return typeLiteral.substring(0, typeLiteral.indexOf("<"));
  }

  private static String extractType(String typeLiteral) {
    return typeLiteral.substring(5, typeLiteral.length() - 1);
  }

  private static String extractKeyType(String typeLiteral) {
    int index = typeLiteral.indexOf(",");
    return typeLiteral.substring(4, index);
  }

  private static String extractValueType(String typeLiteral) {
    int index = typeLiteral.indexOf(",");
    return typeLiteral.substring(index+2, typeLiteral.length() - 1);
  }

}

