#!/usr/bin/env ruby

require 'barrister'

trans  = Barrister::HttpTransport.new("http://localhost:7667/batch")
client = Barrister::Client.new(trans)

puts client.Echo.echo("hello")
begin
  client.Echo.echo("err")
rescue Barrister::RpcException => e
  puts "err.code=#{e.code}"
end

batch = client.start_batch()
batch.Echo.echo("batch 0")
batch.Echo.echo("batch 1")
batch.Echo.echo("err")
batch.Echo.echo("batch 2")
batch.Echo.echo("batch 3")

result = batch.send
result.each do |r|
  # either r.error or r.result will be set
  if r.error
    # r.error is a Barrister::RpcException, so you can raise it if desired
    puts "err.code=#{r.error.code}"
  else
    # result from a successful call
    puts r.result
  end
end

