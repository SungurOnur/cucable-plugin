# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

Back to [Readme](README.md).

## [Unreleased]
- Ability to generate features and runners in Maven's target folder
- Ability to run multiple features per generated runner

## [0.0.8] - 2017-09-24

### Changed
- Fixed parse error on empty cells in data tables
- Fixed file name generation bug with special chars in source feature file names

### Added
- Unit tests for data table handling

## [0.0.7] - 2017-09-20

### Changed
- Complete rewrite of the internal logic of the plugin to support all edge cases like
  - empty scenarios
  - background steps (also with data tables)
  - scenario steps with data tables
  - complex scenario outlines

### Added
- Better logging (including plugin version)
- Clearer error messages

## [0.0.6] - 2017-08-31

### Added
- Ability to run single features multiple times in parallel
- Properties are now logged on plugin start
- More unit tests

### Changed
- Complete project now uses dependency injection via Google [Guice](https://github.com/google/guice)
- POM parameter _featureFileDirectory_ was renamed to _sourceFeatures_ since it now supports specifying either a directory or a single feature file

## [0.0.5] - 2017-08-18

### Added
- Cucumber 'Background' is now supported in feature files

### Changed
- More logging added.

## [0.0.4] - 2017-06-01

Initial project version on GitHub and Maven Central.

[Unreleased]: https://github.com/trivago/cucable-plugin/compare/0.0.8...HEAD
[0.0.8]: https://github.com/trivago/cucable-plugin/compare/0.0.7...0.0.8
[0.0.7]: https://github.com/trivago/cucable-plugin/compare/0.0.6...0.0.7
[0.0.6]: https://github.com/trivago/cucable-plugin/compare/0.0.5...0.0.6
[0.0.5]: https://github.com/trivago/cucable-plugin/compare/v0.0.4...0.0.5
[0.0.4]: https://github.com/trivago/cucable-plugin/tree/v0.0.4