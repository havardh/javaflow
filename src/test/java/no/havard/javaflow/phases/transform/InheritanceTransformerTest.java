package no.havard.javaflow.phases.transform;

import static java.util.Arrays.asList;

import static no.havard.javaflow.ast.builders.ClassBuilder.classBuilder;
import static no.havard.javaflow.ast.builders.EnumBuilder.enumBuilder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.List;

import no.havard.javaflow.ast.Type;

import org.junit.jupiter.api.Test;

public class InheritanceTransformerTest {

  private Transformer transformer = new InheritanceTransformer();

  @Test
  public void shouldKeepAllTypes() {
    List<Type> types = asList(
        classBuilder().build(),
        enumBuilder().build()
    );

    transformer.transform(types);

    assertThat(types, hasSize(2));
  }

  @Test
  public void shouldLinkClassesWithParentOnMatchingName() {
    no.havard.javaflow.ast.Class parent = classBuilder()
        .withName("Parent")
        .build();

    no.havard.javaflow.ast.Class child = classBuilder()
        .withName("Child")
        .withParent("Parent")
        .build();

    transformer.transform(asList(parent, child));

    assertThat(child.getParent().get().getReference(), is(parent));
  }

}
