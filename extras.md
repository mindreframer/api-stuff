---
layout: default
title: Barrister RPC - Extras
---

## Extras

This page contains links to demos, articles, and other information related to the project.  If you have 
something to add, please [fork this project](https://github.com/coopernurse/barrister-site) and send a 
pull request with your changes.

### Articles

* [Using Backbone.js with Barrister RPC](http://bitmechanic.com/2012/05/02/backbone-with-barrister-rpc.html)

### Demos

#### [Generic Client in Javascript](bclient.html)

Source code: 

* Client: [bclient.js](https://github.com/coopernurse/barrister-site/blob/master/js/bclient.js) and 
[bclient.html](https://github.com/coopernurse/barrister-site/blob/master/bclient.html)
* [Server - Python](https://github.com/coopernurse/barrister-demo-contact/blob/master/python/server.py)

> This demo shows how the Barrister JSON IDL can be used at runtime to create forms for any 
> Barrister service.  The client code in this demo knows nothing about any particular service.
> Instead, given any valid endpoint URL, it loads the IDL, then lists the interfaces and functions
> that are available.  When a function is clicked, the input parameters for that function are displayed
> in a HTML form.
>
> This demonstrates that Barrister can provide auto-discovery features at runtime that can be useful for 
> writing generic clients and diagnostic tools.