package com.github.havardh.javaflow.phases.writer.flow.converter.definitions;

import java.util.Map;

import com.github.havardh.javaflow.util.Maps;

/**
 * Static utility for converting fro java primitive types to flow types
 */
public class Primitives {

  public static Map<String, String> PRIMITIVES = Maps.collect(
      Maps.entry("byte", "number"),
      Maps.entry("short", "number"),
      Maps.entry("int", "number"),
      Maps.entry("long", "number"),
      Maps.entry("float", "number"),
      Maps.entry("double", "number"),
      Maps.entry("boolean", "boolean"),
      Maps.entry("char", "string")
  );

  /**
   * Check if a name is a java primitive
   *
   * @param name the name to check
   * @return true if the name is a primitive
   */
  public static boolean isPrimitive(String name) {
    return PRIMITIVES.containsKey(name);
  }

  /**
   * Get the flow type of a java primitive
   *
   * @param name the java primitive
   * @return the flow type
   */
  public static String get(String name) {
    return PRIMITIVES.get(name);
  }

}
