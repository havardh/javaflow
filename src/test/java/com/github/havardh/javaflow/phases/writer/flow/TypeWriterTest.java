package com.github.havardh.javaflow.phases.writer.flow;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.IOException;

import com.github.havardh.javaflow.ast.Type;
import com.github.havardh.javaflow.phases.writer.WriterTest;

import org.junit.jupiter.api.Test;

import com.github.havardh.javaflow.model.fixtures.TypeFixtures;
import com.github.havardh.javaflow.testutil.MapConverterFixture;

public class TypeWriterTest extends WriterTest<Type> {

  public TypeWriterTest() {
    super(new TypeWriter(MapConverterFixture.stringMap()
        .with("java.util.List", "Array")
        .build()));
  }

  @Test
  public void shouldSerializeType() throws IOException {
    String flow = toFlow(TypeFixtures.stringType().build());

    assertThat(flow, is("string"));
  }

  @Test
  public void shouldSerializeListType() throws IOException {
    String flow = toFlow(TypeFixtures.listType().build());

    assertThat(flow, is("Array<string>"));
  }

  @Test
  public void shouldSerializeMapType() throws IOException {
    String flow = toFlow(TypeFixtures.mapType().build());

    assertThat(flow, is("{[key: string]: string}"));
  }
}

