package no.havard.javaflow;

import static java.lang.String.format;

import static no.havard.javaflow.convertion.CompilationUnitConverter.convert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import no.havard.javaflow.convertion.JavaFlowTypeConversion;
import no.havard.javaflow.model.Definition;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;

public class JavaFlow {

  public static void main(String args[]) {
    JavaFlowTypeConversion.init();

    Stream.of(args).forEach(JavaFlow::parseAndPrint);
  }

  public static void parseAndPrint(String filename) {
    System.out.println(format("// %s", filename));

    Definition definition = parse(filename)
      .orElseThrow(() -> new IllegalArgumentException("Could not convert: " + filename));

    System.out.println(definition);
    System.out.println();

  }

  public static Optional<Definition> parse(String file) {
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
