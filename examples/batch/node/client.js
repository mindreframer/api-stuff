var barrister = require('barrister');

function runSync(funcs) {
    if (funcs.length > 0) {
        var nextFunction = funcs.shift();

        nextFunction(function(err, result) {
            var i;
            if (err) {
                console.log("err.code=" + err.code);
            }
            else {
                if (result instanceof Array) {
                    // handle batch result
                    for (i = 0; i < result.length; i++) {
                        if (result[i].error) {
                            console.log("err.code=" + result[i].error.code);
                        }
                        else {
                            console.log(result[i].result);
                        }
                    }
                }
                else {
                    // handle single result
                    console.log(result);
                }
            }
            runSync(funcs);
        });

    }
}

////////

var client = barrister.httpClient("http://localhost:7667/batch");

client.loadContract(function(err) {
    if (err) { 
        console.log("error loading contract");
        process.exit(1);
    }

    var echo  = client.proxy("Echo");

    var batch = client.startBatch();
    var batchEcho = batch.proxy("Echo");

    var funcs = [
        function(next) { echo.echo("hello", next); },
        function(next) { echo.echo("err", next); },
        function(next) {
            batchEcho.echo("batch 0");
            batchEcho.echo("batch 1");
            batchEcho.echo("err");
            batchEcho.echo("batch 2");
            batchEcho.echo("batch 3");
            batch.send(next);
        }
    ];
    runSync(funcs);
});
