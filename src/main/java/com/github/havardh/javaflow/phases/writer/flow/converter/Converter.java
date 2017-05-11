package com.github.havardh.javaflow.phases.writer.flow.converter;

import com.github.havardh.javaflow.model.CanonicalName;

/**
 * Interface for type converters. The converter
 * converts types from the source language to the
 * target language.
 */
public interface Converter {

  /**
   * Converts a {@code CanonicalName} in the source language
   * to a type in the target language.
   *
   * @param name a {@code CanonicalName} in the source language
   * @return the {@String} name in the target language
   */
  String convert(CanonicalName name);

}
