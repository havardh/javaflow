package no.havard.javaflow;

import static no.havard.javaflow.DefinitionBuilder.DefinitionType.Class;
import static no.havard.javaflow.DefinitionBuilder.DefinitionType.Enum;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class JavaFlow {

  public static void main(String args[]) {
    JavaFlowTypeConversion.init();
    System.out.println(parse(args[0]));
  }

  public static Definition parse(String file) {
    try (FileInputStream inputStream = new FileInputStream(file)) {
      CompilationUnit compilationUnit = JavaParser.parse(inputStream);

      DefinitionBuilder builder = DefinitionBuilder.builder();

      new MemberVisitor().visit(compilationUnit, builder);

      return builder.build();

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
    return null;
  }


  private static class MemberVisitor extends VoidVisitorAdapter<DefinitionBuilder> {

    @Override
    public void visit(EnumDeclaration n, DefinitionBuilder builder) {
      super.visit(n, builder);
      builder.withType(Enum);
      builder.withName(n.getName());

      n.getEntries().stream().forEach(dec -> builder.withEnumValue(dec.getName()));
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration n, DefinitionBuilder builder) {
      super.visit(n, builder);
      builder.withName(n.getName());
      builder.withType(Class);
    }

    @Override
    public void visit(FieldDeclaration field, DefinitionBuilder builder) {
      super.visit(field, builder);

      field.getVariables().stream()
          .forEach(variable -> builder.withField(field.getType(), variable.getId().getName()));
    }
  }

}
