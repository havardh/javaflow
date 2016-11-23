package no.havard.javaflow.phases.verifier;

import static java.util.Collections.singletonList;

import static no.havard.javaflow.ast.builders.FieldBuilder.fieldBuilder;
import static no.havard.javaflow.ast.builders.TypeBuilder.type;
import static no.havard.javaflow.util.TypeMap.emptyTypeMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.expectThrows;

import java.util.List;
import java.util.Map;

import no.havard.javaflow.ast.Class;
import no.havard.javaflow.ast.Field;
import no.havard.javaflow.ast.Type;
import no.havard.javaflow.ast.builders.ClassBuilder;
import no.havard.javaflow.exceptions.MissingTypeException;
import no.havard.javaflow.model.CanonicalName;

import org.junit.jupiter.api.Test;

public class MemberFieldsPresentVerifierTest {

  private Verifier verifier = new MemberFieldsPresentVerifier(emptyTypeMap());

  @Test
  public void shouldPassTypeWithoutMembers() {
    verifier.verify(singletonList(type()
        .withName(CanonicalName.fromString("no.havard.Test1"))
        .build()));
  }

  @Test
  public void shouldPassForBuiltInTypes() {
    Class aClass = ClassBuilder.classBuilder()
        .withName("Test1")
        .withField(fieldBuilder()
            .withName("name")
            .withType(type()
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
        .withType(type()
            .withName(CanonicalName.fromString("no.havard.Type"))
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
