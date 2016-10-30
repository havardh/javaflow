package no.havard.javaflow.phases.writer.flow;

import static no.havard.javaflow.phases.writer.flow.JavaFlowTypeConversion.toFlow;

import java.io.IOException;

import no.havard.javaflow.model.Type;
import no.havard.javaflow.phases.writer.Writer;

public class TypeWriter implements Writer<Type> {

  @Override
  public void write(Type type, java.io.Writer writer) throws IOException {
    if (type instanceof Type.MapType) {
      write((Type.MapType) type, writer);
    } else if (type instanceof Type.ListType) {
      write((Type.ListType) type, writer);
    } else {
      writer.write(toFlow(type.getCanonicalName()));
    }
  }

  private void write(Type.MapType map, java.io.Writer writer) throws IOException {
    writer.write("{[key: ");
    writer.write(toFlow(map.getKeyType()));
    writer.write("]: ");
    writer.write(toFlow(map.getValueType()));
    writer.write("}");
  }

  private void write(Type.ListType list, java.io.Writer writer) throws IOException {
    writer.write(toFlow(list.getCanonicalName()));
    writer.write("<");
    writer.write(toFlow(list.getType()));
    writer.write(">");
  }
}

