#!/usr/bin/env python

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
