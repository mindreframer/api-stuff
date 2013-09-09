---
layout: default
title: Barrister RPC - About
---

## Why

I have a crush on interfaces. Thinking about a system in terms of the interfaces it implements is
the single best way I've found to focus on the 
[essential complexity](http://en.wikipedia.org/wiki/No_Silver_Bullet) of the system.  Functions
and data.  No implementation details.

My goals for this project are:

* Provide an easy way to document interfaces that both **humans** and **computers** can read
* Make it easy to get started, but provide enough power to model real systems
* Be fun and idiomatic to use from dynamic and static languages
  * No code generator required for dynamic languages
  * Runtime service discovery can lead to self-configuring clients powered by interface metadata

## Roadmap

Barrister currently provides a way to express a type system for an interface and validate that
requests and responses comply with that type system.  Future possibilities for development include:

* Validation
  * Enhance the IDL to allow expression of required fields, min/max values, regexp, etc
  * JSON Schema is one possibility, although it's a huge spec
* More transports and serialization formats
  * Main motivation would be performance.  HTTP/JSON works well for public APIs, but if you want to route requests internally behind a firewall, you might want something faster.  ZeroMQ+MessagePack is one clear option.

## Who are You?

I'm James Cooper of Seattle, WA.  I do freelance hacking as [bitmechanic](http://bitmechanic.com/).
Nice to meet you.
