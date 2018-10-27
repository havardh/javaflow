package com.github.havardh.javaflow;

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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.github.havardh.javaflow.ast.Class;
import com.github.havardh.javaflow.ast.Enum;
import com.github.havardh.javaflow.ast.Field;
import com.github.havardh.javaflow.ast.Parent;
import com.github.havardh.javaflow.ast.Type;
import com.github.havardh.javaflow.model.CanonicalName;
import com.github.havardh.javaflow.phases.parser.Parser;
import com.github.havardh.javaflow.phases.parser.java.JavaParser;
import com.github.havardh.javaflow.phases.reader.FileReader;
import com.github.havardh.javaflow.phases.transform.InheritanceTransformer;
import com.github.havardh.javaflow.phases.transform.Transformer;
import com.github.havardh.javaflow.phases.writer.flow.converter.JavaFlowConverter;

public class JavaFlowTest {

  private static final String BASE_PATH = "src/test/java/com/github/havardh/javaflow/model/";
  private static final JavaFlowConverter CONVERTER = new JavaFlowConverter();

  @Nested
  class Classes {

    @Test
    public void shouldSetNameOfClass() {
      Type type = parse("Model");

      assertThat(type.getCanonicalName().getName(), is("Model"));
    }

    @Test
    public void shouldSetPackageOfClass() {
      Type type = parse("Model");

      assertThat(type.getCanonicalName().getPackageName(), is("com.github.havardh.javaflow.model"));
    }

    @Test
    public void shouldSetFieldOfClass() {
      Class aClass = (Class)parse("Model");

      Field field = aClass.getFields().get(0);

      assertThat(field.getName(), is("yolo"));
      assertThat(field.getType().getCanonicalName().getName(), is("String"));
    }

    @Test
    public void shouldAddParentNameToDefinition() {
      Class aClass = (Class)parse("Sub");

      assertThat(aClass.getParent()
              .map(Parent::getCanonicalName)
              .map(CanonicalName::getName).get(),
          is("Super")
      );
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
          Map<String, String> types = typeMap((Class) parse("ModelWithCharArray"));

          assertThat(types.get("field"), is("string"));
        }
      }
    }

    @Nested
    public class Inheritance {
      Map<String, Type> typeMap;
      Class top, sup, sub;

      @BeforeEach
      public void setup() {
        typeMap = parseAll("Top", "Sub", "Super");
        top = (Class) typeMap.get("Top");
        sup = (Class) typeMap.get("Super");
        sub = (Class) typeMap.get("Sub");
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
      public class Fields {

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
  class Enums {

    @Test
    public void shouldSetNameOfEnum() {
      Type type = parse("Enumeration");

      assertThat(type.getCanonicalName().getName(), is("Enumeration"));
    }

    @Test
    public void shouldSetPackageNameOfEnum() {
      Type type = parse("Enumeration");

      assertThat(type.getCanonicalName().getPackageName(), is("com.github.havardh.javaflow.model"));
    }

    @Test
    public void shouldSetValuesOfEnum() {
      Enum anEnum = (Enum)parse("Enumeration");

      assertThat(anEnum.getValues(), contains("ONE", "TWO"));
    }
  }

  @Nested
  class Packages {

    @Test
    public void shouldSetPackageNameForFields() {
      Class aClass = (Class)parse("Wrapper");

      List<Field> fields = aClass.getFields();

      Field member = fields.get(0);
      Field packagedMember = fields.get(1);

      assertThat(member.getType().getCanonicalName().getPackageName(), is("com.github.havardh.javaflow.model"));
      assertThat(packagedMember.getType().getCanonicalName().getPackageName(),
          is("com.github.havardh.javaflow.model.packaged"));
    }
  }

  private static Type parse(String name) {
    return new FileReader().read(BASE_PATH + name + ".java")
        .map(new JavaParser()::parse)
        .map(Optional::get)
        .orElse(null);
  }

  private static Map<String, Type> parseAll(String ...modelNames) {
    FileReader adapter = new FileReader();
    Parser parser = new JavaParser();
    Transformer transformer = new InheritanceTransformer();

    List<Type> types = stream(modelNames)
        .map(name -> BASE_PATH + name + ".java")
        .map(adapter::read)
        .map(Optional::get)
        .map(parser::parse)
        .map(Optional::get)
        .collect(toList());

    transformer.transform(types);

    return types
        .stream()
        .collect(toMap(type -> type.getCanonicalName().getName(), identity()));
  }

  private static Map<String, String> typeMap(Class aClass) {
    return aClass.getFields()
        .stream()
        .collect(toMap(Field::getName, JavaFlowTest::fieldToFlow));
  }

  private static String fieldToFlow(Field field) {
    return CONVERTER.convert(field.getType().getCanonicalName());
  }

}
