package no.havard.javaflow.phases.writer.flow.converter;

import no.havard.javaflow.model.CanonicalName;

public interface Converter {

  String convert(CanonicalName name);

}
