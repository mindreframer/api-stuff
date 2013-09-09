#!/usr/bin/env python

from flask import Flask, request, make_response
import barrister
import uuid
import time

def now_millis():
    return int(time.time() * 1000)

def create_page(authorId, title, body, category, publishTime=None):
    now_ms = now_millis()
    return { "id"          : uuid.uuid4().hex,
             "version"     : 1,
             "createdTime" : now_ms,
             "updatedTime" : now_ms,
             "authorId"    : authorId,
             "title"       : title,
             "body"        : body,
             "category"    : category,
             "publishTime" : publishTime }

class ContentService(object):

    def __init__(self):
        self.pagesById = { }

    def addPage(self, authorId, title, body, category):
        page = create_page(authorId, title, body, category)
        self.pagesById[page["id"]] = page
        return page["id"]

    def updatePage(self, page):
        existing = self.getPage(page["id"])
        if not existing:
            raise barrister.RpcException(40, "No page exists with id: %s" % page["id"])
        elif existing["version"] != page["version"]:
            raise barrister.RpcException(30, "Version is out of date")
        else:
            version = existing["version"] + 1
            page["version"]     = version
            page["createdTime"] = existing["createdTime"]
            page["updatedTime"] = now_millis()
            self.pagesById[page["id"]] = page
            return version

    def deletePage(self, id, version):
        existing = self.getPage(id)
        if existing:
            if existing["version"] == version:
                del self.pagesById[id]
                return True
            else:
                raise barrister.RpcException(30, "Version is out of date")
        else:
            return False

    def getPage(self, id):
        if self.pagesById.has_key(id):
            return self.pagesById[id]
        else:
            return None

contract = barrister.contract_from_file("../validation.json")
server   = barrister.Server(contract)
server.add_handler("ContentService", ContentService())

app = Flask(__name__)

@app.route("/content", methods=["POST"])
def content():
    resp_data = server.call_json(request.data)
    resp = make_response(resp_data)
    resp.headers['Content-Type'] = 'application/json'
    return resp

app.run(host="127.0.0.1", port=7667)
