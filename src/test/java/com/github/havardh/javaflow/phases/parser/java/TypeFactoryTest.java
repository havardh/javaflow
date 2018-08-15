package com.github.havardh.javaflow.phases.parser.java;

import static com.github.havardh.javaflow.util.Maps.collect;
import static com.github.havardh.javaflow.util.Maps.entry;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import com.github.havardh.javaflow.ast.List;
import com.github.havardh.javaflow.ast.Type;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class TypeFactoryTest {

  @Nested
  public class Collections {

    private TypeFactory factory = new TypeFactory("com.github.havardh", collect(entry("Collection", "java.util")));

    @Test
    public void shouldBeAnInstanceOfList() {
      Type type = factory.build("Collection<String>", false);

      assertThat(type, instanceOf(List.class));
    }

    @Test
    public void shouldBeAnInstanceOfListWhenSetType() {
      Type type = factory.build("Set<String>", false);

      assertThat(type, instanceOf(List.class));
    }

    @Test
    public void shouldSetSubType() {
      List list = (List)factory.build("Collection<String>", false);

      assertThat(list.getType().getCanonicalName().getName(), is("String"));
    }

    @Test
    public void shouldSetSubTypeOfSubType() {
      List list = (List)factory.build("Collection<Collection<String>>", false);
      List subtypeList = (List)list.getType();

      assertThat(subtypeList.getType().getCanonicalName().getName(), is("String"));
    }
  }



}
