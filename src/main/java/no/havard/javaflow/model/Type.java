package no.havard.javaflow.model;

public class Type {

  protected final CanonicalName name;

  private Type(CanonicalName name) {
    this.name = name;
  }

  public String getName() {
    return name.getName();
  }

  public CanonicalName getCanonicalName() {
    return name;
  }

  public String getPackageName() {
    return name.getPackageName();
  }

  public static Type object(CanonicalName name) {
    return new Type(name);
  }

  public static Type list(CanonicalName name, CanonicalName type) {
    return new ListType(name, type);
  }

  public static Type map(CanonicalName name, CanonicalName key, CanonicalName value) {
    return new MapType(name, key, value);
  }

  public static class ListType extends Type {
    private final CanonicalName type;

    public ListType(CanonicalName name, CanonicalName type) {
      super(name);
      this.type = type;
    }

    public CanonicalName getType() {
      return type;
    }
  }

  public static class MapType extends Type {
    private final CanonicalName key;
    private final CanonicalName value;

    public MapType(CanonicalName name, CanonicalName key, CanonicalName value) {
      super(name);
      this.key = key;
      this.value = value;
    }

    public CanonicalName getKeyType() {
      return key;
    }

    public CanonicalName getValueType() {
      return value;
    }
  }
}

