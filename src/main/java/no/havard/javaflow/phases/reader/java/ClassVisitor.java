package no.havard.javaflow.phases.reader.java;

import static java.util.stream.Collectors.joining;

import static no.havard.javaflow.phases.reader.java.TypeFactory.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import no.havard.javaflow.model.FieldDefinition;
import no.havard.javaflow.model.builders.ClassDefinitionBuilder;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ClassVisitor extends VoidVisitorAdapter<ClassDefinitionBuilder> {

  private String packageName;
  private Map<String, String> imports = new HashMap<>();

  @Override
  public void visit(PackageDeclaration n, ClassDefinitionBuilder builder) {
    super.visit(n, builder);

    packageName = n.getPackageName();
    builder.withPackageName(n.getPackageName());
  }

  @Override
  public void visit(ImportDeclaration n, ClassDefinitionBuilder builder) {
    super.visit(n, builder);

    String[] packages = n.getName().toString().split("\\.");
    String typeName = n.getName().getName();
    String packageName = Stream.of(packages).limit(packages.length-1).collect(joining("."));

    imports.put(typeName, packageName);
    builder.withImport(typeName, packageName);
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

    field.getVariables().forEach(variable -> builder.withField(new FieldDefinition(
        isNullable(field),
        variable.getId().getName(),
        factory(packageName, imports).of(field.getType())
    )));
  }

  private boolean isNullable(FieldDeclaration field) {
    return field.getAnnotations().stream()
        .map(AnnotationExpr::getName)
        .map(NameExpr::getName)
        .filter(name -> name.equals("Nullable"))
        .count() > 0;
  }

}
