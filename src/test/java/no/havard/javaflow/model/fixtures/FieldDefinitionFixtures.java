package no.havard.javaflow.model.fixtures;

import static no.havard.javaflow.model.builders.FieldDefinitionBuilder.fieldDefinition;
import static no.havard.javaflow.model.fixtures.TypeFixtures.*;

import no.havard.javaflow.model.builders.FieldDefinitionBuilder;

public class FieldDefinitionFixtures {

  public static FieldDefinitionBuilder stringFieldDefinition() {
    return fieldDefinition()
        .withIsNullable(false)
        .withName("field")
        .withType(stringType()
            .build());
  }

}

