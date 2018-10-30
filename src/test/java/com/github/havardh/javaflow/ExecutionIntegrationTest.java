package com.github.havardh.javaflow;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.fail;

import static com.github.havardh.javaflow.model.TypeMap.emptyTypeMap;
import static com.github.havardh.javaflow.testutil.Assertions.assertStringEqual;

import com.github.havardh.javaflow.phases.resolver.FileResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.havardh.javaflow.exceptions.AggregatedException;
import com.github.havardh.javaflow.exceptions.MissingTypeException;
import com.github.havardh.javaflow.phases.parser.java.JavaParser;
import com.github.havardh.javaflow.phases.reader.FileReader;
import com.github.havardh.javaflow.phases.transform.InheritanceTransformer;
import com.github.havardh.javaflow.phases.transform.SortedTypeTransformer;
import com.github.havardh.javaflow.phases.verifier.ClassGetterNamingVerifier;
import com.github.havardh.javaflow.phases.verifier.MemberFieldsPresentVerifier;
import com.github.havardh.javaflow.phases.writer.flow.FlowWriter;
import com.github.havardh.javaflow.phases.writer.flow.converter.JavaFlowConverter;

public class ExecutionIntegrationTest {

  private static final String BASE_PATH = "src/test/java/com/github/havardh/javaflow/model/";

  private Execution execution;

  @BeforeEach
  public void setup() {
    this.execution = new Execution(
        new FileResolver(),
        new FileReader(),
        new JavaParser(),
        asList(
            new InheritanceTransformer(),
            new SortedTypeTransformer()
        ),
        asList(new MemberFieldsPresentVerifier(emptyTypeMap()), new ClassGetterNamingVerifier()),
        new FlowWriter(new JavaFlowConverter()),
        emptyList()
    );
  }

  @Test
  public void shouldParseModel() {
    String flowCode = execution.run(BASE_PATH + "Model.java");

    assertStringEqual(
        flowCode,
        "export type Model = {",
        "  yolo: string,",
        "};"
    );
  }

  @Test
  public void shouldParseEnum() {
    String flowCode = execution.run(BASE_PATH + "Enumeration.java");

    assertStringEqual(
        flowCode,
        "export type Enumeration =",
        "  | \"ONE\"",
        "  | \"TWO\";"
    );
  }

  @Test
  public void shouldParseModelWithList() {
    String flowCode = execution.run(BASE_PATH + "ModelWithList.java");

    assertStringEqual(
        flowCode,
        "export type ModelWithList = {",
        "  words: Array<string>,",
        "};"
    );
  }

  @Test
  public void shouldParseModelWithMap() {
    String flowCode = execution.run(BASE_PATH + "ModelWithMap.java");

    assertStringEqual(
        flowCode,
        "export type ModelWithMap = {",
        "  field: {[key: string]: number},",
        "};"
    );
  }

  @Test
  public void shouldParseModelWithMapGenericValue() {
    String flowCode = execution.run(BASE_PATH + "ModelWithMapGenericValue.java");

    assertStringEqual(
        flowCode,
        "export type ModelWithMapGenericValue = {",
        "  field: {[key: string]: Array<number>},",
        "  multipleNestedField: {[key: string]: Array<Array<{[key: string]: number}>>},",
        "};"
    );
  }

  @Test
  public void shouldParseModelWithGenericMapKey() {
    String flowCode = execution.run(BASE_PATH + "ModelWithGenericMapKey.java");

    assertStringEqual(
        flowCode,
        "export type ModelWithGenericMapKey = {",
        "  field: {[key: Array<number>]: number},",
        "};"
    );
  }

  @Test
  public void shouldParseModelWithCollection() {
    String flowCode = execution.run(BASE_PATH + "ModelWithCollection.java");

    assertStringEqual(
        flowCode,
        "export type ModelWithCollection = {",
        "  strings: Array<string>,",
        "};"
    );
  }

  @Test
  public void shouldParseModelWithCharArray() {
    String flowCode = execution.run(BASE_PATH + "ModelWithCharArray.java");

    assertStringEqual(
        flowCode,
        "export type ModelWithCharArray = {",
        "  field: string,",
        "};"
    );
  }

  @Test
  public void shouldParseModelWithArrays() {
    String flowCode = execution.run(BASE_PATH + "ModelWithGenericArrays.java");

    assertStringEqual(
        flowCode,
        "export type ModelWithGenericArrays = {",
        "  field1: Array<string>,",
        "  field2: Array<number>,",
        "};"
    );
  }

  @Test
  public void shouldParseModelWithSet() {
    String flowCode = execution.run(BASE_PATH + "ModelWithSet.java");

    assertStringEqual(
        flowCode,
        "export type ModelWithSet = {",
        "  strings: Array<string>,",
        "};"
    );
  }

  @Test
  public void shouldParseModelWithMember() {
    String flowCode = execution.run(
        BASE_PATH + "Wrapper.java",
        BASE_PATH + "Member.java",
        BASE_PATH + "packaged/PackagedMember.java"
    );

    assertStringEqual(
        flowCode,
        "export type Member = {};",
        "",
        "export type PackagedMember = {};",
        "",
        "export type Wrapper = {",
        "  member: Member,",
        "  packagedMember: PackagedMember,",
        "};"
    );
  }

  @Test
  public void shouldParseModelWithAnonymousClass() {
    String flowCode = execution.run(
        BASE_PATH + "ModelWithAnonymousClass.java",
        BASE_PATH + "Member.java"
    );

    assertStringEqual(
        flowCode,
        "export type Member = {};",
        "",
        "export type ModelWithAnonymousClass = {",
        "  field: Member,",
        "};"
    );
  }

  @Test
  public void shouldParseModelWithInnerClasses() {
    String flowCode = execution.run(BASE_PATH + "ModelWithInnerClasses.java");

    assertStringEqual(
        flowCode,
        "export type ModelWithInnerClasses = {",
        "  child1: Child1,",
        "  child2: Child2,",
        "  child3: Child3,",
        "  child4: Child4,",
        "};",
        "",
        "export type Child1 = {};",
        "",
        "export type Child2 = {",
        "  field1: number,",
        "};",
        "",
        "export type Child3 = {",
        "  field1: string,",
        "};",
        "",
        "export type Child4 = {",
        "  field1: string,",
        "};"
    );
  }

  @Test
  public void shouldParseModelWithPrimitive() {
    String flowCode = execution.run(BASE_PATH + "ModelWithPrimitive.java");

    assertStringEqual(
        flowCode,
        "export type ModelWithPrimitive = {",
        "  byteField: number,",
        "  shortField: number,",
        "  intField: number,",
        "  longField: number,",
        "  floatField: number,",
        "  doubleField: number,",
        "  booleanField: boolean,",
        "  booleanField2: boolean,",
        "  charField: string,",
        "};"
    );
  }

  @Test
  public void shouldParseModelWithJavaLangObjects() {
    String flowCode = execution.run(BASE_PATH + "ModelWithJavaLangObjects.java");

    assertStringEqual(
        flowCode,
        "export type ModelWithJavaLangObjects = {",
        "  booleanField: boolean,",
        "  booleanField2: boolean,",
        "  byteField: number,",
        "  characterField: string,",
        "  doubleField: number,",
        "  floatField: number,",
        "  integerField: number,",
        "  longField: number,",
        "  shortField: number,",
        "  stringField: string,",
        "};"
    );
  }

  @Test
  public void shouldParseModelWithStaticFields() {
    String flowCode = execution.run(BASE_PATH + "ModelWithStaticFields.java");

    assertStringEqual(
        flowCode,
        "export type ModelWithStaticFields = {};"
    );
  }

  @Test
  public void shouldThrowExceptionWhenFieldIsNotFound() {
    try {
      execution.run(BASE_PATH + "Wrapper.java");
      fail("Should fail when member types are not found");
    } catch (AggregatedException e) {
      MissingTypeException missingTypeException = (MissingTypeException) e.getExceptions().get(0);
      assertThat(missingTypeException.getTypes().entrySet(), hasSize(1));
    }
  }

  @Test
  public void shouldThrowExceptionWhenGetterDoesNotHaveMatchingField() {
    try {
      execution.run(BASE_PATH + "ModelWithNotMatchingGetter.java");
      fail("Should fail when getter does not have matching field");
    } catch (AggregatedException e) {
      assertThat(e.getExceptions(), hasSize(1));
      assertStringEqual(e.getMessage(), "Verification failed:",
          "",
          "Class getter naming validation failed with following errors:",
          "",
          "1) Model {com.github.havardh.javaflow.model.ModelWithNotMatchingGetter} is not a pure DTO.",
          "Name of getter getStringFields does not correspond to any field name."
      );
    }
  }

  @Test
  public void shouldThrowExceptionWhenBooleanGetterDoesNotHaveMatchingField() {
    try {
      execution.run(BASE_PATH + "ModelWithNotMatchingBooleanGetter.java");
      fail("Should fail when boolean getter does not have matching field");
    } catch (AggregatedException e) {
      assertThat(e.getExceptions(), hasSize(1));
      assertStringEqual(e.getMessage(), "Verification failed:",
          "",
          "Class getter naming validation failed with following errors:",
          "",
          "1) Model {com.github.havardh.javaflow.model.ModelWithNotMatchingBooleanGetter} is not a pure DTO.",
          "Name of getter isBooleanFields does not correspond to any field name."
      );
    }
  }

  @Test
  public void shouldThrowExceptionWhenDifferentNumberOfGettersAndFields() {
    try {
      execution.run(BASE_PATH + "ModelWithoutGetters.java");
      fail("Should fail when model has no getters");
    } catch (AggregatedException e) {
      assertThat(e.getExceptions(), hasSize(1));
      assertStringEqual(e.getMessage(), "Verification failed:",
          "",
          "Class getter naming validation failed with following errors:",
          "",
          "1) Model {com.github.havardh.javaflow.model.ModelWithoutGetters} is not a pure DTO.",
          "Number of getters and fields is not the same.",
          "Fields in model: [stringField: java.lang.String]",
          "Getters in model: []"
      );
    }
  }

  @Test
  public void shouldThrowExceptionWhenGetterMatchingToFieldHasDifferentType() {
    try {
      execution.run(BASE_PATH + "ModelWithNotMatchingGetterType.java");
      fail("Should fail when getter type is different than type of field with matching name");
    } catch (AggregatedException e) {
      assertThat(e.getExceptions(), hasSize(1));
      assertStringEqual(e.getMessage(), "Verification failed:",
          "",
          "Class getter naming validation failed with following errors:",
          "",
          "1) Model {com.github.havardh.javaflow.model.ModelWithNotMatchingGetterType} is not a pure DTO.",
          "Type of getter getIntegerField (java.lang.String) does not correspond to field integerField (int)"
      );
    }
  }
}

