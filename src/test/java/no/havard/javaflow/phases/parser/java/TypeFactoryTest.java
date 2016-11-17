package no.havard.javaflow.phases.parser.java;

import static no.havard.javaflow.util.Maps.collect;
import static no.havard.javaflow.util.Maps.entry;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import no.havard.javaflow.ast.List;
import no.havard.javaflow.ast.Type;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class TypeFactoryTest {

  @Nested
  public class Collections {

    private TypeFactory factory = new TypeFactory("no.havard", collect(entry("Collection", "java.util")));

    @Test
    public void shouldBeAnInstanceOfList() {
      Type type = factory.build("Collection<String>", false);

      assertThat(type, instanceOf(List.class));
    }

    @Test
    public void shouldSetSubType() {
      List list = (List)factory.build("Collection<String>", false);

      assertThat(list.getType().getName(), is("String"));
    }
  }



}
