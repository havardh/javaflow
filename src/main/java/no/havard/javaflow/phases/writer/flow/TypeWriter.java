package no.havard.javaflow.phases.writer.flow;

import static no.havard.javaflow.phases.writer.flow.JavaFlowTypeConversion.toFlow;

import java.io.IOException;

import no.havard.javaflow.ast.List;
import no.havard.javaflow.ast.Map;
import no.havard.javaflow.ast.Type;
import no.havard.javaflow.phases.writer.Writer;

public class TypeWriter implements Writer<Type> {

  @Override
  public void write(Type type, java.io.Writer writer) throws IOException {
    if (type instanceof Map) {
      write((Map) type, writer);
    } else if (type instanceof List) {
      write((List) type, writer);
    } else {
      writer.write(toFlow(type.getCanonicalName()));
    }
  }

  private void write(Map map, java.io.Writer writer) throws IOException {
    writer.write("{[key: ");
    writer.write(toFlow(map.getKeyType()));
    writer.write("]: ");
    writer.write(toFlow(map.getValueType()));
    writer.write("}");
  }

  private void write(List list, java.io.Writer writer) throws IOException {
    writer.write(toFlow(list.getCanonicalName()));
    writer.write("<");
    writer.write(toFlow(list.getType()));
    writer.write(">");
  }
}

