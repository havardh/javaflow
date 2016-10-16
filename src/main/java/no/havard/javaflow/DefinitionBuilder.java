package no.havard.javaflow;

import static no.havard.javaflow.DefinitionBuilder.DefinitionType.Class;

import java.util.ArrayList;
import java.util.List;
import com.github.javaparser.ast.type.Type;

public class DefinitionBuilder {

  private DefinitionType type;

  private String name;
  private List<FieldDefinition> fields;
  private List<String> values;

  public enum DefinitionType { Enum, Class }

  private DefinitionBuilder() {
  }

  public static DefinitionBuilder builder() {
   return new DefinitionBuilder();
  }

  public DefinitionBuilder withType(DefinitionType type) {
    this.type = type;
    return this;
  }

  public DefinitionBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public DefinitionBuilder withField(Type type, String name) {
    if (fields == null) {
      fields = new ArrayList<>();
    }
    this.fields.add(new FieldDefinition(type, name));
    return this;
  }

  public DefinitionBuilder withEnumValue(String name) {
    if (values == null) {
      values = new ArrayList<>();
    }
    this.values.add(name);
    return this;
  }

  public Definition build() {
    return type == Class ?
        new ClassDefinition(name, fields) :
        new EnumDefinition(name, values);
  }

}