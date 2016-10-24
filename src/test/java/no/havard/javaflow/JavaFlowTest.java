package no.havard.javaflow;

import static java.util.Arrays.asList;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import static no.havard.javaflow.convertion.FileSetHandler.handleExtends;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import no.havard.javaflow.model.ClassDefinition;
import no.havard.javaflow.model.Definition;
import no.havard.javaflow.model.EnumDefinition;
import no.havard.javaflow.model.FieldDefinition;
import no.havard.javaflow.model.Parent;

import org.junit.jupiter.api.BeforeEach;
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
    public void shouldSetPackageOfClass() {
      Definition definition = parse("Model");

      assertThat(definition.getPackageName(), is("no.havard.javaflow.model"));
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

      assertThat(definition.getParent().map(Parent::getName).get(), is("Super"));
    }

    @Nested
    public class Inheritance {
      Map<String, Definition> definitions;
      ClassDefinition top, sup, sub;

      @BeforeEach
      public void setup() {
        definitions = parseAll("Top", "Sub", "Super");
        top = (ClassDefinition) definitions.get("Top");
        sup = (ClassDefinition) definitions.get("Super");
        sub = (ClassDefinition) definitions.get("Sub");
      }

      @Test
      public void shouldParseAllModels() {
        assertThat(sup, is(notNullValue()));
        assertThat(sub, is(notNullValue()));
      }

      @Nested
      public class ParentLinks {

        @Test
        public void shouldSetSubParentLinkToSuper() {
          assertThat(sub.getParent().map(Parent::getReference).get(), is(sup));
        }

        @Test
        public void shouldSetSupParentLinkToTop() {
          assertThat(sup.getParent().map(Parent::getReference).get(), is(top));
        }

        @Test
        public void shouldNotSetTopParentLink() {
          assertThat(top.getParent(), is(Optional.empty()));
        }
      }

      @Nested
      public class FieldDefinitions {

        @Nested
        public class Top {

          @Test
          public void shouldHaveASingleField() {
            assertThat(top.getFieldDefinitions(), hasSize(1));
            assertThat(top.getFieldDefinitions().get(0).getName(), is("topField"));
          }
        }

        @Nested
        public class Super {

          @Test
          public void shouldInheritFieldFromTop() {
            assertThat(sup.getFieldDefinitions(), hasSize(2));
          }

          @Test
          public void shouldHaveTopFieldsFirst() {
            assertThat(sup.getFieldDefinitions().get(0).getName(), is("topField"));
          }

          @Test
          public void shouldHaveOwnFieldLast() {
            assertThat(sup.getFieldDefinitions().get(1).getName(), is("superField"));
          }
        }

        @Nested
        public class Sub {

          @Test
          public void shouldInheritFieldFromAllParents() {
            assertThat(sub.getFieldDefinitions().size(), is(3));
          }

          @Test
          public void shouldHaveFieldFromTopMostParentFirst() {
            assertThat(sub.getFieldDefinitions().get(0).getName(), is("topField"));
          }
          @Test
          public void shouldHaveAllParentFieldBeforeOwn() {
            assertThat(sub.getFieldDefinitions().get(1).getName(), is("superField"));
          }

          @Test
          public void shouldHaveOwnFieldLast() {
            assertThat(sub.getFieldDefinitions().get(2).getName(), is("subField"));
          }
        }
      }
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
    public void shouldSetPackageNameOfEnum() {
      Definition definition = parse("Enumeration");

      assertThat(definition.getPackageName(), is("no.havard.javaflow.model"));
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

  private static Map<String, Definition> parseAll(String ...modelNames) {
    List<Definition> definitions = JavaFlow.parseAll(asList(modelNames)
        .stream()
        .map(name -> BASE_PATH + name + ".java")
        .collect(toList())
        .toArray(new String[]{}));

    handleExtends(definitions);

    return definitions
        .stream()
        .collect(toMap(Definition::getName, identity()));
  }

}
