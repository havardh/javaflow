package no.havard.javaflow;

import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import no.havard.javaflow.ast.Class;
import no.havard.javaflow.ast.Definition;
import no.havard.javaflow.ast.Enum;
import no.havard.javaflow.ast.Field;
import no.havard.javaflow.ast.Parent;
import no.havard.javaflow.phases.reader.java.JavaReader;
import no.havard.javaflow.phases.transform.InheritanceTransformer;

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
      Class definition = (Class)parse("Model");

      Field field = definition.getFields().get(0);

      assertThat(field.getName(), is("yolo"));
      assertThat(field.getType().getName(), is("String"));
    }

    @Test
    public void shouldAddParentNameToDefinition() {
      Class aClass = (Class)parse("Sub");

      assertThat(aClass.getParent().map(Parent::getName).get(), is("Super"));
    }

    @Nested
    class Types {

      @Nested
      class Primitives {
        private Map<String, String> typeMap;

        @BeforeEach
        public void setup() {
          typeMap = typeMap((Class) parse("ModelWithPrimitive"));
        }

        @Test
        public void shouldMapByte() {
          assertThat(typeMap.get("byteField"), is("number"));
        }

        @Test
        public void shouldMapShort() {
          assertThat(typeMap.get("shortField"), is("number"));
        }

        @Test
        public void shouldMapInt() {
          assertThat(typeMap.get("intField"), is("number"));
        }

        @Test
        public void shouldMapLong() {
          assertThat(typeMap.get("longField"), is("number"));
        }

        @Test
        public void shouldMapFloat() {
          assertThat(typeMap.get("floatField"), is("number"));
        }

        @Test
        public void shouldMapDouble() {
          assertThat(typeMap.get("doubleField"), is("number"));
        }

        @Test
        public void shouldMapBoolean() {
          assertThat(typeMap.get("booleanField"), is("boolean"));
        }

        @Test
        public void shouldMapChar() {
          assertThat(typeMap.get("charField"), is("string"));
        }
      }

      @Nested
      class JavaLangObjects {
        private Map<String, String> typeMap;

        @BeforeEach
        public void setup() {
          typeMap = typeMap((Class) parse("ModelWithJavaLangObjects"));
        }

        @Test
        public void shouldMapByte() {
          assertThat(typeMap.get("byteField"), is("number"));
        }

        @Test
        public void shouldMapShort() {
          assertThat(typeMap.get("shortField"), is("number"));
        }

        @Test
        public void shouldMapInt() {
          assertThat(typeMap.get("integerField"), is("number"));
        }

        @Test
        public void shouldMapLong() {
          assertThat(typeMap.get("longField"), is("number"));
        }

        @Test
        public void shouldMapFloat() {
          assertThat(typeMap.get("floatField"), is("number"));
        }

        @Test
        public void shouldMapDouble() {
          assertThat(typeMap.get("doubleField"), is("number"));
        }

        @Test
        public void shouldMapBoolean() {
          assertThat(typeMap.get("booleanField"), is("boolean"));
        }

        @Test
        public void shouldMapChar() {
          assertThat(typeMap.get("characterField"), is("string"));
        }

        @Test
        public void shouldMapString() {
          assertThat(typeMap.get("stringField"), is("string"));
        }

      }

      @Nested
      class Arrays {

        @Test
        public void shouldMapCharArrayToString() {
          Map<String, String> types = typeMap((Class) parse("ModelWithArrays"));

          assertThat(types.get("field"), is("string"));
        }
      }
    }

    @Nested
    public class Inheritance {
      Map<String, Definition> definitions;
      Class top, sup, sub;

      @BeforeEach
      public void setup() {
        definitions = parseAll("Top", "Sub", "Super");
        top = (Class) definitions.get("Top");
        sup = (Class) definitions.get("Super");
        sub = (Class) definitions.get("Sub");
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
            assertThat(top.getFields(), hasSize(1));
            assertThat(top.getFields().get(0).getName(), is("topField"));
          }
        }

        @Nested
        public class Super {

          @Test
          public void shouldInheritFieldFromTop() {
            assertThat(sup.getFields(), hasSize(2));
          }

          @Test
          public void shouldHaveTopFieldsFirst() {
            assertThat(sup.getFields().get(0).getName(), is("topField"));
          }

          @Test
          public void shouldHaveOwnFieldLast() {
            assertThat(sup.getFields().get(1).getName(), is("superField"));
          }
        }

        @Nested
        public class Sub {

          @Test
          public void shouldInheritFieldFromAllParents() {
            assertThat(sub.getFields().size(), is(3));
          }

          @Test
          public void shouldHaveFieldFromTopMostParentFirst() {
            assertThat(sub.getFields().get(0).getName(), is("topField"));
          }
          @Test
          public void shouldHaveAllParentFieldBeforeOwn() {
            assertThat(sub.getFields().get(1).getName(), is("superField"));
          }

          @Test
          public void shouldHaveOwnFieldLast() {
            assertThat(sub.getFields().get(2).getName(), is("subField"));
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
      Enum definition = (Enum)parse("Enumeration");

      assertThat(definition.getValues(), contains("ONE", "TWO"));
    }
  }

  @Nested
  class Packages {

    @Test
    public void shouldSetPackageNameForFields() {
      Class definition = (Class)parse("Wrapper");

      List<Field> fields = definition.getFields();

      Field member = fields.get(0);
      Field packagedMember = fields.get(1);

      assertThat(member.getPackageName(), is("no.havard.javaflow.model"));
      assertThat(packagedMember.getPackageName(), is("no.havard.javaflow.model.packaged"));
    }

  }

  private static Definition parse(String name) {
    return new JavaReader().read(BASE_PATH + name + ".java").get();
  }

  private static Map<String, Definition> parseAll(String ...modelNames) {
    List<Definition> definitions = JavaFlow.parseAll(stream(modelNames)
        .map(name -> BASE_PATH + name + ".java")
        .collect(toList())
        .toArray(new String[]{}));

    new InheritanceTransformer().transform(definitions);

    return definitions
        .stream()
        .collect(toMap(Definition::getName, identity()));
  }

  private static Map<String, String> typeMap(Class aClass) {
    return aClass.getFields()
        .stream()
        .collect(toMap(Field::getName, Field::getFlowType));
  }

}
