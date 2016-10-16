package no.havard.javaflow.convertion;

import static java.util.Optional.of;

import java.util.Optional;

import no.havard.javaflow.model.ClassDefinitionBuilder;
import no.havard.javaflow.model.Definition;
import no.havard.javaflow.model.EnumDefinitionBuilder;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;

public final class CompilationUnitConverter {

  private static ClassVisitor classVisitor = new ClassVisitor();
  private static EnumVisitor enumVisitor = new EnumVisitor();

  public static Optional<Definition> convert(CompilationUnit cu) {

    if (containsClass(cu)) {
      ClassDefinitionBuilder builder = ClassDefinitionBuilder.builder();
      classVisitor.visit(cu, builder);
      return of(builder.build());
    } else if (containsEnum(cu)) {
      EnumDefinitionBuilder builder = EnumDefinitionBuilder.builder();
      enumVisitor.visit(cu, builder);
      return of(builder.build());
    }

    return Optional.empty();
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

