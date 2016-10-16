package no.havard.javaflow;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import no.havard.javaflow.convertion.JavaFlowTypeConversion;
import no.havard.javaflow.convertion.MemberVisitor;
import no.havard.javaflow.model.Definition;
import no.havard.javaflow.model.DefinitionBuilder;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;

public class JavaFlow {

  public static void main(String args[]) {
    JavaFlowTypeConversion.init();
    System.out.println(parse(args[0]));
  }

  public static Definition parse(String file) {
    try (FileInputStream inputStream = new FileInputStream(file)) {
      CompilationUnit compilationUnit = JavaParser.parse(inputStream);

      DefinitionBuilder builder = DefinitionBuilder.builder();

      new MemberVisitor().visit(compilationUnit, builder);

      return builder.build();

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
    return null;
  }


}
