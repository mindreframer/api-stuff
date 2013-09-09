#!/usr/bin/env ruby

require 'barrister'

trans = Barrister::HttpTransport.new("http://localhost:7667/calc")

# automatically connects to endpoint and loads IDL JSON contract
client = Barrister::Client.new(trans)

puts "1+5.1=%.1f" % client.Calculator.add(1, 5.1)
puts "8-1.1=%.1f" % client.Calculator.subtract(8, 1.1)

puts
puts "IDL metadata:"
meta = client.get_meta
[ "barrister_version", "checksum" ].each do |key|
    puts "#{key}=#{meta[key]}"
end
