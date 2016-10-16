package no.havard.javaflow.convertion;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.github.javaparser.ast.type.Type;

public final class JavaFlowTypeConversion {

  private static Stream<Map.Entry<String, String>> TYPE_CONVERSIONS = Stream.of(
      entry("int", "number"),
      entry("Integer", "?number"),
      entry("double", "number"),
      entry("Double", "?number"),

      entry("String", "string"),

      entry("boolean", "boolean"),
      entry("Boolean", "?boolean")
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

  public static String toFlow(Type type) {
    return CUSTOM_TYPE_MAP.getOrDefault(type.toString(),
        TYPE_MAP.getOrDefault(type.toString(),
            type.toString()));
  }

  private static Map.Entry<String, String> entry(String key, String value) {
    return new AbstractMap.SimpleEntry<>(key, value);
  }

  private static Collector<Map.Entry<String, String>, ?, Map<String, String>> entriesToMap() {
    return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue);
  }

}
