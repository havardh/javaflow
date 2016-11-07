package no.havard.javaflow.phases.reader.java;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import static no.havard.javaflow.ast.builders.ClassBuilder.classBuilder;
import static no.havard.javaflow.ast.builders.EnumBuilder.enumBuilder;

import static com.github.javaparser.JavaParser.parse;

import java.io.StringReader;
import java.util.Optional;

import no.havard.javaflow.ast.Type;
import no.havard.javaflow.ast.builders.Builder;
import no.havard.javaflow.phases.reader.Reader;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;

public class JavaReader implements Reader {

  public Optional<Type> read(String file) {

    try {
      CompilationUnit cu = parse(new StringReader(file));
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

