#!/usr/bin/env python

from flask import Flask, request, Response, make_response
from functools import wraps
from multiprocessing import Process
import barrister
import redis
import msgpack
import json
import uuid
import threading
import signal
import select

# Helper functions to serialize/deserialize the msgpack messages
def dump_msg(headers, body):
    return msgpack.dumps([ headers, body ])
    
def load_msg(raw):
    return msgpack.loads(raw)

# Auth code from: http://flask.pocoo.org/snippets/8/
def check_auth(username, password):
    """This function is called to check if a username /
    password combination is valid.
    """
    return username == 'johndoe' and password == 'johnpass'

def authenticate():
    """Sends a 401 response that enables basic auth"""
    return Response(
    'Could not verify your access level for that URL.\n'
    'You have to login with proper credentials', 401,
    {'WWW-Authenticate': 'Basic realm="Login Required"'})

def requires_auth(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        auth = request.authorization
        if not auth or not check_auth(auth.username, auth.password):
            return authenticate()
        return f(*args, **kwargs)
    return decorated

###############################################################
# Router -- Accepts HTTP requests and sends to Redis
#
# Doesn't actually process any messages.  Has no domain
# specific code, aside from user authentication.
###############################################################

app = Flask(__name__)

# generic redis bridging function
def bridge_to_redis_backend(queue, http_req):
    # unpack request JSON and reserialize w/msgpack
    req = json.loads(http_req.data)
    
    # create a headers map that contains a 'reply_to' key that is unique
    # the worker will send the response to that key, and we'll dequeue it
    # from there.
    headers = { "reply_to" : "reply-" + uuid.uuid4().hex }
    
    # if we have HTTP auth, add the username to the headers
    # so that downstream processes know the user context that originated
    # the request, and can apply additional security rules as desired
    if http_req.authorization:
        headers["username"] = http_req.authorization.username
    
    msg_to_redis = dump_msg(headers, req)
    
    # send to redis
    redis_client = redis.StrictRedis("localhost", port=6379)
    redis_client.lpush(queue, msg_to_redis)
    
    # block for 30 seconds for a reply on our reply_to queue
    raw_resp = redis_client.brpop(headers["reply_to"], timeout=30)
    
    if raw_resp:
        (headers, resp) = load_msg(raw_resp[1])
    else:
        errmsg = "30 second timeout on queue: %s" % queue
        resp = { "jsonrpc" : "2.0", "error" : { "code" : -32700, "message" : errmsg } }
    
    return resp

@app.route("/contact", methods=["POST"])
@requires_auth
def contact():
    # Send to redis -- let some backend worker process it
    resp = bridge_to_redis_backend("contact", request)
    
    # serialize as JSON and send to client
    http_resp = make_response(json.dumps(resp))
    http_resp.headers['Content-Type'] = 'application/json'
    return http_resp

def start_router():
    try:
        app.run(host="127.0.0.1", port=7667)
    except select.error:
        pass

###############################################################
# Worker -- This is the Barrister Server process.  It polls
#        Redis for requests, and processes them.  In practice
#        this would be a separate daemon.  With Redis as a
#        central broker, you could run as many copies of this
#        process as you wish to balance load.
#################################################################

class ContactService(object):

    def __init__(self, req_context):
        """
        req_context is a thread local variable that we use to
        share out of band context.  In this example we use it
        to give this class access to the headers on the request,
        which are used to enforce security rules
        """
        self.contacts    = { }
        self.req_context = req_context

    def put(self, contact):
        existing = self._get(contact["contactId"])
        if existing:
            self._check_contact_owner(existing)
        
        self._check_contact_owner(contact)
        self.contacts[contact["contactId"]] = contact
        return contact["contactId"]
        
    def get(self, contactId):
        c = self._get(contactId)
        if c:
            self._check_contact_owner(c)
            return c
        else:
            return None
        
    def remove(self, contactId):
        c = self._get(contactId)
        if c:
            self._check_contact_owner(c)
            del self.contacts[contactId]
            return True
        else:
            return False
            
    def _get_username(self):
        """
        Grabs the username from the thread local context
        """
        headers = self.req_context.headers
        try:
            return headers["username"]
        except:
            return None
            
    def _get(self, contactId):
        try:
            return self.contacts[contactId]
        except:
            return None
    
    def _check_contact_owner(self, contact):
        username = self._get_username()
        if not username or username != contact["username"]:
            raise barrister.RpcException(4000, "Permission Denied for user")
            
def start_worker():
    # create a thread local that we can use to store request headers
    req_context = threading.local()
    
    contract = barrister.contract_from_file("../redis-msgpack.json")
    server   = barrister.Server(contract)
    server.add_handler("ContactService", ContactService(req_context))
    
    redis_client = redis.StrictRedis("localhost", port=6379)
    while True:
        raw_msg = redis_client.brpop([ "contact" ], timeout=1)
        if raw_msg:
            (headers, req) = load_msg(raw_msg[1])
            if headers.has_key("reply_to"):
                tls  = threading.local()
                # set the headers on the thread local req_context
                req_context.headers = headers
                resp = server.call(req)
                redis_client.lpush(headers["reply_to"], dump_msg(headers, resp))
                req_context.headers = None

if __name__ == "__main__":
    # In a real system the router and worker would probably be
    # separate processes.  For this demo we're combining them
    # for simplicity
    worker_proc = Process(target=start_worker)
    worker_proc.daemon = True
    worker_proc.start()

    def handler(signum, frame):
        worker_proc.terminate()
        
    signal.signal(signal.SIGTERM, handler)
    start_router()
    
