# Contributing

Thanks for showing an interest in contributing to javaflow!

**Working on your first Pull Request?** You can learn how from this _free_
series [How to Contribute to an Open Source Project on GitHub][egghead] by
Kent C. Dodds.

By making a contribution to this project you agree to abide by the
[Code of Conduct][code-of-conduct].

## Project setup

This project uses a bundled `gradle`package manager.

1.  Fork and clone the repo
2.  `./gradlew build` - builds sources
3.  `git checkout -b <branch>` Create a branch for your PR

### Tests and static analysis

Tests are mainly added as integration tests in [`ExecutionIntegrationTest`]([integration-tests]).

- `./gradlew test` - Runs the all the tests

#### Continuous integration

Circle CI is used for continuous integration.

## Conventions

The [angular commit guide][angular-commit] line is used to govern commit messages.

## Help needed

Please checkout the [the open issues][issues]

Also, please watch the repo and respond to questions/bug reports/feature
requests! Thanks!

## Attribution

This guide is adapted from the [Downshift][downshift] contributors guide.

[egghead]: https://egghead.io/series/how-to-contribute-to-an-open-source-project-on-github
[issues]: https://github.com/havardh/javaflow/issues
[downshift]: https://github.com/paypal/downshift
[flowtype]: https://flow.org/
[angular-commit]: https://gist.github.com/stephenparish/9941e89d80e2bc58a153
[code-of-conduct]: CODE_OF_CONDUCT.md
[integration-tests]: test/java/com/github/havardh/javaflow/ExecutionIntegrationTest.java