package com.github.havardh.javaflow.phases.writer.flow.converter;

import com.github.havardh.javaflow.model.CanonicalName;

public interface Converter {

  String convert(CanonicalName name);

}
