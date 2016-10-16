package no.havard.javaflow.model;

import java.util.ArrayList;
import java.util.List;
import com.github.javaparser.ast.type.Type;

public class ClassDefinitionBuilder {

  private String name;
  private List<FieldDefinition> fields;

  private ClassDefinitionBuilder() {
  }

  public static ClassDefinitionBuilder builder() {
    return new ClassDefinitionBuilder();
  }

  public ClassDefinitionBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public ClassDefinitionBuilder withField(Type type, String name) {
    if (fields == null) {
      fields = new ArrayList<>();
    }
    this.fields.add(new FieldDefinition(type, name));
    return this;
  }

  public Definition build() {
    return new ClassDefinition(name, fields);
  }

}