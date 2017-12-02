package com.github.havardh.javaflow.phases.filetransform;

/**
 * An interface for file transformers.
 *
 * {@code FileTransformer}s will look at a complete output
 * file and transform it to a new file
 */
public interface FileTransformer {

  /**
   * Transform the file contents to a new file.
   *
   * @param file the file contents to transform
   * @return the transformed file contents.
   */
  String transform(String file);

}
