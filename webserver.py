#!/usr/bin/env python
from BaseHTTPServer import HTTPServer
from CGIHTTPServer import CGIHTTPRequestHandler

serve = HTTPServer(("",8080),CGIHTTPRequestHandler)
serve.serve_forever()
