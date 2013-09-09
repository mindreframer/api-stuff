#!/usr/bin/env ruby

require 'sinatra'
require 'barrister'

class Echo

  def echo(s)
    if s == "err"
      raise Barrister::RpcException.new(99, "Error!")
    else
      return s
    end
  end

end

contract = Barrister::contract_from_file("../batch.json")
server   = Barrister::Server.new(contract)
server.add_handler("Echo", Echo.new)

post '/batch' do
  request.body.rewind
  resp = server.handle_json(request.body.read)
  
  status 200
  headers "Content-Type" => "application/json"
  resp
end
