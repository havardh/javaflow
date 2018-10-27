package com.github.havardh.javaflow.phases.parser.java;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.github.havardh.javaflow.ast.Type;
import com.github.havardh.javaflow.ast.builders.TypeBuilder;
import com.github.havardh.javaflow.model.CanonicalName;

/**
 * A factory for {@code Type} used to create these objects from
 * Java models.
 */
public class TypeFactory {

  private static final List<String> PRIMITIVES = Arrays.asList("boolean", "byte",
      "char", "double", "float", "int", "long", "short");

  private CanonicalNameFactory canonicalNameFactory;

  /**
   * Constructs a {@code TypeFactory} based on the a package name and a list
   * of imports.
   *
   * The types build by this factory will have their names assigned based on
   * the package name and or the imported name. The package name should be
   * the package name for the model the factory is used for. The list of
   * imported names should be equal to the list of imports in the model the
   * factory is used for.
   *
   * @param packageName the package name for the model to use the factory for
   * @param imports the list of imports for the model to use the factory for
   */
  public TypeFactory(String packageName, Map<String, String> imports) {
    this.canonicalNameFactory = new CanonicalNameFactory(packageName, imports);
  }

  /**
   * Builds a {@code Type} from the given type literal and is primitive flag.
   *
   * The canonical name for the {@code Type} is resolved based on the context of
   * the factory. If the name used in the model is found in the import the canonical
   * name is build using the imported name. If the name is not imported the {@code Type}
   * is given the same package name as the factory is initialized for.
   *
   * @param typeLiteral the literal name for the type used in the model
   * @param isPrimitive a flag saying if the type is a primitive or an object
   * @return the {@code Type} representation for type literal
   */
  public Type build(String typeLiteral, boolean isPrimitive) {
    if (isList(typeLiteral) || isSet(typeLiteral)) {
      String tagType = extractTagType(typeLiteral);
      String valType = extractType(typeLiteral);
      return TypeBuilder.list(
          canonicalNameFactory.build(tagType),
          build(valType, false)
      );
    }

    if (isMap(typeLiteral)) {
      String tagType = extractTagType(typeLiteral);
      String keyType = extractKeyType(typeLiteral);
      String valType = extractValueType(typeLiteral);

      return TypeBuilder.map(
          canonicalNameFactory.build(tagType),
          build(keyType, false),
          build(valType, false)
      );
    }

    if (isPrimitive || typeLiteral.equals("char[]")) {
      return TypeBuilder.primitive(CanonicalName.primitive(typeLiteral));
    }

    if (isArray(typeLiteral)) {
      String valType = extractArrayType(typeLiteral);
      return TypeBuilder.list(
          CanonicalName.fromString(List.class.getName()),
          build(valType, PRIMITIVES.contains(valType))
      );
    }

    return TypeBuilder.object(canonicalNameFactory.build(typeLiteral));
  }

  private static boolean isList(String typeLiteral) {
    return typeLiteral.startsWith("List<") && typeLiteral.endsWith(">")
        || typeLiteral.startsWith("Collection<") && typeLiteral.endsWith(">");
  }

  private static boolean isSet(String typeLiteral) {
    return typeLiteral.startsWith("Set<") && typeLiteral.endsWith(">");
  }

  private static boolean isMap(String typeLiteral) {
    return typeLiteral.startsWith("Map<") && typeLiteral.endsWith(">");
  }

  private static boolean isArray(String typeLiteral) {
    return typeLiteral.endsWith("[]");
  }

  private static String extractTagType(String typeLiteral) {
    return typeLiteral.substring(0, typeLiteral.indexOf("<"));
  }

  private static String extractType(String typeLiteral) {
    return typeLiteral.substring(typeLiteral.indexOf("<") + 1, typeLiteral.length() - 1);
  }

  private static String extractKeyType(String typeLiteral) {
    int index = typeLiteral.indexOf(",");
    return typeLiteral.substring(4, index);
  }

  private static String extractValueType(String typeLiteral) {
    int index = typeLiteral.indexOf(",");
    return typeLiteral.substring(index+2, typeLiteral.length() - 1);
  }

  private static String extractArrayType(String typeLiteral) {
    return typeLiteral.substring(0, typeLiteral.lastIndexOf("[]"));
  }

}

