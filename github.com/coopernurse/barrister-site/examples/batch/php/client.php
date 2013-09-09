<?php

$path = $_ENV["BARRISTER_PHP"];
include_once("$path/barrister.php");

$barrister = new Barrister();
$client    = $barrister->httpClient("http://localhost:8080/cgi-bin/server.php");
$echo      = $client->proxy("Echo");

echo $echo->echo("hello") . "\n";
try {
    $echo->echo("err");
}
catch (BarristerRpcException $e) {
    echo "err.code=" . $e->getCode() . "\n";
}

$batch = $client->startBatch();

$batchEcho = $batch->proxy("Echo");
$batchEcho->echo("batch 0");
$batchEcho->echo("batch 1");
$batchEcho->echo("err");
$batchEcho->echo("batch 2");
$batchEcho->echo("batch 3");

$results = $batch->send();
foreach ($results as $i=>$res) {
    if ($res->error) {
        echo "err.code=" . $res->error->code . "\n";
    }
    else {
        echo $res->result . "\n";
    }
}

?>