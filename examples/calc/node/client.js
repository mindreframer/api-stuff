var barrister = require('barrister');

function checkErr(err) {
    if (err) {
        console.log("ERR: " + JSON.stringify(err));
        process.exit(1);
    }
}

var client = barrister.httpClient("http://localhost:7667/calc");

client.loadContract(function(err) {
    checkErr(err);

    var calc = client.proxy("Calculator");

    calc.add(1, 5.1, function(err, result) {
        var i;
        checkErr(err);
        console.log("1+5.1=" + result);

        calc.subtract(8, 1.1, function(err, result) {
            checkErr(err);
            console.log("8-1.1=" + result);

            console.log("\nIDL metadata:");
            meta = client.getMeta();
            keys = [ "barrister_version", "checksum" ];
            for (i = 0; i < keys.length; i++) {
                console.log(keys[i] + "=" + meta[keys[i]]);
            }
        });
    });
});
