package no.havard.javaflow.phases.filetransform;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.Test;

class CommentAppendTransformerTest {

  @Test
  public void shouldPrependFileWithComment() {
    CommentAppendTransformer transformer = new CommentAppendTransformer("comment");

    String output = transformer.transform("");

    assertThat(output, equalTo("/* comment */\n"));
  }

}
