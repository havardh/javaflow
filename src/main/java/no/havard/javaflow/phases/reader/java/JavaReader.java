package no.havard.javaflow.phases.reader.java;

import static java.util.Optional.of;

import static no.havard.javaflow.ast.builders.ClassBuilder.classBuilder;
import static no.havard.javaflow.ast.builders.EnumBuilder.enumBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import no.havard.javaflow.ast.Type;
import no.havard.javaflow.ast.builders.Builder;
import no.havard.javaflow.phases.reader.Reader;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;

public class JavaReader implements Reader {

  private static ClassVisitor classVisitor = new ClassVisitor();
  private static EnumVisitor enumVisitor = new EnumVisitor();

  public Optional<Type> read(String file) {
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

  private static Optional<Type> convert(CompilationUnit cu) {

    if (containsClass(cu)) {
      return of(convert(cu, classBuilder(), classVisitor));
    } else if (containsEnum(cu)) {
      return of(convert(cu, enumBuilder(), enumVisitor));
    } else {
      return Optional.empty();
    }
  }

  private static <T extends Type> T convert(
      CompilationUnit cu,
      Builder<T> builder,
      VoidVisitor visitor
  ) {
    visitor.visit(cu, builder);
    return builder.build();
  }

  private static boolean containsEnum(CompilationUnit cu) {
    return cu.getTypes().stream()
        .filter(t -> t instanceof EnumDeclaration)
        .findFirst()
        .isPresent();
  }

  private static boolean containsClass(CompilationUnit cu) {
    return cu.getTypes().stream()
        .filter(t -> t instanceof ClassOrInterfaceDeclaration)
        .findFirst()
        .isPresent();
  }

}

