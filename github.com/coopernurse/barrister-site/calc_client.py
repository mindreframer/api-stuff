#!/usr/bin/env python
    
import barrister
import sys
    
trans  = barrister.HttpTransport(sys.argv[1])
client = barrister.Client(trans)
    
print "1+5.1=%.1f" % client.Calculator.add(1, 5.1)
print "8-1.1=%.1f" % client.Calculator.subtract(8, 1.1)
