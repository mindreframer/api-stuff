---
layout: default
title: Barrister RPC - JSON-RPC plus IDL
---

# JSON-RPC + IDL = Barrister RPC

* Define your interface in a human readable IDL
* Run `barrister` to convert IDL to JSON and produce [docco style HTML docs](http://jashkenas.github.com/docco/) for your interface
* Write your server implementation
* Consume it
  
### Overview

Barrister is a RPC system that uses an external interface definition (IDL) file to describe the
interfaces and data structures that a component implements.  It is similar to
tools like Protocol Buffers, Thrift, Avro, and SOAP.

Barrister emphasizes:

* **Ease of use.**  You should be able to write a client and server in minutes.
* **Interface as documentation.**  The interface definition should be human readable. The collection
  of interfaces of a system's components can be an excellent way to understand the system.
* **Being idiomatic.** Provide code generation for static languages which can enforce type safety 
  at compile time.  Don't use code generation for dynamic languages.  Language bindings should
  feel natural for developers of each language.
* **Interoperability.**  You shouldn't have to worry about whether your Python client will work with
  a Node.js server.  Barrister has a conformance suite that validates all supported language 
  bindings enforce the IDL rules uniformly.

RPC calls are encoded as [JSON-RPC 2.0](http://jsonrpc.org/specification) requests/responses.
Consequently, any JSON-RPC 2.0 client should be able to consume a Barrister RPC service, but Barrister
clients are preferred as they provide client side type validation and IDL discovery.

### Example


<div class="tabbable">
  <ul class="nav nav-tabs">
    <li class="active"><a href="#calc-idl" data-toggle="tab">IDL</a></li>
    <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Servers<b class="caret"></b></a>
        <ul class="dropdown-menu">
          <li><a href="#calc-java-server" data-toggle="tab">java</a></li>
          <li><a href="#calc-node-server" data-toggle="tab">node</a></li>
          <li><a href="#calc-php-server" data-toggle="tab">php</a></li>
          <li><a href="#calc-python-server" data-toggle="tab">python</a></li>
          <li><a href="#calc-ruby-server" data-toggle="tab">ruby</a></li>
          
        </ul>
    </li>
    <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Clients<b class="caret"></b></a>
        <ul class="dropdown-menu">
          <li><a href="#calc-java-client" data-toggle="tab">java</a></li>
          <li><a href="#calc-node-client" data-toggle="tab">node</a></li>
          <li><a href="#calc-php-client" data-toggle="tab">php</a></li>
          <li><a href="#calc-python-client" data-toggle="tab">python</a></li>
          <li><a href="#calc-ruby-client" data-toggle="tab">ruby</a></li>
          
        </ul>
    </li>
    <li><a href="#calc-output" data-toggle="tab">Output</a></li>

  </ul>
  <div class="tab-content">
    <div class="tab-pane active" id="calc-idl">
{% highlight go %}
//
// The Calculator service is easy to use.
//
// Examples
// --------
//
//     x = calc.add(10, 30)
//     # x == 40
//
//     y = calc.subtract(44, 10)
//     # y == 34

interface Calculator {
    // Adds two numbers together and returns the result   
    add(a float, b float) float
    
    // Subtracts b from a and returns the result
    subtract(a float, b float) float
}

{% endhighlight %}
    </div>
  <div class="tab-pane" id="calc-java-server">
{% highlight java %}
package example;

public class Server implements Calculator {

    public Double add(Double a, Double b) {
        return a+b;
    }

    public Double subtract(Double a, Double b) {
        return a-b;
    }

}
{% endhighlight %}
    </div>
    <div class="tab-pane" id="calc-java-client">
{% highlight java %}
package example;

import com.bitmechanic.barrister.HttpTransport;

public class Client {

    public static void main(String argv[]) throws Exception {
        HttpTransport trans = new HttpTransport("http://127.0.0.1:8080/example/");
        CalculatorClient calc = new CalculatorClient(trans);

        System.out.println(String.format("1+5.1=%.1f", calc.add(1.0, 5.1)));
        System.out.println(String.format("8-1.1=%.1f", calc.subtract(8.0, 1.1)));

        System.out.println("\nIDL metadata:");

        // BarristerMeta is a Idl2Java generated class in the same package
        // as the other generated files for this IDL
        System.out.println("barrister_version=" + BarristerMeta.BARRISTER_VERSION);
        System.out.println("checksum=" + BarristerMeta.CHECKSUM);
    }

}
{% endhighlight %}
    </div>
  <div class="tab-pane" id="calc-node-server">
{% highlight javascript %}
var barrister = require('barrister');
var express   = require('express');
var fs        = require('fs');

function Calculator() { }
Calculator.prototype.add = function(a, b, callback) {
    // first param is for errors
    callback(null, a+b);
};
Calculator.prototype.subtract = function(a, b, callback) {
    callback(null, a-b);
};

var idl    = JSON.parse(fs.readFileSync("../calc.json").toString());
var server = new barrister.Server(idl);
server.addHandler("Calculator", new Calculator());

var app = express.createServer();
app.use(express.bodyParser());
app.post('/calc', function(req, res) {
    server.handle({}, req.body, function(respJson) {
        res.contentType('application/json');
        res.send(respJson);
    });
});
app.listen(7667);
{% endhighlight %}
    </div>
    <div class="tab-pane" id="calc-node-client">
{% highlight javascript %}
var barrister = require('barrister');

function checkErr(err) {
    if (err) {
        console.log("ERR: " + JSON.stringify(err));
        process.exit(1);
    }
}

var client = barrister.httpClient("http://localhost:7667/calc");

client.loadContract(function(err) {
    checkErr(err);

    var calc = client.proxy("Calculator");

    calc.add(1, 5.1, function(err, result) {
        var i;
        checkErr(err);
        console.log("1+5.1=" + result);

        calc.subtract(8, 1.1, function(err, result) {
            checkErr(err);
            console.log("8-1.1=" + result);

            console.log("\nIDL metadata:");
            meta = client.getMeta();
            keys = [ "barrister_version", "checksum" ];
            for (i = 0; i < keys.length; i++) {
                console.log(keys[i] + "=" + meta[keys[i]]);
            }
        });
    });
});

{% endhighlight %}
    </div>
  <div class="tab-pane" id="calc-php-server">
{% highlight php %}
<?php

$path = $_ENV["BARRISTER_PHP"];
include_once("$path/barrister.php");

class Calculator {

  function add($a, $b) {
    return $a + $b;
  }

  function subtract($a, $b) {
    return $a - $b;
  }

}

$server = new BarristerServer("../calc.json");
$server->addHandler("Calculator", new Calculator());
$server->handleHTTP();
?>
{% endhighlight %}
    </div>
    <div class="tab-pane" id="calc-php-client">
{% highlight php %}
<?php

$path = $_ENV["BARRISTER_PHP"];
include_once("$path/barrister.php");

$barrister = new Barrister();
$client    = $barrister->httpClient("http://localhost:8080/cgi-bin/server.php");
$calc      = $client->proxy("Calculator");

echo sprintf("1+5.1=%.1f\n", $calc->add(1, 5.1));
echo sprintf("8-1.1=%.1f\n", $calc->subtract(8, 1.1));

echo "\nIDL metadata:\n";
$meta = $client->getMeta();
$keys = array("barrister_version", "checksum");
foreach ($keys as $i=>$key) {
  echo "$key=$meta[$key]\n";
}

?>
{% endhighlight %}
    </div>
  <div class="tab-pane" id="calc-python-server">
{% highlight python %}

from flask import Flask, request, make_response
import barrister

# Our implementation of the 'Calculator' interface in the IDL
class Calculator(object):

    # Parameters match the params in the functions in the IDL
    def add(self, a, b):
        return a+b

    def subtract(self, a, b):
        return a-b

contract = barrister.contract_from_file("../calc.json")
server   = barrister.Server(contract)
server.add_handler("Calculator", Calculator())

app = Flask(__name__)

@app.route("/calc", methods=["POST"])
def calc():
    resp_data = server.call_json(request.data)
    resp = make_response(resp_data)
    resp.headers['Content-Type'] = 'application/json'
    return resp

app.run(host="127.0.0.1", port=7667)

{% endhighlight %}
    </div>
    <div class="tab-pane" id="calc-python-client">
{% highlight python %}

import barrister

trans  = barrister.HttpTransport("http://localhost:7667/calc")

# automatically connects to endpoint and loads IDL JSON contract
client = barrister.Client(trans)

print "1+5.1=%.1f" % client.Calculator.add(1, 5.1)
print "8-1.1=%.1f" % client.Calculator.subtract(8, 1.1)

print
print "IDL metadata:"
meta = client.get_meta()
for key in [ "barrister_version", "checksum" ]:
    print "%s=%s" % (key, meta[key])

# not printing this one because it changes per run, which breaks our
# very literal 'examples' test harness, but let's verify it exists at least..
assert meta.has_key("date_generated")


{% endhighlight %}
    </div>
  <div class="tab-pane" id="calc-ruby-server">
{% highlight ruby %}

require 'sinatra'
require 'barrister'

class Calculator

  def add(a, b)
    return a+b
  end

  def subtract(a, b)
    return a-b
  end

end

contract = Barrister::contract_from_file("../calc.json")
server   = Barrister::Server.new(contract)
server.add_handler("Calculator", Calculator.new)

post '/calc' do
  request.body.rewind
  resp = server.handle_json(request.body.read)
  
  status 200
  headers "Content-Type" => "application/json"
  resp
end

{% endhighlight %}
    </div>
    <div class="tab-pane" id="calc-ruby-client">
{% highlight ruby %}

require 'barrister'

trans = Barrister::HttpTransport.new("http://localhost:7667/calc")

# automatically connects to endpoint and loads IDL JSON contract
client = Barrister::Client.new(trans)

puts "1+5.1=%.1f" % client.Calculator.add(1, 5.1)
puts "8-1.1=%.1f" % client.Calculator.subtract(8, 1.1)

puts
puts "IDL metadata:"
meta = client.get_meta
[ "barrister_version", "checksum" ].each do |key|
    puts "#{key}=#{meta[key]}"
end

{% endhighlight %}
    </div>
  
    <div class="tab-pane" id="calc-output">
<pre>1+5.1=6.1
8-1.1=6.9

IDL metadata:
barrister_version=0.1.3
checksum=51a911b5eb0b61fbb9300221d8c37134
</pre>
    </div>
  </div>
</div>

To convert `calc.idl` to JSON and HTML forms, run:

    barrister -t "Calculator Interface" -d calc.html -j calc.json calc.idl
    
Or use the hosted translator (this will only output the JSON file, not the docs):

    curl --data-urlencode idl@calc.idl http://barrister.bitmechanic.com/run > calc.json

Output files:

 * `calc.html` - [A human readable interface doc](calc.html)
 * `calc.json` - [A computer readable JSON file](calc.json)

----

### Nifty, now what?

 * [Download](download.html) Barrister and install it
 * Peruse [more examples](examples.html) of Barrister 
 * Try writing some IDL files to get comfortable with the syntax
 * Join the [mailing list](https://groups.google.com/forum/#!forum/barrister-rpc)
 * [Contribute](contribute.html) to the project by writing a [new language binding](binding.html), a blog article, or a demo app
