package no.havard.javaflow;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

import no.havard.javaflow.phases.filetransform.CommentAppendTransformer;
import no.havard.javaflow.phases.parser.java.JavaParser;
import no.havard.javaflow.phases.reader.FileReader;
import no.havard.javaflow.phases.transform.InheritanceTransformer;
import no.havard.javaflow.phases.transform.SortedTypeTransformer;
import no.havard.javaflow.phases.verifier.MemberFieldsPresentVerifier;
import no.havard.javaflow.phases.writer.flow.FlowWriter;
import no.havard.javaflow.phases.writer.flow.converter.Converter;
import no.havard.javaflow.phases.writer.flow.converter.JavaFlowConverter;
import no.havard.javaflow.util.TypeMap;

public class JavaFlow {

  public static void main(String args[]) {
    TypeMap typeMap = new TypeMap("types.yml");
    Converter converter = new JavaFlowConverter(typeMap);

    Execution execution = new Execution(
        new FileReader(),
        new JavaParser(),
        asList(
            new InheritanceTransformer(),
            new SortedTypeTransformer()
        ),
        singletonList(new MemberFieldsPresentVerifier(typeMap)),
        new FlowWriter(converter),
        asList(
            new CommentAppendTransformer("@flow")
        )
    );

    System.out.println(execution.run(args));
  }

}
