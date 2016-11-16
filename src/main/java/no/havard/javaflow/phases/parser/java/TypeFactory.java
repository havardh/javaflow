package no.havard.javaflow.phases.parser.java;

import static no.havard.javaflow.ast.builders.TypeBuilder.list;
import static no.havard.javaflow.ast.builders.TypeBuilder.map;
import static no.havard.javaflow.ast.builders.TypeBuilder.object;

import java.util.Map;

import no.havard.javaflow.ast.Type;
import no.havard.javaflow.ast.builders.TypeBuilder;
import no.havard.javaflow.model.CanonicalName;

import com.github.javaparser.ast.type.PrimitiveType;

public class TypeFactory {

  private CanonicalNameFactory canonicalNameFactory;

  public TypeFactory(String packageName, Map<String, String> imports) {
    this.canonicalNameFactory = new CanonicalNameFactory(packageName, imports);
  }

  public Type build(com.github.javaparser.ast.type.Type type) {
    String typeLiteral = type.toString();

    if (isList(typeLiteral)) {
      String tagType = extractTagType(typeLiteral);
      String valType = extractType(typeLiteral);
      return list(
          canonicalNameFactory.build(tagType),
          canonicalNameFactory.build(valType)
      );
    }

    if (isMap(typeLiteral)) {
      String tagType = extractTagType(typeLiteral);
      String keyType = extractKeyType(typeLiteral);
      String valType = extractValueType(typeLiteral);

      return map(
          canonicalNameFactory.build(tagType),
          canonicalNameFactory.build(keyType),
          canonicalNameFactory.build(valType)
      );
    }

    if (type instanceof PrimitiveType || typeLiteral.equals("char[]")) {
      return TypeBuilder.primitive(CanonicalName.primitive(typeLiteral));
    } else {
      return object(canonicalNameFactory.build(typeLiteral));
    }
  }

  private static boolean isList(String typeLiteral) {
    return typeLiteral.startsWith("List<") && typeLiteral.endsWith(">")
        || typeLiteral.startsWith("Collection<") && typeLiteral.endsWith(">");
  }

  private static boolean isMap(String typeLiteral) {
    return typeLiteral.startsWith("Map<") && typeLiteral.endsWith(">");
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

}

