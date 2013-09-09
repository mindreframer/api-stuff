#!/usr/bin/env python

import barrister
import sys
import copy
import time

trans  = barrister.HttpTransport("http://localhost:7667/content")
client = barrister.Client(trans)

#
# Test 1 - Try adding a page with incorrect types.  Note that server.py 
#          has no type validation code.  Type enforcement is done
#          automatically by Barrister based on the IDL

invalid_add_page = [
    # use an int for authorId
    [ 1, "title", "body", "sports" ],
    # pass a null title
    [ "author-1", None, "body", "sports" ],
    # pass a float for body
    [ "author-1", "title", 32.3, "sports" ],
    # pass a bool for category
    [ "author-1", "title", "body", True ],
    # pass an invalid enum value
    [ "author-1", "title", "body", "op-ed" ]
]

for page_data in invalid_add_page:
    try:
        client.ContentService.addPage(*page_data)
        print "addPage allowed invalid data: %s" % page_data
        sys.exit(1)
    except barrister.RpcException as e:
        # -32602 is the standard JSON-RPC error code for
        # "invalid params", which Barrister uses if types are invalid
        assert e.code == -32602

print "Test 1 - Passed"


#
# Test 2 - Create a page, then test getPage/updatePage cases
#

pageId = client.ContentService.addPage("author-1", "title", "body", "sports")
page   = client.ContentService.getPage(pageId)
assert page != None

page["title"] = "new title"
page["publishTime"] = int(time.time() * 1000)
version = client.ContentService.updatePage(page)
assert version == 2

page2 = client.ContentService.getPage(pageId)
assert page2["title"] == page["title"]
assert page2["publishTime"] == page["publishTime"]

print "Test 2 - Passed"

#
# Test 3 - Test updatePage type validation
#
def updatePageExpectErr(page):
    try:
        client.ContentService.updatePage(page)
        print "updatePage allowed invalid page: %s" % str(page)
        sys.exit(1)
    except barrister.RpcException as e:
        assert e.code == -32602

page = page2

# Remove required fields one at a time and verify that updatePage rejects request
required_fields = [ "id", "createdTime", "updatedTime", "version", "body", "title" ]
for field in required_fields:
    page_copy = copy.copy(page)
    del page_copy[field]
    updatePageExpectErr(page_copy)

# Try sending a struct with an extra field
page_copy = copy.copy(page)
page_copy["unknown-field"] = "hi"
updatePageExpectErr(page_copy)

# Try sending an array with an invalid element type
page_copy = copy.copy(page)
page_copy["tags"] = [ "good", "ok", 1 ]
updatePageExpectErr(page_copy)

# Try a valid array
page_copy = copy.copy(page)
page_copy["tags"] = [ "good", "ok" ]
version = client.ContentService.updatePage(page_copy)
assert version == 3

print "Test 3 - Passed"


#
# Test 4 - getPage / deletePage
#

# delete non-existing page
assert False == client.ContentService.deletePage("bogus-id", version)

# delete real page
assert True  == client.ContentService.deletePage(page["id"], version)

# get page we just deleted
assert None == client.ContentService.getPage(page["id"])

print "Test 4 - Passed"
