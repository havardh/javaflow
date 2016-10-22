package no.havard.javaflow;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;


import no.havard.javaflow.model.ClassDefinition;
import no.havard.javaflow.model.Definition;
import no.havard.javaflow.model.EnumDefinition;
import no.havard.javaflow.model.FieldDefinition;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class JavaFlowTest {

  private static final String BASE_PATH = "src/test/java/no/havard/javaflow/model/";

  @Nested
  class ClassDefinitions {

    @Test
    public void shouldSetNameOfClass() {
      Definition definition = parse("Model");

      assertThat(definition.getName(), is("Model"));
    }

    @Test
    public void shouldSetFieldOfClass() {
      ClassDefinition definition = (ClassDefinition)parse("Model");

      FieldDefinition field = definition.getFieldDefinitions().get(0);

      assertThat(field.getName(), is("yolo"));
      assertThat(field.getType().toString(), is("String"));
    }

    @Test
    public void shouldAddParentNameToDefinition() {
      Definition definition = parse("Sub");

      assertThat(definition.getParent().get(), is("Super"));
    }

  }

  @Nested
  class EnumDefinitions {

    @Test
    public void shouldSetNameOfEnum() {
      Definition definition = parse("Enumeration");

      assertThat(definition.getName(), is("Enumeration"));
    }

    @Test
    public void shouldSetValuesOfEnum() {
      EnumDefinition definition = (EnumDefinition)parse("Enumeration");

      assertThat(definition.getValues(), contains("ONE", "TWO"));
    }
  }

  private static Definition parse(String name) {
    return JavaFlow.parse(BASE_PATH + name + ".java").get();
  }

}
