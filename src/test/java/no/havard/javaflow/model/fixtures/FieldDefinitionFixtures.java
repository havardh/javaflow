package no.havard.javaflow.model.fixtures;

import static no.havard.javaflow.model.builders.FieldBuilder.fieldBuilder;
import static no.havard.javaflow.model.fixtures.TypeFixtures.*;

import no.havard.javaflow.model.builders.FieldBuilder;

public class FieldDefinitionFixtures {

  public static FieldBuilder stringFieldDefinition() {
    return fieldBuilder()
        .withIsNullable(false)
        .withName("field")
        .withType(stringType()
            .build());
  }

}

