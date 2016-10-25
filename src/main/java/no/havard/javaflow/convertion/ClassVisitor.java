package no.havard.javaflow.convertion;

import static java.util.stream.Collectors.joining;

import java.util.stream.Stream;

import no.havard.javaflow.model.builders.ClassDefinitionBuilder;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ClassVisitor extends VoidVisitorAdapter<ClassDefinitionBuilder> {

  @Override
  public void visit(PackageDeclaration n, ClassDefinitionBuilder builder) {
    super.visit(n, builder);
    builder.withPackageName(n.getPackageName());
  }

  @Override
  public void visit(ImportDeclaration n, ClassDefinitionBuilder builder) {
    super.visit(n, builder);

    String[] packages = n.getName().toString().split("\\.");
    builder.withImport(
        n.getName().getName(),
        Stream.of(packages).limit(packages.length-1).collect(joining("."))
    );
  }

  @Override
  public void visit(ClassOrInterfaceDeclaration n, ClassDefinitionBuilder builder) {
    super.visit(n, builder);
    builder.withName(n.getName());

    n.getExtends().stream().findFirst()
        .ifPresent(parent -> builder.withParent(parent.getName()));
  }

  @Override
  public void visit(FieldDeclaration field, ClassDefinitionBuilder builder) {
    super.visit(field, builder);

    field.getVariables().stream()
      .forEach(variable -> builder.withField(field.getType().toString(), variable.getId().getName()));
  }
}
