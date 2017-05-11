package com.github.havardh.javaflow.phases.writer.flow.converter.definitions;

import java.util.Map;

import com.github.havardh.javaflow.util.Maps;

/**
 * Static utility for converting fro java built-in object types to flow types
 */
public class Objects {

  public static Map<String, String> OBJECTS = Maps.collect(
      Maps.entry("char[]", "string"),

      Maps.entry("java.util.Date", "string"),
      Maps.entry("java.util.Map", "Map"),
      Maps.entry("java.util.List", "Array"),
      Maps.entry("java.util.Collection", "Array"),
      Maps.entry("java.util.Set", "Array"),

      Maps.entry("java.lang.Boolean", "boolean"),
      Maps.entry("java.lang.Byte", "number"),
      Maps.entry("java.lang.Character", "string"),
      Maps.entry("java.lang.Double", "number"),
      Maps.entry("java.lang.Float", "number"),
      Maps.entry("java.lang.Integer", "number"),
      Maps.entry("java.lang.Long", "number"),
      Maps.entry("java.lang.Short", "number"),
      Maps.entry("java.lang.String", "string")
  );

  /**
   * Check if a name is a java built-in object
   *
   * @param name the name to check
   * @return true if the name is a built-in object
   */
  public static boolean isObject(String name) {
    return OBJECTS.containsKey(name);
  }

  /**
   * Get the flow type of a java built-in type
   *
   * @param name the java built-in type
   * @return the flow type
   */
  public static String get(String name) {
    return OBJECTS.get(name);
  }

}
