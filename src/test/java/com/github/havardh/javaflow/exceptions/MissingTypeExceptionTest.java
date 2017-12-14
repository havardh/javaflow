package com.github.havardh.javaflow.exceptions;

import static java.util.Collections.singletonList;

import static com.github.havardh.javaflow.testutil.Assertions.assertStringEqual;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.github.havardh.javaflow.ast.Field;
import com.github.havardh.javaflow.ast.Type;
import com.github.havardh.javaflow.ast.builders.FieldBuilder;
import com.github.havardh.javaflow.ast.builders.TypeBuilder;
import com.github.havardh.javaflow.model.CanonicalName;

public class MissingTypeExceptionTest {

  private static Type TYPE = TypeBuilder.object(CanonicalName.fromString("com.github.havardh.javaflow.Type"));
  private static Map<Type, List<Field>> MISSING_TYPES_FOR_FIELDS = new HashMap<>();

  static {
    MISSING_TYPES_FOR_FIELDS.put(
        TYPE,
        singletonList(FieldBuilder.fieldBuilder().withName("aField").withType(TYPE).build())
    );
  }

  @Test
  public void shouldPrintMessageWithMissingTypesDefinitions() {
    MissingTypeException exception = new MissingTypeException(MISSING_TYPES_FOR_FIELDS);

    assertStringEqual(
        exception.getMessage(),
        "Could not find types: {com.github.havardh.javaflow.Type=[aField: com.github.havardh.javaflow.Type]}"
    );
  }
}
