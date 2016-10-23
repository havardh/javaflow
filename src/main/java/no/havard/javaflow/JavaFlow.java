package no.havard.javaflow;

import static java.util.stream.Collectors.toList;

import static no.havard.javaflow.convertion.CompilationUnitConverter.convert;
import static no.havard.javaflow.convertion.FileSetHandler.handleExtends;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import no.havard.javaflow.convertion.JavaFlowTypeConversion;
import no.havard.javaflow.model.Definition;
import no.havard.javaflow.phases.writer.FlowWriter;
import no.havard.javaflow.phases.writer.Writer;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;

public class JavaFlow {

  private static Writer<Definition> writer = new FlowWriter();

  public static void main(String args[]) {
    JavaFlowTypeConversion.init();

    List<Definition> definitions = parseAll(args);
    handleExtends(definitions);
    definitions.stream().forEach(writer::write);
  }

  public static List<Definition> parseAll(String[] filenames) {
    return Stream.of(filenames)
        .map(JavaFlow::parse)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(toList());
  }

  static Optional<Definition> parse(String file) {
    try (FileInputStream inputStream = new FileInputStream(file)) {
      CompilationUnit compilationUnit = JavaParser.parse(inputStream);

      return convert(compilationUnit);

    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.exit(0);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(0);
    } catch (ParseException e) {
      e.printStackTrace();
      System.exit(0);
    }
    return Optional.empty();
  }
}
