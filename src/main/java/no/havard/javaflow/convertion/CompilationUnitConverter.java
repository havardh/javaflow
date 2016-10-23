package no.havard.javaflow.convertion;

import static java.util.Optional.of;

import static no.havard.javaflow.model.builders.ClassDefinitionBuilder.classDefinitionBuilder;
import static no.havard.javaflow.model.builders.EnumDefinitionBuilder.enumDefinitionBuilder;

import java.util.Optional;

import no.havard.javaflow.model.builders.Builder;
import no.havard.javaflow.model.Definition;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;

public final class CompilationUnitConverter {

  private static ClassVisitor classVisitor = new ClassVisitor();
  private static EnumVisitor enumVisitor = new EnumVisitor();

  public static Optional<Definition> convert(CompilationUnit cu) {

    if (containsClass(cu)) {
      return of(convert(cu, classDefinitionBuilder(), classVisitor));
    } else if (containsEnum(cu)) {
      return of(convert(cu, enumDefinitionBuilder(), enumVisitor));
    } else {
      return Optional.empty();
    }
  }

  private static <T extends Definition> T convert(
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

