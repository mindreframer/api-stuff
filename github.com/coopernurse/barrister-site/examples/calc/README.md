### Hello World

This example demonstrates a simple Calulator interface with two functions.

It also shows how to access basic metadata about the IDL contract such as:

* `barrister_version` - Version of barrister that translated the IDL to JSON
* `date_created` - Time JSON file was translated.  UTC milliseconds since epoch.
* `checksum` - A hash of the structure of the IDL that ignores comments, whitespace, 
and function parameter names.  The intent is that if the checksum 
changes, something semantically meaningful about the IDL has changed.
