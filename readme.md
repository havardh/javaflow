# Javaflow #

A Java model to flowtype converter.
[![Build Status](https://travis-ci.org/havardh/javaflow.svg?branch=master)](https://travis-ci.org/havardh/javaflow)

## Package ##

Packaging a zip file with all dependencies and the binary for running:

`mvn clean package appassembler:assemble`

This create a zip file in the target folder.

## Usage ##

```
./javaflow <input-file:.java> > output.js
```

## Custom types ##

The converter reads a custom `types.yml` with a simple key-value substitution map. 
The keys are java types and the values are the flowtypes. 
This feature can be used to override the default substitutions. 
A use case if when a java type is serialized as a simpler type and you want the flowtype to be the simpler type.