
### Under the hood

When this code executes: `client = barrister.Client(trans)`

A JSON-RPC message is POSTed to `http://localhost:8080/calc` that looks like:

    { "jsonrpc": "2.0", "id": "1", "method": "barrister-idl" }
    
The server sends back the IDL JSON registered with the `server = barrister.Server(contract)` call.
The response looks something like:

    { "jsonrpc": "2.0", "id": "1", "result": { ... idl json here ... } }
    
The client parses the IDL JSON and uses it to validate outgoing requests against that endpoint.

When this code executes: `client.Calculator.add(1, 5.1)`

A JSON-RPC message is POSTed to `http://localhost:8080/calc` that looks like:

    { "jsonrpc": "2.0", "id": "uuid-4-here", "method": "Calculator.add", "params": [1, 5.1] }
    
The server unpacks the JSON, verifies that the method exists, and validates that the parameter types
agree with the types defined for the `add()` function on the `Calculator` interface in the IDL.  If 
validation passes, the method is invoked, and the result is returned as a JSON response that looks like:

    { "jsonrpc": "2.0", "id": "id-from-request", "result": 6.1 }

If an error occurs, an `error` property replaces the `result`:

    { "jsonrpc": "2.0", "id": "id-from-request", 
      "error" : { "code": -32601, "message": "Unknown function: Calculator.multiply" } }
      
Theoretically you could use any JSON-RPC 2.0 client to consume a Barrister service, but you'd lose
the request/response validation.
