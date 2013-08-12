# Swagger Sample App

## Overview
This is a "server-less" example of running swagger.  There technically is a web server responding to the requests
from the swagger-ui, this is needed because loading the files requires AJAX calls to be made.  But the specs are
provided as flat json files, and calls to the API itself are sent off to the http://petstore.swagger.wordnik.com api.

This demonstrates how you can write swagger specs and take advantage of the UI without ANY server integration.  You
are burdened with the task of making sure the spec files match up with the api itself, and the filtering of api calls
in the UI is not possible.  All users will see the same APIs.

### To build from source
There's nothing to build--all files are here.

### To run with maven

```
mvn jetty:run
```

This will start jetty on port 8000, and open the swagger-ui to view the spec files.

### Testing the server
Once started, you can view all apis and even invoke them.  Opening the Chrome Developer Tools and selecting
the `Network` tab, you will see the API calls going to the http://petstore.swagger.wordnik.com API rather than
the local apache web server.

### Modifying the spec files
Now try editing the spec files in the web root--you can immediately reload the browser and see changes.
