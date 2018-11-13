package com.github.havardh.javaflow.phases.parser.java;

import static com.github.havardh.javaflow.ast.builders.ClassBuilder.classBuilder;
import static com.github.havardh.javaflow.ast.builders.EnumBuilder.enumBuilder;
import static com.github.havardh.javaflow.phases.parser.java.JavaParserFlags.defaultFlags;

import java.io.StringReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.havardh.javaflow.ast.Class;
import com.github.havardh.javaflow.ast.Type;
import com.github.havardh.javaflow.ast.builders.Builder;
import com.github.havardh.javaflow.exceptions.ExitException;
import com.github.havardh.javaflow.exceptions.ExitException.ErrorCode;
import com.github.havardh.javaflow.phases.parser.Parser;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;

/**
 * {@code Parser} for parsing Java source code into {@code Type}.
 */
public class JavaParser implements Parser {

  private final JavaParserFlags flags;

  public JavaParser() {
    flags = defaultFlags();
  }

  public JavaParser(JavaParserFlags flags) {
    this.flags = flags;
  }

  /**
   * Parse a Java model into the internal representation of a model.
   *
   * The Java model parser parses {@code class} or {@code enum} definitions
   * into a list of {@code Type} objects.
   *
   * @param source the Java source code for a model
   * @return the parsed source code as a {@code List<Type>}.
   */
  public List<Type> parse(String source) {

    try {
      CompilationUnit cu = com.github.javaparser.JavaParser.parse(new StringReader(source));
      return convert(cu);
    } catch (ParseProblemException e) {
      throw new ExitException(ErrorCode.COULD_NOT_PARSE_SOURCE_CODE, e);
    }
  }

  private List<Type> convert(CompilationUnit cu) {

    if (containsClass(cu)) {
      return convert(cu, classBuilder(), new ClassVisitor(flags.shouldIncludeStaticFields()));
    } else if (containsEnum(cu)) {
      return convert(cu, enumBuilder(), new EnumVisitor());
    } else {
      return Collections.emptyList();
    }
  }

  private static List<Type> convert(
      CompilationUnit cu,
      Builder<? extends Type> builder,
      VoidVisitor visitor
  ) {
    visitor.visit(cu, builder);
    return toTypes(builder.build()).collect(Collectors.toList());
  }

  private static Stream<Type> toTypes(Type type) {
    if (type instanceof Class) {
      return Stream.concat(Stream.of(type), ((Class) type).getInnerClasses().stream().flatMap(JavaParser::toTypes));
    } else {
      return Stream.of(type);
    }
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

