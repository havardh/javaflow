package no.havard.javaflow.phases.parser.java;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import static no.havard.javaflow.ast.builders.ClassBuilder.classBuilder;
import static no.havard.javaflow.ast.builders.EnumBuilder.enumBuilder;

import java.io.StringReader;
import java.util.Optional;

import no.havard.javaflow.ast.Type;
import no.havard.javaflow.ast.builders.Builder;
import no.havard.javaflow.phases.parser.Parser;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;

public class JavaParser implements Parser {

  public Optional<Type> parse(String file) {

    try {
      CompilationUnit cu = com.github.javaparser.JavaParser.parse(new StringReader(file));
      return convert(cu);
    } catch (ParseException e) {
      e.printStackTrace();
      System.exit(1);
    }

    return empty();
  }

  private static Optional<Type> convert(CompilationUnit cu) {

    if (containsClass(cu)) {
      return of(convert(cu, classBuilder(), new ClassVisitor()));
    } else if (containsEnum(cu)) {
      return of(convert(cu, enumBuilder(), new EnumVisitor()));
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

