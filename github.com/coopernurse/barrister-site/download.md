---
layout: default
title: Barrister RPC - Download
---

# Download Barrister

The Barrister parser, which converts the IDL file into JSON/HTML is written in Python. 
Regardless of what language you write your clients/servers in, you'll probably want to install
this package so you can translate your `.idl` files.  Once you have the `.json` file for your
IDL, you can copy that around into any server projects that implement it.  Your client and 
server programs only need the language binding for the language they're written in.

Python ships with an `easy_install` utility that knows about the main 
[Python Package Index](http://pypi.python.org/pypi).  Unfortunately it doesn't know how to 
do things like uninstall, so we encourage using [pip](http://pypi.python.org/pypi/pip).  

To install pip, run:  `easy_install pip`

Then: `pip install barrister`

At that point you should be able to run: `barrister -h`

# Language Bindings

The GitHub project for each language includes more detailed information on how to write 
clients and servers using the binding.

<table class="table table-bordered table-striped">
  <tr>
    <th>Language</th>
    <th>GitHub</th>
    <th width="50%">Quick Install</th>
    <th>Notes</th>
  </tr>
  <tr>
    <td>Python</td>
    <td><a href="https://github.com/coopernurse/barrister">barrister</a></td>
    <td width="50%"><code>pip install barrister</code></td>
    <td>Includes core barrister CLI plus Python client/server bindings</td>
  </tr>
  <tr>
    <td>Ruby</td>
    <td><a href="https://github.com/coopernurse/barrister-ruby">barrister-ruby</a></td>
    <td width="50%"><code>gem install barrister</code></td>
    <td>Ruby client/server bindings</td>
  </tr>
  <tr>
    <td>Java</td>
    <td><a href="https://github.com/coopernurse/barrister-java">barrister-java</a></td>
    <td width="50%">See GitHub - Available via Maven</td>
    <td>Java client/server bindings + code generator</td>
  </tr>
  <tr>
    <td>Javascript / Node.js</td>
    <td><a href="https://github.com/coopernurse/barrister-js">barrister-js</a></td>
    <td width="50%"><code>npm install barrister</code></td>
    <td>Node.js client/server bindings. Web browser client only.</td>
  </tr>
  <tr>
    <td>PHP</td>
    <td><a href="https://github.com/coopernurse/barrister-php">barrister-php</a></td>
    <td width="50%"><code>curl -L -o barrister.php http://bit.ly/IHZBGg</code></td>
    <td>PHP client/server bindings</td>
  </tr>

</table>

