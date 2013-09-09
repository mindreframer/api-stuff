#!/usr/bin/env python

from flask import Flask, request, make_response
import barrister

class Echo(object):

    def echo(self, s):
        if s == "err":
            raise barrister.RpcException(99, "Error!")
        else:
            return s

contract = barrister.contract_from_file("../batch.json")
server   = barrister.Server(contract)
server.add_handler("Echo", Echo())

app = Flask(__name__)

@app.route("/batch", methods=["POST"])
def batch():
    resp_data = server.call_json(request.data)
    resp = make_response(resp_data)
    resp.headers['Content-Type'] = 'application/json'
    return resp

app.run(host="127.0.0.1", port=7667)
