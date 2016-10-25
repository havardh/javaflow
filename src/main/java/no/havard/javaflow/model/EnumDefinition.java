package no.havard.javaflow.model;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

import java.util.List;

public class EnumDefinition extends Definition {

  private final List<String> values;

  public EnumDefinition(String packageName, String name, List<String> values) {
    super(packageName, name);
    this.values = values;
  }

  public List<String> getValues() {
    return values;
  }

  @Override
  public String toString() {
    return format("export type %s = \n  | \"%s\";", name, values.stream().collect(joining("\" \n  | \"")));
  }
}
