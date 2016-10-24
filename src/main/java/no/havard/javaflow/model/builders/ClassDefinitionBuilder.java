package no.havard.javaflow.model.builders;

import java.util.ArrayList;
import java.util.List;

import no.havard.javaflow.model.ClassDefinition;
import no.havard.javaflow.model.FieldDefinition;

import com.github.javaparser.ast.type.Type;

public class ClassDefinitionBuilder implements Builder<ClassDefinition> {

  private String packageName;
  private String name;
  private String parent;
  private List<FieldDefinition> fields = new ArrayList<>();

  private ClassDefinitionBuilder() {
  }

  public static ClassDefinitionBuilder classDefinitionBuilder() {
    return new ClassDefinitionBuilder();
  }

  public ClassDefinitionBuilder withPackageName(String packageName) {
    this.packageName = packageName;
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
    this.fields.add(new FieldDefinition(type, name));
    return this;
  }

  public ClassDefinition build() {
    if (parent == null) {
      return new ClassDefinition(packageName, name, fields);
    } else {
      return new ClassDefinition(packageName, name, parent, fields);
    }
  }
}
