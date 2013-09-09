#!/usr/bin/env python

import re
import os
import os.path
import pystache
import sys
import time
import socket
import threading
from subprocess import Popen, PIPE

def log(s):
    print s

class Runner(threading.Thread):

    def __init__(self, cwd, name, cmd):
        super(Runner, self).__init__()
        self.cwd = cwd
        self.name = name
        self.cmd = cmd
        self.exit_code = None
        self.proc = None
        self.stdout = ""

    def run(self):
        log("[%s] Starting process: %s" % (self.name, " ".join(self.cmd)))
        self.proc = Popen(self.cmd, stdout=PIPE, stderr=PIPE, close_fds=False, 
                          shell=False, cwd=self.cwd)
        out, err = self.proc.communicate()
        self.stdout += out
        for line in err.split("\n"):
            log("[%s err] %s" % (self.name, line))
        self.exit_code = self.proc.poll()

    def stop(self):
        log("[%s] Stopping" % self.name)
        self.proc.terminate()
        self.join()
        log("[%s] Terminated" % self.name)

example_re = re.compile(r"\[\[example-(\w+)\]\]")

example_header ="""---
layout: default
title: Barrister RPC - Examples
---

# Examples

Note about the Java server examples: The server classes are simply the interface implementations.
The code is exposed using the provided `BarristerServlet` class via web.xml.  Examples are run using
the Maven Jetty plugin.  For more details 
[see the example source](https://github.com/coopernurse/barrister-site/tree/master/examples/calc/java).

"""

section = """
<div class="tabbable">
  <ul class="nav nav-tabs">
    <li class="active"><a href="#{{example}}-idl" data-toggle="tab">IDL</a></li>
    <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Servers<b class="caret"></b></a>
        <ul class="dropdown-menu">
          {{#langs}}
            <li><a href="#{{example}}-{{lang}}-server" data-toggle="tab">{{lang}}</a></li>
          {{/langs}}
        </ul>
    </li>
    <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Clients<b class="caret"></b></a>
        <ul class="dropdown-menu">
          {{#langs}}
            <li><a href="#{{example}}-{{lang}}-client" data-toggle="tab">{{lang}}</a></li>
          {{/langs}}
        </ul>
    </li>
    <li><a href="#{{example}}-output" data-toggle="tab">Output</a></li>

  </ul>
  <div class="tab-content">
    <div class="tab-pane active" id="{{example}}-idl">
{% highlight go %}
{{idl}}
{% endhighlight %}
    </div>
  {{#langs}}
    <div class="tab-pane" id="{{example}}-{{lang}}-server">
{% highlight {{highlight-lang}} %}
{{{servercode}}}
{% endhighlight %}
    </div>
    <div class="tab-pane" id="{{example}}-{{lang}}-client">
{% highlight {{highlight-lang}} %}
{{{clientcode}}}
{% endhighlight %}
    </div>
  {{/langs}}
    <div class="tab-pane" id="{{example}}-output">
<pre>{{output}}</pre>
    </div>
  </div>
</div>
"""

def strip_shbang(s):
    lines = s.split("\n")
    if lines[0].startswith("#!"):
        return "\n".join(lines[1:])
    else:
        return s

def read_file(fname):
    f = open(fname)
    s = f.read()
    f.close()
    return s

def run_python(basedir):
    client = Runner(basedir, "%s client" % basedir, [ "python", "client.py" ])
    server = Runner(basedir, "%s server" % basedir, [ "python", "server.py" ])
    return run_lang(client, server, 7667)

def run_node(basedir):
    client = Runner(basedir, "%s client" % basedir, [ "node", "client.js" ])
    server = Runner(basedir, "%s server" % basedir, [ "node", "server.js" ])
    return run_lang(client, server, 7667)

def run_php(basedir):
    client = Runner(basedir, "%s client" % basedir, [ "php", "client.php" ])
    server = Runner(basedir, "%s server" % basedir, [ "python", "../../../webserver.py" ])
    return run_lang(client, server, 8080)

def run_ruby(basedir):
    incl = "-I%s/lib" % os.environ["BARRISTER_RUBY"]
    client = Runner(basedir, "%s client" % basedir, [ "ruby", "-rubygems", incl, "client.rb" ])
    server = Runner(basedir, "%s server" % basedir, [ "ruby", "-rubygems", incl, "server.rb", "-p", "7667" ])
    return run_lang(client, server, 7667)

def run_java(basedir):
    # generate code from idl
    safe_exec(basedir, [ "./codegen.sh" ])
    client = Runner(basedir, "%s client" % basedir, [ "./client.sh" ])
    server = Runner(basedir, "%s server" % basedir, [ "mvn", "clean", "jetty:run" ])
    return run_lang(client, server, 8080)

def run_lang(client, server, port):
    server.start()
    try:
        poll_for_port(port)
    except:
        server.stop()
        sys.exit(1)
    client.start()
    client.join()
    server.stop()
    return client.stdout    

def run_example(basedir, lang, expected):
    output = ""
    if lang == "python":
        output = run_python(basedir)
    elif lang == "node":
        output = run_node(basedir)
    elif lang == "java":
        output = run_java(basedir)
    elif lang == "php":
        output = run_php(basedir)
    elif lang == "ruby":
        output = run_ruby(basedir)

    if output != expected:
        print "Failed example: '%s'" % basedir
        print
        print "Expected:"
        print expected
        print "Actual:"
        print output
        sys.exit(1)

def safe_exec(cwd, cmd):
    p = Popen(cmd, stdout=PIPE, stderr=PIPE, close_fds=False, shell=False, cwd=cwd)
    out, err = p.communicate()
    exit_code = p.poll()
    if exit_code != 0:
        print "Error running: %s" % cmd
        print "STDOUT: %s" % out
        print "STDERR: %s" % err
        sys.exit(1)

def translate_idl(basedir, name):
    cmd = [ "barrister", "-j", "%s.json" % name, "-d", "%s.html" % name, "%s.idl" % name ]
    safe_exec(basedir, cmd)

def run_all_examples():
    example_html = { }
    for name in os.listdir("examples"):
        if os.path.isdir(os.path.join("examples", name)):
            example_html[name] = get_example(name)
    return example_html

def poll_for_port(port, timeout=30):
    stop = time.time() + timeout
    while time.time() < stop:
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        result = s.connect_ex(('127.0.0.1', port))
        if result == 0:
            s.close()
            return
        else:
            time.sleep(.1)
    raise Exception("timed out trying to connect to port %d" % port)

def get_example(example):
    lang_to_src = {
        "python" : { "client" : [ "client.py" ], "server" : [ "server.py" ] },
        "node" : { "client" : [ "client.js" ], "server" : [ "server.js" ] },
        "ruby" : { "client" : [ "client.rb" ], "server" : [ "server.rb" ] },
        "java" : { "client" : [ "src/main/java/example/Client.java" ], 
                   "server" : [ "src/main/java/example/Server.java" ] },
        "php" : { "client" : [ "client.php" ], "server" : [ "cgi-bin/server.php" ] }
    }

    example_dir = os.path.join("examples", example)
    data = { }
    data["example"] = example
    data["langs"]   = [ ]
    data["idl"]     = read_file(os.path.join(example_dir, "%s.idl" % example))
    data["output"]  = read_file(os.path.join(example_dir, "output.txt"))
    translate_idl(example_dir, example)
    for fname in os.listdir(example_dir):
        rel_path = os.path.join(example_dir, fname)
        if os.path.isdir(rel_path):
            run_example(rel_path, fname, data["output"])
            lang = { "servercode":"", "clientcode":"" }
            lang["lang"] = fname
            lang["highlight-lang"] = fname
            if fname == "node":
                lang["highlight-lang"] = "javascript"
            lang["example"] = example

            src_files = lang_to_src[fname]
            for lang_file in src_files["server"]:
                lang["servercode"] += strip_shbang(read_file(os.path.join(rel_path, lang_file)))
            for lang_file in src_files["client"]:
                lang["clientcode"] += strip_shbang(read_file(os.path.join(rel_path, lang_file)))

            data["langs"].append(lang)
    return pystache.render(section, data, escape=lambda u: u)

def cook_example_page(examples_html):
    example_order = [ "calc", "batch", "validation", "redis-msgpack" ]
    f = open("examples.md", "w")
    f.write(example_header)
    for key in example_order:
        f.write(read_file(os.path.join("examples", key, "README.md")))
        f.write("\n")
        f.write(examples_html[key])
        f.write("\n----\n")
    f.close()

def cook(infile, outfile):
    f = open(infile)
    contents = f.read()
    f.close()
    s = ""
    curpos = 0
    for chunk in example_re.finditer(contents):
         s += contents[curpos:chunk.start()]
         name = chunk.group(1)
         s += get_example(name)
         curpos = chunk.end()+1
    s += contents[curpos:]

    f = open(outfile, "w")
    f.write(s)
    f.close()


if __name__ == "__main__":
    reqenv = [ "BARRISTER_JAVA", "BARRISTER_RUBY", "BARRISTER_PHP" ]
    for e in reqenv:
        if not os.environ.has_key(e):
            print "Set required environment variable: %s" % e
            sys.exit(1)
    if len(sys.argv) == 1:
        cook("index.md.tmpl", "index.md")
    elif sys.argv[1] == "all":
        cook_example_page(run_all_examples())
    else:
        get_example(sys.argv[1])
