package com.github.havardh.javaflow.phases.parser.java;

import static java.util.Optional.of;

import static com.github.havardh.javaflow.ast.builders.ClassBuilder.classBuilder;
import static com.github.havardh.javaflow.ast.builders.EnumBuilder.enumBuilder;

import java.io.StringReader;
import java.util.Optional;

import com.github.havardh.javaflow.ast.Type;
import com.github.havardh.javaflow.ast.builders.Builder;
import com.github.havardh.javaflow.exceptions.ExitException;
import com.github.havardh.javaflow.exceptions.ExitException.ErrorCode;
import com.github.havardh.javaflow.phases.parser.Parser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;

/**
 * {@code Parser} for parsing Java source code into {@code Type}.
 */
public class JavaParser implements Parser {

  /**
   * Parse a Java model into the internal representation of a model.
   *
   * The Java model parser parses {@code class} or {@code enum} definitions
   * into a {@code Type}.
   *
   * @param source the Java source code for a model
   * @return the parsed source code as a {@code Type}.
   */
  public Optional<Type> parse(String source) {

    try {
      CompilationUnit cu = com.github.javaparser.JavaParser.parse(new StringReader(source));
      return convert(cu);
    } catch (ParseException e) {
      throw new ExitException(ErrorCode.COULD_NOT_PARSE_SOURCE_CODE, e);
    }
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
        .anyMatch(t -> t instanceof EnumDeclaration);
  }

  private static boolean containsClass(CompilationUnit cu) {
    return cu.getTypes().stream()
        .anyMatch(t -> t instanceof ClassOrInterfaceDeclaration);
  }

}

