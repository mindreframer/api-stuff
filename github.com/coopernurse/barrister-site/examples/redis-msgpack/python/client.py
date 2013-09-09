#!/usr/bin/env python

import barrister
import urllib2
import sys

password_mgr = urllib2.HTTPPasswordMgrWithDefaultRealm()
password_mgr.add_password(None, 'http://localhost:7667/','johndoe','johnpass')
auth_handler = urllib2.HTTPBasicAuthHandler(password_mgr)

trans  = barrister.HttpTransport("http://localhost:7667/contact", 
                                 handlers=[auth_handler])
client = barrister.Client(trans)

contact = {
    "contactId" : "1234",
    "username"  : "johndoe",
    "firstName" : "Mary",
    "lastName"  : "Smith"
}

contactId = client.ContactService.put(contact)
print "put contact: %s" % contactId

contact2 = client.ContactService.get(contactId)
assert contact2 == contact

deleted = client.ContactService.remove(contactId)
assert deleted == True

# Try to be naughty and create a contact for another user
contact = {
    "contactId" : "12345",
    "username"  : "sally",
    "firstName" : "Ed",
    "lastName"  : "Henderson"
}

try:
    # should fail with error.code=4000
    client.ContactService.put(contact)
    
    print "Error! Server allowed us to act as another user"
    sys.exit(1)
except barrister.RpcException as e:
    assert e.code == 4000
    
print "OK"
