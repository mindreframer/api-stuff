---
layout: default
title: Barrister RPC - Binding Dev Guide
---

## Binding Dev Guide

Thanks for your interest in writing a new language binding for Barrister.  This guide outlines
some things to keep in mind while writing your binding.

## Before we go further

Barrister is really a layer on [JSON-RPC 2.0](http://jsonrpc.org/specification).  If you are familiar with
a JSON-RPC library for your favorite language, you could try overlaying the Barrister IDL discovery and
type validation onto that library.  

That might save you some time.  Or it might not.

## Considerations

* Requests and responses are encoded using the [JSON-RPC 2.0](http://jsonrpc.org/specification) format.  Take a moment to familiarize yourself with that document.
* Write a README that explains:
  * How to install the binding -- ideally using the language package manager if one exists
  * How to write a client
  * How to write a server
* Should pass the conformance test suite (see below)

### Type Validation

* Client and server code should validate that request / response payloads match the type definitions specified in the IDL
* JSON-RPC error codes should be used to express errors. For example, invalid params passed to the server should result in an error with code=-32602
* Null value rules
  * Top level function parameters may **never** be null
  * Struct fields flagged as `optional=true` may be null
  * Return types flagged as `optional=true` may be null
  * Non-null values should be validated against the type specified in the IDL (recursively)

### Client

* Provide a HTTP transport that given an URL, loads the IDL contract for that endpoint
  * Send a request to the endpoint with the JSON-RPC method: `barrister-idl`
* Provide batch support so that clients can invoke multiple methods in a single RPC call
  * Clients are responsible for correlating the responses with the requests based on ID
  * Reponses should be ordered to match the order of the requests made against the batch
* Ideally provides an "in process" transport that skips the serialization code but still performs type validation.

### Server

* Provide a way to associate interface implementations with a given IDL
* Provide a transport independent dispatching mechanism that parses the JSON-RPC request and invokes the correct method on the registered interface implementation
* Implement the `barrister-idl` method, which returns the IDL JSON associated with the server instance
* Provide batch request support.  
  * Per the JSON-RPC spec, servers **may** parallelize execution of individual requests in the batch, but are not required to.

### Code Generation

* If the language is static, we encourage you to provide a command line code generator that takes a IDL JSON file as input and produces stubs for that interface
  * See the [barrister-java](https://github.com/coopernurse/barrister-java) implementation as an example
  * Interfaces, Structs, and Enums should all be supported
  * Consider writing the comments in the IDL as comments in the generated source code that match the convention of the host language (e.g. Javadoc style for Java)

## Jenkins Continuous Integration

I have a Jenkins server that runs the test suite for Barrister and the supported language bindings.
Once your binding is far enough along I'd like to add it to the Jenkins server and integrate it into the
conformance test suite.  Please ping me on the 
[mailing list](https://groups.google.com/forum/#!forum/barrister-rpc) when you're ready.

## Conformance Tests

In the [python tree](https://github.com/coopernurse/barrister/tree/master/conform) there is a 
conformance test that runs all the clients and servers against each other in a pair-wise fashion 
using a common text input file.  The output from each client is checked and any anomolies are
logged as failures.

Each language runtime needs to provide a client and server implementation that can be run by the
test suite.  Look at the python code as a reference:

* `client.py` - Conformance test client
  * Is passed two command line arguments:
    * Path to input file: `conform.in`
    * Path to output file to write to
  * Initializes a HTTP client against `localhost:9233`
  * Opens input file and for each line:
    * Skips blank lines or comment lines
    * Splits line on pipes. See conform.in for a description fo the columns in the file.
    * Executes the method against the server
    * If result is ok, logs the `result`
    * If result is rpcerr, logs the `error.code`
  * Closes output file and exits

* `flask_server.py` - Conformance test server.
  * Implements the `A` and `B` interfaces defined in conform.idl/conform.json.  
  * Binds HTTP server to `localhost:9233`
  * Binds Barrister server to `/` URL
  * Binds a separate handler to `/exit` URL which is triggered by test suite
    * Server should exit when this URL is hit
  * No other magic in the server impl.  Should simply execute the calls made against it.
    * Some requests are intentionally invalid, so server should reject them with the correct error codes.
    
To add your implementation to the suite:

* Open `conform_test.py`
* Add the client to the `clients` block which starts around line 39
  * Format:  `[ "name", [ "command", "line", "to", "start", "client" ] ]`
* Add a new test method to the `ConformTest` class
  * See `test_python_server` as an example
  * Should provide a `cmd` array that can start the server
  * Can optionally sleep for x seconds to give server time to start and bind to port
* To run: `python conform_test.py`
  * The conformance test will run each client against each server
  * Any output from the clients that deviates from the expectation in `conform.in` will trigger a failure
