package com.github.havardh.javaflow.phases.verifier;

import static java.util.Collections.singletonList;

import static com.github.havardh.javaflow.ast.builders.FieldBuilder.fieldBuilder;
import static com.github.havardh.javaflow.model.TypeMap.emptyTypeMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.expectThrows;

import java.util.List;
import java.util.Map;

import com.github.havardh.javaflow.ast.Class;
import com.github.havardh.javaflow.ast.Field;
import com.github.havardh.javaflow.ast.Type;
import com.github.havardh.javaflow.ast.builders.ClassBuilder;
import com.github.havardh.javaflow.exceptions.MissingTypeException;
import com.github.havardh.javaflow.model.CanonicalName;

import org.junit.jupiter.api.Test;

import com.github.havardh.javaflow.ast.builders.TypeBuilder;

public class MemberFieldsPresentVerifierTest {

  private Verifier verifier = new MemberFieldsPresentVerifier(emptyTypeMap());

  @Test
  public void shouldPassTypeWithoutMembers() {
    verifier.verify(singletonList(TypeBuilder.type()
        .withName(CanonicalName.fromString("com.github.havardh.Test1"))
        .build()));
  }

  @Test
  public void shouldPassForBuiltInTypes() {
    Class aClass = ClassBuilder.classBuilder()
        .withName("Test1")
        .withField(fieldBuilder()
            .withName("name")
            .withType(TypeBuilder.type()
                .withName(CanonicalName.fromString("java.lang.String"))
                .build())
            .build())
        .build();

    verifier.verify(singletonList(aClass));
  }

  @Test
  public void shouldFailTypeWithMissingMember() {
    Field field = fieldBuilder()
        .withName("name")
        .withType(TypeBuilder.type()
            .withName(CanonicalName.fromString("com.github.havardh.Type"))
            .build())
        .build();

    Class aClass = ClassBuilder.classBuilder()
        .withName("Test1")
        .withField(field)
        .build();

    MissingTypeException exception = expectThrows(MissingTypeException.class, () ->
        verifier.verify(singletonList(aClass))
    );

    Map<Type, List<Field>> missing = exception.getTypes();

    assertThat(missing.get(aClass), is(singletonList(field)));

  }

}
