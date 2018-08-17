# Javaflow #

`javaflow` converts a set of Java models to Flow types.
[![CircleCI](https://circleci.com/gh/havardh/javaflow.svg?style=svg)](https://circleci.com/gh/havardh/javaflow)

## Example ##

`javaflow` takes a list of Java class files, and writes the flow types
for the models to the standard output.

A simple example is the following conversion of `Post.java` to `post.js`.

```
public class Post {
  private String title;
  private String text;
  
  public String getTitle() { return this.title; }
  public String getText() { return this.text; }
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

## Installation ##

### Embedding in maven build

See the [readme](https://github.com/havardh/javaflow-maven-plugin/blob/master/Readme.md) of the javaflow-maven-plugin repository.

### Standalone cli tool

Download the most recent zip file under releases.
Unzip the downloaded file to the desired location like `~/apps/javaflow`.
Add `~/app/javaflow/bin` to your `PATH` variable.

## Advanced usage ##

### Custom type substitution ###

Internally `javaflow` contains a mapping from standard Java types like String, int and boolean
to corresponding flow types. This mapping can be overridden, or additional mappings can be provided.

An use case for providing custom type substitutions is when the Java model contains a type with
a custom serialized form.

Consider the model `Person` with the `Ssn` wrapper type below.

```
com.github.havardh.examples;

public class Ssn {
  private String ssn;
  
  public String getSsn() { return this.ssn; }
}
```

```
com.github.havardh.examples;

public class Person {
  private Ssn ssn;
  
  public Ssn getSsn() { return this.ssn; }
}
```

Lets say we the Java api layer contains a custom json serializer which removes the wrapper
type and outputs the ssn directly like so:

```
{"ssn": "12120032701"}
```

Without any further configuration, `javaflow Person.java Ssn.java` would convert the types as follows

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
This can be achieved by supplying a `types.yml`. The `types.yml` file is
read from the folder the `javaflow` command is executed from.

```
com.github.havardh.examples.Ssn: string
```

The format of the yaml file is `<canonical java type>: <flow type>`.

Now `javaflow Person.java` will output:

```
/* @flow */
export type Person = {
  ssn: string,
};
``` 

Note: executing the command `javaflow Person.java` without the `types.yml` file would lead 
to the program failing due to the `Ssn.java` file not being found. `javaflow` does not look
for additional files which where not supplied as input.

### Verifiers ###

In case you want to verify that you have specified mappings for all types,
that the DTOs have getters for all fields,
or that the type of the fields are same as the return types of their getters,
`javaflow` offers the possibility to add verifiers.

Available verifiers:
- `MemberFieldsPresentVerifier`, active by default, verifies that all types used in the types converted are either:
    - built-in java types,
    - overridden by custom type substitution, or
    - included as a type being converted. 
- `ClassGetterNamingVerifier`, can be activated by passing the `--verifyGetters` flag in the CLI, verifies that the given types all have:
    - the same number of fields and getters,
    - the same type in the field definitions as the return type in the corresponding getter and
    - their getters for the corresponding fields with the same name, prefixed with `get` or `is`.

## Development ##

### Package ###

Run the following command to package a zip file with all dependencies and the `javaflow` binary.

`gradle assemble`

This create a zip file in the target folder.
