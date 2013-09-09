#!/usr/bin/env ruby

require 'sinatra'
require 'barrister'

def now_millis
  return (Time.now.to_f * 1000).floor
end

def create_page(authorId, title, body, category, publishTime=nil)
    now_ms = now_millis()
    return { "id"          => Barrister::rand_str(24),
             "version"     => 1,
             "createdTime" => now_ms,
             "updatedTime" => now_ms,
             "authorId"    => authorId,
             "title"       => title,
             "body"        => body,
             "category"    => category,
             "publishTime" => publishTime }
end

class ContentService

  def initialize
    @pagesById = { }
  end

  def addPage(authorId, title, body, category)
    page = create_page(authorId, title, body, category)
    @pagesById[page["id"]] = page
    return page["id"]
  end

  def updatePage(page)
    existing = getPage(page["id"])
    if !existing
      raise Barrister::RpcException.new(40, "No page exists with id: " + page["id"])
    elsif existing["version"] != page["version"]
      raise Barrister::RpcException.new(30, "Version out of date")
    else
      version = existing["version"] + 1
      page["version"]     = version
      page["createdTime"] = existing["createdTime"]
      page["updatedTime"] = now_millis
      @pagesById[page["id"]] = page
      return version
    end
  end

  def deletePage(id, version)
    existing = getPage(id)
    if existing
      if existing["version"] == version
        @pagesById.delete(id)
        return true
      else
        raise Barrister::RpcException.new(30, "Version out of date")
      end
    else
      return false
    end
  end

  def getPage(id)
    return @pagesById[id]
  end

end

contract = Barrister::contract_from_file("../validation.json")
server   = Barrister::Server.new(contract)
server.add_handler("ContentService", ContentService.new)

post '/content' do
  request.body.rewind
  resp = server.handle_json(request.body.read)
  
  status 200
  headers "Content-Type" => "application/json"
  resp
end
