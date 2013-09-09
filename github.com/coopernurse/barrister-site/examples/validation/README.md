### Type validation

This example demonstrates the automatic type validation that Barrister performs including:

* Simple type mismatches (e.g. pass an int when a string is expected)
* Ensuring that objects contain all non-optional struct fields (including fields on parent structs)
* Ensuring that objects do not contain properties missing from a struct
* Ensuring enum values are valid
