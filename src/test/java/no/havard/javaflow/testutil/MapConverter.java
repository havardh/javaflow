package no.havard.javaflow.testutil;

import java.util.HashMap;
import java.util.Map;

import no.havard.javaflow.model.CanonicalName;
import no.havard.javaflow.phases.writer.flow.converter.Converter;

public class MapConverter implements Converter {

  private Map<CanonicalName, String> map;

  private MapConverter(Map<CanonicalName, String> map) {
    this.map = map;
  }

  public static MapConverterBuilder builder() {
    return new MapConverterBuilder();
  }

  public static class MapConverterBuilder {
    private Map<CanonicalName, String> map = new HashMap<>();

    public MapConverterBuilder with(String cName, String type) {
      return with(CanonicalName.fromString(cName), type);
    }

    public MapConverterBuilder with(CanonicalName name, String type) {
      map.put(name, type);
      return this;
    }

    public MapConverter build() {
      return new MapConverter(map);
    }

  }

  @Override
  public String convert(CanonicalName name) {
    return map.get(name);
  }
}

