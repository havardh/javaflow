package no.havard.javaflow.phases.writer;

import static java.lang.String.format;
import static java.util.Arrays.asList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public class WriterTest<T> {

  private final Writer<T> writer;

  public WriterTest(Writer<T> writer) {
    this.writer = writer;
  }

  protected String toFlow(T t) throws IOException {
    StringWriter stringWriter = new StringWriter();
    writer.write(t, stringWriter);
    return stringWriter.toString();
  }

  protected void assertStringEqual(String actual, String... expected) {
    List<String> actuals = asList(actual.split("\n"));
    int actualSize = actuals.size();
    assertThat(asList(expected), hasSize(actualSize));

    for (int i=0; i<actualSize; i++) {
      assertThat(format("Mismatch on line %d", i), expected[i], is(actuals.get(i)));
    }
  }

}

