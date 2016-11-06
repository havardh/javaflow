package no.havard.javaflow.phases.transform;

import static java.util.Arrays.asList;

import static no.havard.javaflow.ast.builders.ClassBuilder.classBuilder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import java.util.List;

import no.havard.javaflow.ast.Type;

import org.junit.jupiter.api.Test;

public class SortedTypeTransformerTest {

  private static final Type A = classBuilder().withName("A").build();
  private static final Type B = classBuilder().withName("b").build();
  private static final Type C = classBuilder().withName("C").build();

  private Transformer transformer = new SortedTypeTransformer();

  @Test
  public void shouldSortTypesBasedOnName() {
    List<Type> types = asList(C, B, A);

    transformer.transform(types);

    assertThat(types, contains(A, B, C));
  }

}
