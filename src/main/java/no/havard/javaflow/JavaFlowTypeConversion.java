package no.havard.javaflow;

import static java.util.Collections.unmodifiableMap;
import static java.util.stream.Stream.concat;

import static no.havard.javaflow.util.Maps.entriesToMap;
import static no.havard.javaflow.util.Maps.entry;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

import no.havard.javaflow.model.CanonicalName;

import com.esotericsoftware.yamlbeans.YamlReader;

public final class JavaFlowTypeConversion {

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
      entry("char[]", "?string"),

      entry("java.util.Date", "?string"),
      entry("java.util.Map", "Map"),
      entry("java.util.List", "Array"),

      entry("java.lang.Boolean", "?boolean"),
      entry("java.lang.Byte", "?number"),
      entry("java.lang.Character", "?string"),
      entry("java.lang.Double", "?number"),
      entry("java.lang.Float", "?number"),
      entry("java.lang.Integer", "?number"),
      entry("java.lang.Long", "?number"),
      entry("java.lang.Short", "?number"),
      entry("java.lang.String", "?string")
  );

  private static Map<String, String> TYPE_MAP = unmodifiableMap(
      concat(PRIMITIVES, OBJECTS).collect(entriesToMap())
  );

  private static Map<String, String> CUSTOM_TYPE_MAP = Collections.emptyMap();

  private JavaFlowTypeConversion() {
  }

  public static void init() {
    try (FileReader fileReader = new FileReader("types.yml")) {
      YamlReader yamlReader = new YamlReader(fileReader);

      CUSTOM_TYPE_MAP = (Map<String, String>)yamlReader.read();
    } catch (FileNotFoundException e) {
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(0);
    }

  }

  public static String toFlow(CanonicalName canonicalName) {
    String name = canonicalName.getName();
    String fullName = canonicalName.getCanonicalName();

    return CUSTOM_TYPE_MAP.getOrDefault(fullName, TYPE_MAP.getOrDefault(fullName, name));
  }

}
