package no.havard.javaflow.phases.writer.flow;

import no.havard.javaflow.model.CanonicalName;

public interface Converter {

  String convert(CanonicalName name);

}
