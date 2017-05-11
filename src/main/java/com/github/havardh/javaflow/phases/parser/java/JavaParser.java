package com.github.havardh.javaflow.phases.parser.java;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import static com.github.havardh.javaflow.ast.builders.EnumBuilder.enumBuilder;

import java.io.StringReader;
import java.util.Optional;

import com.github.havardh.javaflow.ast.Type;
import com.github.havardh.javaflow.ast.builders.Builder;
import com.github.havardh.javaflow.phases.parser.Parser;

import com.github.havardh.javaflow.ast.builders.ClassBuilder;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;

public class JavaParser implements Parser {


    try {
      CompilationUnit cu = com.github.javaparser.JavaParser.parse(new StringReader(source));
      return convert(cu);
    } catch (ParseException e) {
      e.printStackTrace();
      System.exit(1);
    }

    return empty();
  }

  private static Optional<Type> convert(CompilationUnit cu) {

    if (containsClass(cu)) {
      return of(convert(cu, ClassBuilder.classBuilder(), new ClassVisitor()));
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

