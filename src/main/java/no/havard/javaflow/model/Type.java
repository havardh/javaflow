package no.havard.javaflow.model;

import static java.lang.String.format;

import no.havard.javaflow.convertion.JavaFlowTypeConversion;

public class Type {

  protected final String name;

  private Type(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static Type object(String name) {
    return new Type(name);
  }

  public static Type list(String name, String type) {
    return new ListType(name, type);
  }

  public static Type map(String name, String key, String value) {
    return new MapType(name, key, value);
  }

  private static class ListType extends Type {
    private final String type;

    public ListType(String name, String type) {
      super(name);
      this.type = type;
    }

    @Override
    public String toString() {
      return format("%s<%s>",
          name,
          JavaFlowTypeConversion.toFlow(type, type)
      );
    }
  }

  private static class MapType extends Type {
    private final String key;
    private final String value;

    public MapType(String name, String key, String value) {
      super(name);
      this.key = key;
      this.value = value;
    }

    @Override
    public String toString() {
      return format("{[key: %s]: %s}",
          JavaFlowTypeConversion.toFlow(key, key),
          JavaFlowTypeConversion.toFlow(value, value)
      );
    }
  }

  @Override
  public String toString() {
    return JavaFlowTypeConversion.toFlow(name, name);
  }
}

