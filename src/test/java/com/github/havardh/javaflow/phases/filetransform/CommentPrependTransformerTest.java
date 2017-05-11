package com.github.havardh.javaflow.phases.filetransform;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.Test;

class CommentPrependTransformerTest {

  @Test
  public void shouldPrependFileWithComment() {
    CommentPrependTransformer transformer = new CommentPrependTransformer("comment");

    String output = transformer.transform("");

    assertThat(output, equalTo("/* comment */\n"));
  }

}
