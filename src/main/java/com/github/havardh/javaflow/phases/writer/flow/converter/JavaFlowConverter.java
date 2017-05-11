package com.github.havardh.javaflow.phases.writer.flow.converter;

import com.github.havardh.javaflow.phases.writer.flow.converter.definitions.Objects;
import com.github.havardh.javaflow.phases.writer.flow.converter.definitions.Primitives;
import com.github.havardh.javaflow.model.CanonicalName;
import com.github.havardh.javaflow.model.TypeMap;

public final class JavaFlowConverter implements Converter {

  private final TypeMap customTypeMap;

  public JavaFlowConverter() {
    this.customTypeMap = TypeMap.emptyTypeMap();
  }

  public JavaFlowConverter(TypeMap typeMap) {
    this.customTypeMap = typeMap;
  }

  public String convert(CanonicalName canonicalName) {
    String name = canonicalName.getName();
    String fullName = canonicalName.getCanonicalName();

    return customTypeMap.getOrDefault(fullName, getOrDefault(fullName, name));
  }

  private static String getOrDefault(String name, String defaultName) {
    if (Objects.isObject(name)) {
      return Objects.get(name);
    }

    if (Primitives.isPrimitive(name)) {
      return Primitives.get(name);
    }

    return defaultName;
  }

}
