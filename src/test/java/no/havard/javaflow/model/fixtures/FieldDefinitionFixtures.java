package no.havard.javaflow.model.fixtures;

import static no.havard.javaflow.model.builders.FieldBuilder.fieldDefinition;
import static no.havard.javaflow.model.fixtures.TypeFixtures.*;

import no.havard.javaflow.model.builders.FieldBuilder;

public class FieldDefinitionFixtures {

  public static FieldBuilder stringFieldDefinition() {
    return fieldDefinition()
        .withIsNullable(false)
        .withName("field")
        .withType(stringType()
            .build());
  }

}

