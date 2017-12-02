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

Download the most recent zip file under releases.
Unzip the downloaded file to the desired location like `~/apps/javaflow`.
Add `~/app/javaflow/bin` to your `PATH` variable.

## Advanced usage - Custom type substitution ##

Internally `javaflow` contains a mapping from standard Java types like String, int and boolean
to corresponding flow types. This mapping can be overridden, or additional mappings can be provided.

An use case for providing custom type substitutions is when the Java model contains a type with
a custom serialized form.

Consider the model `Person` with the `Ssn` wrapper type below.

```
com.github.havardh.examples;

public class Ssn {
  private String ssn;
}
```

```
com.github.havardh.examples;

public class Person {
  private Ssn ssn;
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

## Development ##

### Package ###

Run the following command to package a zip file with all dependencies and the `javaflow` binary.

`gradle assemble`

This create a zip file in the target folder.
