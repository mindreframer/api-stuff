#!/usr/bin/env ruby

require 'sinatra'
require 'barrister'

class Calculator

  def add(a, b)
    return a+b
  end

  def subtract(a, b)
    return a-b
  end

end

contract = Barrister::contract_from_file("../calc.json")
server   = Barrister::Server.new(contract)
server.add_handler("Calculator", Calculator.new)

post '/calc' do
  request.body.rewind
  resp = server.handle_json(request.body.read)
  
  status 200
  headers "Content-Type" => "application/json"
  resp
end
