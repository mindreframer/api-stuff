### Experimental: Alternate transport / serializer

This example shows how you can build multi-tier topologies with Barrister.
In this fictional example requests originate from the client via HTTP as JSON messages.  

Imagine a backend implemented as a set of independent services that use Redis as a broker.
For performance, backend messages are encoded using [message pack](http://msgpack.org/).

We want to ensure that only authenticated HTTP clients can put messages on the Redis bus, 
and we want the backend services to be able to access the username of the user originating the
request.  To summarize the flow:

* Client makes Barrister requests using HTTP, secured with Basic Auth
  * In this example, the client is Python, but in the real world this might be a rich web app
    written in Backbone.js that makes service calls over HTTP
* Web Server accepts request and acts as an authenticating router:
  * Basic Auth header is decoded and verified (in memory in this example, but in the real world, perhaps
    against a database, or another downstream service)
  * Barrister request is deserialized as JSON, and re-encoded with message pack.  A name/value pair map
    of headers is included in the msg pack message.  The headers include a `reply_to` key used to route
    the reply from the backend server, and a `username` key that indicates the identity of the HTTP user.
  * Msg pack message is queued on a Redis list using `lpush`
  * We block for a reply on `reply_to` key in Redis using `brpop` with a 30 second timeout
* Backend worker process actually implements the IDL
  * Has a `while(true)` loop that poll Redis for requests using `brpop`
  * For each request:
    * Message is deserialized via message pack
    * Request body is processed using Barrister
    * Response is serialized via message pack
    * Response is sent to Redis via `lpush` using the `reply_to` header as the key
    
This architecture is interesting because it allows us to:

* Write a rich Javascript client that can directly consume backend services
* Write a mostly domain-neutral HTTP router that is responsible for basic security
* Write all our business logic on the backend as decoupled processes

The Redis transport could easily be swapped out for other transports such as ZeroMQ, AMQP,
etc.  The redis code is about 10 lines total in this example.
