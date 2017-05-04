package com.github.havardh.javaflow.phases.transform;

import static java.util.Arrays.asList;

import static com.github.havardh.javaflow.ast.builders.EnumBuilder.enumBuilder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.List;

import com.github.havardh.javaflow.ast.Parent;
import com.github.havardh.javaflow.ast.Type;
import com.github.havardh.javaflow.ast.Class;
import com.github.havardh.javaflow.model.CanonicalName;

import org.junit.jupiter.api.Test;

import com.github.havardh.javaflow.ast.builders.ClassBuilder;

public class InheritanceTransformerTest {

  private Transformer transformer = new InheritanceTransformer();

  @Test
  public void shouldKeepAllTypes() {
    List<Type> types = asList(
        ClassBuilder.classBuilder().build(),
        enumBuilder().build()
    );

    transformer.transform(types);

    assertThat(types, hasSize(2));
  }

  @Test
  public void shouldLinkClassesWithParentOnMatchingName() {
    Class parent = ClassBuilder.classBuilder()
        .withName("Parent")
        .withPackageName("com.github.havardh.a")
        .build();

    Class child = ClassBuilder.classBuilder()
        .withName("Child")
        .withParent(new Parent(CanonicalName.object("com.github.havardh.a", "Parent")))
        .build();

    transformer.transform(asList(parent, child));

    assertThat(child.getParent().get().getReference(), is(parent));
  }

  @Test
  public void shouldResolveNameConflictsWithPackageName() {
    Class parentA = ClassBuilder.classBuilder()
        .withName("Parent")
        .withPackageName("com.github.havardh.a")
        .build();

    Class parentB = ClassBuilder.classBuilder()
        .withName("Parent")
        .withPackageName("com.github.havardh.b")
        .build();

    Class child = ClassBuilder.classBuilder()
        .withName("Child")
        .withParent(new Parent(CanonicalName.object("com.github.havardh.a", "Parent")))
        .build();

    transformer.transform(asList(parentA, parentB, child));

    assertThat(child.getParent().get().getReference(), is(parentA));
  }

}
