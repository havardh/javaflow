package no.havard.javaflow.model;

import java.util.List;

public class EnumDefinition extends Definition {

  private final List<String> values;

  public EnumDefinition(CanonicalName name, List<String> values) {
    super(name);
    this.values = values;
  }

  public List<String> getValues() {
    return values;
  }

}
