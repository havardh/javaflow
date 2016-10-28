# Javaflow #

`javaflow` converts a set of Java models to Flow types.
[![Build Status](https://travis-ci.org/havardh/javaflow.svg?branch=master)](https://travis-ci.org/havardh/javaflow)

## Example ##

The `javaflow` utility takes a list of Java class files writes the flow types
for the models to the standard output.

```
public class Post {
  private String title;
  private String text;
}
```

```
javaflow Post.java >post.js
```

```
/* @flow */
export type Post {
  title: string,
  text: string,
};
```

## Multiple files ##

To support inheritance `javaflow` accepts a list of files. All non-standard

## Advanced usage - Custom type substitution ##

Internally `javaflow` contains a mapping from standard Java types like String, int and boolean
to corresponding flow types. This mapping can be overrided, or additional mappings can be provided.

An use case for providing custom type substitutions is when the Java model contains a type with
a custom serialized form.

Consider the model Person with the Ssn wrapper type below.

```
no.havard.examples;

public class Ssn {
  private String ssn;
}
```

```
no.havard.examples;

public class Person {
  private Ssn ssn;
}
```

Lets say we the Java api layer contains a custom json serializer which removes the wrapper
type and outputs the ssn directly like so:

```
{"ssn": "12120032701"}
```

Without any further configuration, `javaflow` would convert the types as follows:

```
/* @flow */
export type Ssn = {
  ssn: string,
};

export type Person = {
  ssn: Ssn,
};
```

Here we want the string to be included in the Person type directly.
This can be achived by supplying a `types.yml`

```
no.havard.examples.Ssn: string
```

The format of the yaml file is `<canonical java type>: <flow type>`.

Now `javaflow Person.java` will output:

```
/* @flow */
export type Person = {
  ssn: string,
};
```

## Development ##

### Package ###

Run the following command to package a zip file with all dependencies and the `javaflow` binary.

`mvn clean package appassembler:assemble`

This create a zip file in the target folder.
