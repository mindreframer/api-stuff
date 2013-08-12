# Swagger Core library

[![Build Status](https://travis-ci.org/wordnik/swagger-core.png)](https://travis-ci.org/wordnik/swagger-core)

## [See the Wiki!](https://github.com/wordnik/swagger-core/wiki)
The [github wiki](https://github.com/wordnik/swagger-core/wiki) contains documentation, samples, etc.  Start there

## Where to get help!
Search the [swagger google groups](https://groups.google.com/forum/#!forum/swagger-swaggersocket) for previously 
asked questions.  Join #swagger on irc.freenode.net to talk to interesting human beings.  And if you find a bug,
file it in github:

* [Swagger core + server itegrations](https://github.com/wordnik/swagger-core/issues) issues
* [Swagger UI](https://github.com/wordnik/swagger-ui/issues)
* [Swagger codegen](https://github.com/wordnik/swagger-codegen/issues)

## Overview
This is a project to build the swagger-core library, which is required for the Wordnik 
implementation of the Swagger spec.  You can find out more about both the spec and the
framework at http://swagger.wordnik.com.  For more information about Wordnik's APIs, please
visit http://developer.wordnik.com.

## Version history

Swagger-core v1.3.0 is in Release candidate status!  If you can't wait for the release and want to get
going on the latest, you can read about the [spec transition](https://github.com/wordnik/swagger-core/wiki/1.2-transition).  Migration
guides for the supported server integrations are being worked on now.

### v1.3.0-RC2 Jul-11 2013

Source lives in the [1.3-RC2 branch!](https://github.com/wordnik/swagger-core/tree/1.3-RC2)

<li>- Added apiInfo so top-level API information can be annotated in both swagger-ui and elsewhere


### v1.3.0-RC1 Jul-2 2013

<li>- Release candidate for swagger-spec 1.2

<li>- Pluggable readers for scanning classes, model introspection, config reading

<li>- Simplified overriding of model introspection

<li>- Support for consumes, produces, protocols, authentications

<li>- oAuth support in spec

<li>- Support for raw servlet integration

<li>- Resource, operation, model property ordering

<li>- Polymorphic model support

### v1.2.5 Jun-19 2013
<li>- Fixes for generic objects

### v1.2.4 Jun-5 2013
<li>- Fixed `@Api` paths with slashes

<li>- Added support for model detection with deep recursion (#176)

<li>- i18n support (#190)

<li>- Master is now `stable`

### v1.2.3 Apr-24 2013

<li>- Updated to Jackson 2.1.4

<li>- Fix for generics as input/output values

<li>- Add manual model definitions

### v1.2.2 Apr-8 2013

### v1.2.0 Nov-27 2012

<li>- Updated listing path to /api-docs.json

<li>- Moved swagger-play2 and swagger-play2-utils to maven central

### v1.1.0 Aug-22 2012

<li>- Spec [clarifications](https://github.com/wordnik/swagger-core/wiki/Changelog)

<li>- Updated to Jackson 2.0.x, added sample with [scala case classes](https://github.com/wordnik/swagger-core/tree/master/samples/scala-jaxrs-jackson2)

<li>- Play 2.0.2 supported

<li>- Pluggable model processor, added configurable package introspection

<li>- Feature parity between JAX-RS & Play 2, java, scala

<li>- Made .{format} [configurable](https://github.com/wordnik/swagger-core/tree/master/samples/scala-jaxrs-no-format), see [here](https://github.com/wordnik/swagger-core/blob/master/samples/scala-jaxrs-no-format/src/main/scala/com/wordnik/swagger/sample/Bootstrap.scala#L24)

<li>- Added support for alternate resource listing, examples in [scala](https://github.com/wordnik/swagger-core/tree/master/samples/scala-alt-resource-listing) and [java](https://github.com/wordnik/swagger-core/tree/master/samples/java-alt-resource-listing)

<li>- Moved swagger spec to github [wiki](https://github.com/wordnik/swagger-core/wiki)


### v1.01 Jan-31 2012

<li>- Transitioned build to Maven</li>

<li>- Removed jaxrs dependencies from swagger-core</li>

<li>- Moved sample server implementations into /samples directory</li>

<li>- Added support for alternate API listing paths</li>

<li>- Made .{format} optional</li>

<li>- Added crazy scala versioning into artifact names (Scala 2.8.1 => ${artifact}_2.8.1)

<li>- Added test and integration test through default maven integration-test lifecycle</li>

### v1.0 Aug-10 2011

<li>- Initial release of swagger</li>

Pre-release versions will be suffixed with SNAPSHOT and RC appropriately.  If you want the
release version, please grab it by tag (i.e. v1.0 for the release)

### Prerequisites
You need the following installed and available in your $PATH:

<li>- Java 1.6 or greater (http://java.oracle.com)

<li>- Apache maven 3.0.3 or greater (http://maven.apache.org/)

<li>- Scala 2.9.1-1 [available here](http://www.scala-lang.org).  Note that 2.9.1 has a defect which causes problems with Jax-RS

### To build from source (currently 1.2.4)
```
# first time building locally
mvn -N
```

Subsequent builds:
```
mvn install
```

This will build the modules and sample apps.

Of course if you don't want to build locally you can grab artifacts from maven central:

`http://repo1.maven.org/maven2/com/wordnik/`

## Sample Apps
There are a number of sample apps in the `samples` folder:

[java-jaxrs](https://github.com/wordnik/swagger-core/tree/master/samples/java-jaxrs/README.md) Java-based swagger server with JAX-RS

[scala-jaxrs](https://github.com/wordnik/swagger-core/tree/master/samples/scala-jaxrs/README.md) Scala-based swagger server with JAX-RS

[java-alt-resource-listing](https://github.com/wordnik/swagger-core/tree/master/samples/java-alt-resource-listing/README.md) 
Scala-based swagger server with JAX-RS with an alternate resource listing scheme

[scala-alt-resource-listing](https://github.com/wordnik/swagger-core/tree/master/samples/scala-alt-resource-listing/README.md) 
Scala-based swagger server with JAX-RS with an alternate resource listing scheme

[scala-jaxrs-apm](https://github.com/wordnik/swagger-core/tree/master/samples/scala-jaxrs-apm/README.md) 
Scala-based swagger server using wordnik-oss utils for Application Performance Monitoring (APM)

To run a sample app after initial compile:

```
# run scala-jaxrs sample app
cd samples/scala-jaxrs

mvn jetty:run
```

And the [Play2](http://playframework.org) samples:

[java-play2](https://github.com/wordnik/swagger-core/tree/master/samples/java-play2) Java-based Play2 sample app

[scala-play2](https://github.com/wordnik/swagger-core/tree/master/samples/scala-play2) Scala-based Play2 sample app

[scala-play2-no-format](https://github.com/wordnik/swagger-core/tree/master/samples/scala-play2-no-format) Scala-based Play2 sample app without the .{format} in resource listing

To run the Play2 sample apps:

```
cd samples/java-play2

play run
```

License
-------

Copyright 2013 Reverb Technologies, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at [apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
