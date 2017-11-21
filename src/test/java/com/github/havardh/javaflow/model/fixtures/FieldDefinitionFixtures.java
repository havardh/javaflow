package com.github.havardh.javaflow.model.fixtures;

import static com.github.havardh.javaflow.ast.builders.FieldBuilder.fieldBuilder;

import com.github.havardh.javaflow.ast.builders.FieldBuilder;

public class FieldDefinitionFixtures {

  public static FieldBuilder stringFieldDefinition() {
    return fieldBuilder()
        .withIsNullable(false)
        .withName("field")
        .withType(TypeFixtures.stringType()
            .build());
  }

}

