package no.havard.javaflow;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;


import no.havard.javaflow.model.ClassDefinition;
import no.havard.javaflow.model.EnumDefinition;
import no.havard.javaflow.model.FieldDefinition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class JavaFlowTest {

  private static final String BASE_PATH = "src/test/java/no/havard/javaflow/model/";

  @Nested
  class ClassDefinitions {
    private static final String MODEL = "Model.java";
    private ClassDefinition def;

    @BeforeEach
    void parse() {
      def = (ClassDefinition) JavaFlow.parse(BASE_PATH + MODEL);
    }

    @Test
    public void shouldSetNameOfClass() {
      assertThat(def.getName(), is("Model"));
    }

    @Test
    public void shouldSetFieldOfClass() {

      FieldDefinition field = def.getFieldDefinitions().get(0);

      assertThat(field.getName(), is("yolo"));
      assertThat(field.getType().toString(), is("String"));
    }
  }

  @Nested
  class EnumDefinitions {
    private static final String ENUM = "Enumeration.java";
    private EnumDefinition def;

    @BeforeEach
    public void parse() {
      def = (EnumDefinition)JavaFlow.parse(BASE_PATH + ENUM);
    }

    @Test
    public void shouldSetNameOfEnum() {
      assertThat(def.getName(), is("Enumeration"));
    }

    @Test
    public void shouldSetValuesOfEnum() {
      assertThat(def.getValues(), contains("ONE", "TWO"));
    }
  }

}
