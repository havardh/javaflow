package no.havard.javaflow.convertion;

import static no.havard.javaflow.util.Maps.entriesToMap;
import static no.havard.javaflow.util.Maps.entry;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

import com.esotericsoftware.yamlbeans.YamlReader;

public final class JavaFlowTypeConversion {

  private static Stream<Map.Entry<String, String>> TYPE_CONVERSIONS = Stream.of(
      entry("byte", "number"),
      entry("short", "number"),
      entry("int", "number"),
      entry("long", "number"),
      entry("float", "number"),
      entry("double", "number"),
      entry("boolean", "boolean"),
      entry("char", "number"),

      entry("Integer", "?number"),
      entry("Double", "?number"),

      entry("java.util.Date", "?string"),
      entry("java.util.String", "?string"),

      entry("java.util.Boolean", "?boolean")
  );

  private static Map<String, String> TYPE_MAP = Collections.unmodifiableMap(TYPE_CONVERSIONS.collect(entriesToMap()));
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

  public static String toFlow(String name, String defaultName) {
    return CUSTOM_TYPE_MAP.getOrDefault(name, TYPE_MAP.getOrDefault(name, defaultName));
  }

}
