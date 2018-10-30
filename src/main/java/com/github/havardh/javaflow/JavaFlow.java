package com.github.havardh.javaflow;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.Collections.singletonList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.github.havardh.javaflow.exceptions.ExitException;
import com.github.havardh.javaflow.model.TypeMap;
import com.github.havardh.javaflow.phases.filetransform.CommentPrependTransformer;
import com.github.havardh.javaflow.phases.filetransform.EslintDisableTransformer;
import com.github.havardh.javaflow.phases.parser.java.JavaParser;
import com.github.havardh.javaflow.phases.reader.FileReader;
import com.github.havardh.javaflow.phases.resolver.FileResolver;
import com.github.havardh.javaflow.phases.transform.InheritanceTransformer;
import com.github.havardh.javaflow.phases.transform.SortedTypeTransformer;
import com.github.havardh.javaflow.phases.verifier.ClassGetterNamingVerifier;
import com.github.havardh.javaflow.phases.verifier.MemberFieldsPresentVerifier;
import com.github.havardh.javaflow.phases.verifier.Verifier;
import com.github.havardh.javaflow.phases.writer.flow.FlowWriter;
import com.github.havardh.javaflow.phases.writer.flow.converter.Converter;
import com.github.havardh.javaflow.phases.writer.flow.converter.JavaFlowConverter;
import com.github.havardh.javaflow.util.App;

/**
 * Commmand line runner for JavaFlow
 */
public class JavaFlow {

  private static String VERIFY_GETTERS_ARG = "--verifyGetters";
  private static List<String> ARGS = singletonList(VERIFY_GETTERS_ARG);

  /**
   * Main routine for JavaFlow command line runner
   *
   * @param args command line arguments
   */
  public static void main(String args[]) {
    TypeMap typeMap = new TypeMap("types.yml");
    Converter converter = new JavaFlowConverter(typeMap);

    List<Verifier> verifierList = new ArrayList<>();
    verifierList.add(new MemberFieldsPresentVerifier(typeMap));
    if (stream(args).anyMatch(arg -> arg.equals(VERIFY_GETTERS_ARG))) {
      verifierList.add(new ClassGetterNamingVerifier());
    }

    Execution execution = new Execution(
        new FileResolver(),
        new FileReader(),
        new JavaParser(),
        asList(
            new InheritanceTransformer(),
            new SortedTypeTransformer()
        ),
        verifierList,
        new FlowWriter(converter),
        asList(
            new CommentPrependTransformer(format("Generated by javaflow %s", App.version())),
            new EslintDisableTransformer(singletonList("no-use-before-define")),
            new CommentPrependTransformer("@flow")
        )
    );

    try {
      System.out.println(execution.run(filterOutArgs(args)));
    } catch (ExitException e) {
      e.printStackTrace();
      System.exit(e.getErrorCode().getCode());
    }
  }

  private static String[] filterOutArgs(String[] args) {
    return stream(args).filter(arg -> !ARGS.contains(arg)).toArray(String[]::new);
  }

}
