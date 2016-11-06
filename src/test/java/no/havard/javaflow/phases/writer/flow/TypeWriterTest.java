package no.havard.javaflow.phases.writer.flow;

import static no.havard.javaflow.model.fixtures.TypeFixtures.listType;
import static no.havard.javaflow.model.fixtures.TypeFixtures.mapType;
import static no.havard.javaflow.model.fixtures.TypeFixtures.stringType;
import static no.havard.javaflow.testutil.MapConverterFixture.stringMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.IOException;

import no.havard.javaflow.ast.Type;
import no.havard.javaflow.phases.writer.WriterTest;

import org.junit.jupiter.api.Test;

public class TypeWriterTest extends WriterTest<Type> {

  public TypeWriterTest() {
    super(new TypeWriter(stringMap()
        .with("java.util.List", "Array")
        .build()));
  }

  @Test
  public void shouldSerializeType() throws IOException {
    String flow = toFlow(stringType().build());

    assertThat(flow, is("string"));
  }

  @Test
  public void shouldSerializeListType() throws IOException {
    String flow = toFlow(listType().build());

    assertThat(flow, is("Array<string>"));
  }

  @Test
  public void shouldSerializeMapType() throws IOException {
    String flow = toFlow(mapType().build());

    assertThat(flow, is("{[key: string]: string}"));
  }
}

