package com.github.havardh.javaflow.phases.verifier;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.github.havardh.javaflow.ast.Class;
import com.github.havardh.javaflow.ast.Type;
import com.github.havardh.javaflow.ast.builders.ClassBuilder;
import com.github.havardh.javaflow.ast.builders.FieldBuilder;
import com.github.havardh.javaflow.ast.builders.MethodBuilder;
import com.github.havardh.javaflow.ast.builders.TypeBuilder;
import com.github.havardh.javaflow.exceptions.AggregatedException;
import com.github.havardh.javaflow.model.CanonicalName;

class ClassGetterNamingVerifierTest {

  private static final Type PRIMITIVE_INT = TypeBuilder.primitive(CanonicalName.primitive("int"));
  private static final Type PRIMITIVE_DOUBLE = TypeBuilder.primitive(CanonicalName.primitive("double"));

  private ClassGetterNamingVerifier verifier = new ClassGetterNamingVerifier();

  @Test
  public void shouldPassForModelWithMatchingGetters() {
    Class aClass = ClassBuilder.classBuilder()
        .withPackageName("com.github.havardh.models")
        .withName("Class")
        .withField(FieldBuilder.fieldBuilder()
            .withName("someField")
            .withType(PRIMITIVE_INT)
            .build())
        .withGetter(MethodBuilder.methodBuilder()
            .withName("getSomeField")
            .withType(PRIMITIVE_INT)
            .build())
        .build();

    verifier.verify(singletonList(aClass));
  }

  @Test
  public void shouldFailForModelWithGetterWithNonMatchingType() {
    Class aClass = ClassBuilder.classBuilder()
        .withPackageName("com.github.havardh.models")
        .withName("Class")
        .withField(FieldBuilder.fieldBuilder()
            .withName("someField")
            .withType(PRIMITIVE_INT)
            .build())
        .withGetter(MethodBuilder.methodBuilder()
            .withName("getSomeField")
            .withType(PRIMITIVE_DOUBLE)
            .build())
        .build();

    AggregatedException exception = assertThrows(
        AggregatedException.class,
        () -> verifier.verify(singletonList(aClass))
    );

    assertThat(exception.getExceptions(), hasSize(1));
  }

  @Test
  public void shouldFailForModelWithGetterWithNonMatchingName() {
    Class aClass = ClassBuilder.classBuilder()
        .withPackageName("com.github.havardh.models")
        .withName("Class")
        .withField(FieldBuilder.fieldBuilder()
            .withName("someField")
            .withType(PRIMITIVE_INT)
            .build())
        .withGetter(MethodBuilder.methodBuilder()
            .withName("getSomeFields")
            .withType(PRIMITIVE_INT)
            .build())
        .build();

    AggregatedException exception = assertThrows(
        AggregatedException.class,
        () -> verifier.verify(singletonList(aClass))
    );

    assertThat(exception.getExceptions(), hasSize(1));
  }

  @Test
  public void shouldFailForModelWithoutGetters() {
    Class aClass = ClassBuilder.classBuilder()
        .withPackageName("com.github.havardh.models")
        .withName("Class")
        .withField(FieldBuilder.fieldBuilder()
            .withName("someField")
            .withType(PRIMITIVE_INT)
            .build())
        .build();

    AggregatedException exception = assertThrows(
        AggregatedException.class,
        () -> verifier.verify(singletonList(aClass))
    );

    assertThat(exception.getExceptions(), hasSize(1));
  }

  @Test
  public void shouldFailAndReportMultipleFieldsPerClass() {
    Class aClass = ClassBuilder.classBuilder()
        .withPackageName("com.github.havardh.models")
        .withName("Class")
        .withField(FieldBuilder.fieldBuilder()
            .withName("fieldOne")
            .withType(PRIMITIVE_INT)
            .build())
        .withField(FieldBuilder.fieldBuilder()
            .withName("fieldTwo")
            .withType(PRIMITIVE_INT)
            .build())
        .withGetter(MethodBuilder.methodBuilder().withName("getFieldOnes").withType(PRIMITIVE_INT).build())
        .withGetter(MethodBuilder.methodBuilder().withName("getFieldTwos").withType(PRIMITIVE_INT).build())
        .build();

    AggregatedException exception = assertThrows(
        AggregatedException.class,
        () -> verifier.verify(singletonList(aClass))
    );

    assertThat(exception.getExceptions(), hasSize(2));
  }

  @Test
  public void shouldFailAndReportOnMultipleErroneousClasses() {
    Class aClass = ClassBuilder.classBuilder()
        .withPackageName("com.github.havardh.models")
        .withName("aClass")
        .withField(FieldBuilder.fieldBuilder()
            .withName("fieldOne")
            .withType(PRIMITIVE_INT)
            .build())
        .build();
    Class bClass = ClassBuilder.classBuilder()
        .withPackageName("com.github.havardh.models")
        .withName("bClass")
        .withField(FieldBuilder.fieldBuilder()
            .withName("fieldOne")
            .withType(PRIMITIVE_INT)
            .build())
        .build();

    AggregatedException exception = assertThrows(
        AggregatedException.class,
        () -> verifier.verify(asList(aClass, bClass))
    );

    assertThat(exception.getExceptions(), hasSize(2));
  }
}
