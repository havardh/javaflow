package com.github.havardh.javaflow.phases.transform;

import static java.util.Arrays.asList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import java.util.List;

import com.github.havardh.javaflow.ast.Type;

import org.junit.jupiter.api.Test;

import com.github.havardh.javaflow.ast.builders.ClassBuilder;

public class SortedTypeTransformerTest {

  private static final Type A = ClassBuilder.classBuilder().withName("A").build();
  private static final Type B = ClassBuilder.classBuilder().withName("b").build();
  private static final Type C = ClassBuilder.classBuilder().withName("C").build();

  private Transformer transformer = new SortedTypeTransformer();

  @Test
  public void shouldSortTypesBasedOnName() {
    List<Type> types = asList(C, B, A);

    transformer.transform(types);

    assertThat(types, contains(A, B, C));
  }

}
