package com.github.havardh.javaflow.phases.writer.flow.converter;

import com.github.havardh.javaflow.phases.writer.flow.converter.definitions.Objects;
import com.github.havardh.javaflow.phases.writer.flow.converter.definitions.Primitives;
import com.github.havardh.javaflow.model.CanonicalName;
import com.github.havardh.javaflow.model.TypeMap;

/**
 * Type {@code Converter} from Java to flowtypes.
 */
public final class JavaFlowConverter implements Converter {

  private final TypeMap customTypeMap;

  /**
   * Create an empty {@code JavaFlowConverter}.
   */
  public JavaFlowConverter() {
    this.customTypeMap = TypeMap.emptyTypeMap();
  }

  /**
   * Create a {@code JavaFlowConverter} for a {@code TypeMap}
   *
   * @param typeMap the {@code TypeMap} for the converter
   */
  public JavaFlowConverter(TypeMap typeMap) {
    this.customTypeMap = typeMap;
  }

  /**
   * Convert a @{code CanonicalName} Java name to a flow type.
   *
   * @param canonicalName the name to convert
   * @return the flow type for the {@code CanonicalName}
   */
  public String convert(CanonicalName canonicalName) {
    String name = canonicalName.getName();
    String fullName = canonicalName.toString();

    return customTypeMap.getOrDefault(fullName, getOrDefault(fullName, name));
  }

  private static String getOrDefault(String name, String defaultName) {
    if (name.equals("?") || name.endsWith(".?")) {
      return "any";
    }

    if (Objects.isObject(name)) {
      return Objects.get(name);
    }

    if (Primitives.isPrimitive(name)) {
      return Primitives.get(name);
    }

    return defaultName;
  }

}
