package com.github.havardh.javaflow.phases.parser.java;

import java.util.Map;

import com.github.havardh.javaflow.model.CanonicalName;
import com.github.havardh.javaflow.util.Maps;

/**
 * A Java specific factory for building a {@code CanonicalName}.
 *
 * The factory builds CanonicalNames for the the context or a java built-in type.
 * It will first resolve the name by checking if it is imported, then looking up
 * the typename in the list of built-in types for {@code java.long}. If it does not
 * find the name in the imports or built-in types it will give the type the default
 * package name which should be equal to the package name of the model the type was
 * used in.
 *
 * The list of built-in types are needed as Java does not require explicit imports of
 * these types in a Java program.
 */
public class CanonicalNameFactory {

  private static Map<String, String> BUILTIN = Maps.collect(
      Maps.entry("Boolean", "java.lang"),
      Maps.entry("Byte", "java.lang"),
      Maps.entry("Character", "java.lang"),
      Maps.entry("Double", "java.lang"),
      Maps.entry("Float", "java.lang"),
      Maps.entry("Integer", "java.lang"),
      Maps.entry("Long", "java.lang"),
      Maps.entry("Short", "java.lang"),
      Maps.entry("String", "java.lang")
  );

  private final String packageName;
  private final Map<String, String> imports;

  /**
   * Sets up a factory for the given package name and list of imports. The factory
   * holds an internal list of Java built-in types from the {@code java.lang} package.
   * @param packageName
   * @param imports
   */
  public CanonicalNameFactory(String packageName, Map<String, String> imports) {
    this.packageName = packageName;
    this.imports = imports;
  }

  /**
   * Build a canonical name for the given name.
   *
   * @param name the name to build a {@CanonicalName} for
   * @return the {©code CanonicalName} for the given {@code name}
   */
  CanonicalName build(String name) {
    String packageName = imports.getOrDefault(name, BUILTIN.getOrDefault(name, this.packageName));
    return CanonicalName.object(packageName, name);
  }

}

