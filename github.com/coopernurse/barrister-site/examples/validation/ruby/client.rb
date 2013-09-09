#!/usr/bin/env ruby

require 'barrister'

def assert(b)
  if !b
    raise RuntimeError, "Failed assertion"
  end
end

def now_millis
  return (Time.now.to_f * 1000).floor
end

trans = Barrister::HttpTransport.new("http://localhost:7667/content")
client = Barrister::Client.new(trans)

#
# Test 1 - Try adding a page with incorrect types.  Note that server.py 
#          has no type validation code.  Type enforcement is done
#          automatically by Barrister based on the IDL

invalid_add_page = [
    # use an int for authorId
    [ 1, "title", "body", "sports" ],
    # pass a null title
    [ "author-1", nil, "body", "sports" ],
    # pass a float for body
    [ "author-1", "title", 32.3, "sports" ],
    # pass a bool for category
    [ "author-1", "title", "body", true ],
    # pass an invalid enum value
    [ "author-1", "title", "body", "op-ed" ]
]

invalid_add_page.each do |page_data|
  begin
    client.ContentService.addPage(*page_data)
    abort("addPage allowed invalid data: #{page_data}")
  rescue Barrister::RpcException => e
    # -32602 is the standard JSON-RPC error code for
    # "invalid params", which Barrister uses if types are invalid
    assert e.code == -32602
  end
end

puts "Test 1 - Passed"


#
# Test 2 - Create a page, then test getPage/updatePage cases
#

pageId = client.ContentService.addPage("author-1", "title", "body", "sports")
page   = client.ContentService.getPage(pageId)
assert page != nil

page["title"] = "new title"
page["publishTime"] = now_millis
version = client.ContentService.updatePage(page)
assert version == 2

page2 = client.ContentService.getPage(pageId)
assert page2["title"] == page["title"]
assert page2["publishTime"] == page["publishTime"]

puts "Test 2 - Passed"

#
# Test 3 - Test updatePage type validation
#
def updatePageExpectErr(client, page)
  begin
    client.ContentService.updatePage(page)
    abort("updatePage allowed invalid page: #{page}")
  rescue Barrister::RpcException => e
    assert e.code == -32602
  end
end

page = page2

# Remove required fields one at a time and verify that updatePage rejects request
required_fields = [ "id", "createdTime", "updatedTime", "version", "body", "title" ]
required_fields.each do |field|
  page_copy = page.clone
  page_copy.delete(field)
  updatePageExpectErr(client, page_copy)
end

# Try sending a struct with an extra field
page_copy = page.clone
page_copy["unknown-field"] = "hi"
updatePageExpectErr(client, page_copy)

# Try sending an array with an invalid element type
page_copy = page.clone
page_copy["tags"] = [ "good", "ok", 1 ]
updatePageExpectErr(client, page_copy)

# Try a valid array
page_copy = page.clone
page_copy["tags"] = [ "good", "ok" ]
version = client.ContentService.updatePage(page_copy)
assert version == 3

puts "Test 3 - Passed"


#
# Test 4 - getPage / deletePage
#

# delete non-existing page
assert false == client.ContentService.deletePage("bogus-id", version)

# delete real page
assert true  == client.ContentService.deletePage(page["id"], version)

# get page we just deleted
assert nil == client.ContentService.getPage(page["id"])

puts "Test 4 - Passed"
