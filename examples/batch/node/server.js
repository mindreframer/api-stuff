var barrister = require('barrister');
var express   = require('express');
var fs        = require('fs');

function Echo() { }
Echo.prototype.echo = function(s, callback) {
    if (s === "err") {
        callback({ code: 99, message: "Error!" }, null);
    }
    else {
        callback(null, s);
    }
};

var idl    = JSON.parse(fs.readFileSync("../batch.json").toString());
var server = new barrister.Server(idl);
server.addHandler("Echo", new Echo());

var app = express.createServer();
app.use(express.bodyParser());
app.post('/batch', function(req, res) {
    server.handle({}, req.body, function(respJson) {
        res.contentType('application/json');
        res.send(respJson);
    });
});
app.listen(7667);