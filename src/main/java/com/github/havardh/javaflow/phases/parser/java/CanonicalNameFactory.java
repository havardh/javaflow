package com.github.havardh.javaflow.phases.parser.java;

import java.util.Map;

import com.github.havardh.javaflow.model.CanonicalName;
import com.github.havardh.javaflow.util.Maps;

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

  public CanonicalNameFactory(String packageName, Map<String, String> imports) {
    this.packageName = packageName;
    this.imports = imports;
  }

  CanonicalName build(String name) {
    String packageName = imports.getOrDefault(name, BUILTIN.getOrDefault(name, this.packageName));
    return CanonicalName.object(packageName, name);
  }

}

