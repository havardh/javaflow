package no.havard.javaflow.model;

import static java.util.Arrays.stream;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.type.Type;

public class ClassDefinitionBuilder implements Builder<ClassDefinition> {

  private String name;
  private String parent;
  private List<FieldDefinition> fields = new ArrayList<>();

  private ClassDefinitionBuilder() {
  }

  public static ClassDefinitionBuilder classDefinitionBuilder() {
    return new ClassDefinitionBuilder();
  }

  public ClassDefinitionBuilder with(ClassDefinition classDefinition) {
    withName(classDefinition.name);
    withParent(classDefinition.parent.map(Parent::getName).orElse(null));
    withFields(classDefinition.getFieldDefinitions().toArray(new FieldDefinition[]{}));
    return this;
  }

  public ClassDefinitionBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public ClassDefinitionBuilder withParent(String parent) {
    this.parent = parent;
    return this;
  }

  public ClassDefinitionBuilder withField(Type type, String name) {
    return withField(new FieldDefinition(type, name));
  }

  public ClassDefinitionBuilder withField(FieldDefinition definition) {
    this.fields.add(definition);
    return this;
  }

  public ClassDefinitionBuilder withFields(FieldDefinition ... definitions) {
    stream(definitions).forEach(this::withField);
    return this;
  }

  public ClassDefinition build() {
    if (parent == null) {
      return new ClassDefinition(name, fields);
    } else {
      return new ClassDefinition(name, parent, fields);
    }
  }

}
