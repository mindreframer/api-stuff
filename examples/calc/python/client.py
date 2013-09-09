#!/usr/bin/env python

import barrister

trans  = barrister.HttpTransport("http://localhost:7667/calc")

# automatically connects to endpoint and loads IDL JSON contract
client = barrister.Client(trans)

print "1+5.1=%.1f" % client.Calculator.add(1, 5.1)
print "8-1.1=%.1f" % client.Calculator.subtract(8, 1.1)

print
print "IDL metadata:"
meta = client.get_meta()
for key in [ "barrister_version", "checksum" ]:
    print "%s=%s" % (key, meta[key])

# not printing this one because it changes per run, which breaks our
# very literal 'examples' test harness, but let's verify it exists at least..
assert meta.has_key("date_generated")

