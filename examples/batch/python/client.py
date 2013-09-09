#!/usr/bin/env python

import barrister

trans  = barrister.HttpTransport("http://localhost:7667/batch")
client = barrister.Client(trans)

print client.Echo.echo("hello")
try:
    client.Echo.echo("err")
except barrister.RpcException as e:
    print "err.code=%d" % e.code

batch = client.start_batch()
batch.Echo.echo("batch 0")
batch.Echo.echo("batch 1")
batch.Echo.echo("err")
batch.Echo.echo("batch 2")
batch.Echo.echo("batch 3")

results = batch.send()
for res in results:
    if res.result:
        print res.result
    else:
        # res.error is a barrister.RpcException
        # you can throw it here if desired
        print "err.code=%d" % res.error.code

