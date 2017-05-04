package com.github.havardh.javaflow.phases.filetransform;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Collections;

import org.junit.jupiter.api.Test;

class EslintDisableTransformerTest {

  @Test
  public void shouldAppendEslintDisable() {
    FileTransformer transformer = new EslintDisableTransformer(Collections.singletonList(
        "no-unused-expressions"
    ));

    String output = transformer.transform("");

    assertThat(output, is("/* eslint-disable no-unused-expressions */\n"));
  }

}