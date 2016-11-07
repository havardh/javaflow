package no.havard.javaflow.phases.writer.flow.converter;

import static java.util.Collections.unmodifiableMap;
import static java.util.stream.Stream.concat;

import static no.havard.javaflow.util.Maps.entriesToMap;
import static no.havard.javaflow.util.Maps.entry;
import static no.havard.javaflow.util.TypeMap.emptyTypeMap;

import java.util.Map;
import java.util.stream.Stream;

import no.havard.javaflow.model.CanonicalName;
import no.havard.javaflow.util.TypeMap;

public final class JavaFlowConverter implements Converter {

  private static Stream<Map.Entry<String, String>> PRIMITIVES = Stream.of(
      entry("byte", "number"),
      entry("short", "number"),
      entry("int", "number"),
      entry("long", "number"),
      entry("float", "number"),
      entry("double", "number"),
      entry("boolean", "boolean"),
      entry("char", "string")
  );

  private static Stream<Map.Entry<String, String>> OBJECTS = Stream.of(
      entry("char[]", "string"),

      entry("java.util.Date", "string"),
      entry("java.util.Map", "Map"),
      entry("java.util.List", "Array"),

      entry("java.lang.Boolean", "boolean"),
      entry("java.lang.Byte", "number"),
      entry("java.lang.Character", "string"),
      entry("java.lang.Double", "number"),
      entry("java.lang.Float", "number"),
      entry("java.lang.Integer", "number"),
      entry("java.lang.Long", "number"),
      entry("java.lang.Short", "number"),
      entry("java.lang.String", "string")
  );

  private static Map<String, String> TYPE_MAP = unmodifiableMap(
      concat(PRIMITIVES, OBJECTS).collect(entriesToMap())
  );

  private final TypeMap customTypeMap;

  public JavaFlowConverter() {
    customTypeMap = emptyTypeMap();
  }

  public JavaFlowConverter(String filename) {
    customTypeMap = new TypeMap(filename);
  }

  public String convert(CanonicalName canonicalName) {
    String name = canonicalName.getName();
    String fullName = canonicalName.getCanonicalName();

    return customTypeMap.getOrDefault(fullName, TYPE_MAP.getOrDefault(fullName, name));
  }

}
