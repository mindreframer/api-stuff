# Swagger Sample App

## Overview
This is a java project to build a stand-alone server which implements the Swagger spec.  It demonstrates both
removal of the .{format} in the path as well as placement of specific resource listings under an alternate path.

DANGER!  Most clients lie about what format of data they want.  By requiring .{format} in the root api you
are doing your clients a huge favor.  Otherwise they need to correctly pass Accept headers to the API.  That's 
all good and fine but most client's don't do that.  And since you probably can't just support JSON, somebody will
get a tech support call.  You've been warned.

The default ApiListingResource lives in swagger-jaxrs--it is included by adding the following to the web.xml:

```xml
<init-param>
  <param-name>com.sun.jersey.config.property.packages</param-name>
  <param-value>com.wordnik.swagger.sample.resource;com.wordnik.swagger.jaxrs.listing;</param-value>
</init-param>
```

Note the com.wordnik.swagger.jaxrs.listing contains the default [resource listing](https://github.com/wordnik/swagger-core/blob/master/modules/swagger-jaxrs/src/main/scala/com/wordnik/swagger/jaxrs/listing/ApiListingResource.scala).
When overriding the default resource listing, simply provide that functionality in a new resource and add it to
the packages.  For this sample, the new resource listing class is [here](https://github.com/wordnik/swagger-core/blob/master/samples/java-alt-resource-listing/src/main/java/com/wordnik/swagger/sample/resource/ApiListingResource.java).
One other requirement is setting the JaxrsApiReader format string to an empty string in a [bootstrap servlet](https://github.com/wordnik/swagger-core/blob/master/samples/java-alt-resource-listing/src/main/java/com/wordnik/swagger/sample/Bootstrap.java)

```java
public class Bootstrap extends HttpServlet {
  static {
    JaxrsApiReader.setFormatString("");
  }
}
```

The api resource paths are now detected by the framework and live under the `/resources` path (i.e. `/resources/pet`, etc.):

### To build from source
Please follow instructions to build the top-level [swagger-core project](https://github.com/wordnik/swagger-core)

### To run (with Maven)
To run the server, run this task:

```
mvn package -Dlog4j.configuration=file:./conf/log4j.properties jetty:run
```

This will start Jetty embedded on port 8002 and apply the logging configuration from conf/log4j.properties

### Testing the server
Once started, you can navigate to http://localhost:8002/api/resources to view the Swagger Resource Listing.
This tells you that the server is up and ready to demonstrate Swagger.

### Using the UI
There is an HTML5-based API tool available in a separate project.  This lets you inspect the API using an 
intuitive UI.  You can pull this code from here:  https://github.com/wordnik/swagger-ui

You can then open the dist/index.html file in any HTML5-enabled browser.  Open opening, enter the
URL of your server in the top-centered input box (default is http://localhost:8002/api).  Click the "Explore" 
button and you should see the resources available on the server.

### Applying an API key
The sample app has an implementation of the Swagger ApiAuthorizationFilter.  This restricts access to resources
based on api-key.  There are two keys defined in the sample app:

<li>- default-key</li>

<li>- special-key</li>

When no key is applied, the "default-key" is applied to all operations.  If the "special-key" is entered, a
number of other resources are shown in the UI, including sample CRUD operations.  Note this behavior is similar
to that on http://developer.wordnik.com/docs but the behavior is entirely up to the implementor.