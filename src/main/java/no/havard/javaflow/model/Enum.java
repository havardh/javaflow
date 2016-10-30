package no.havard.javaflow.model;

import java.util.List;

public class Enum extends Definition {

  private final List<String> values;

  public Enum(CanonicalName name, List<String> values) {
    super(name);
    this.values = values;
  }

  public List<String> getValues() {
    return values;
  }

}
