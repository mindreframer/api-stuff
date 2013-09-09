<?php

$path = $_ENV["BARRISTER_PHP"];
include_once("$path/barrister.php");

$barrister = new Barrister();
$client    = $barrister->httpClient("http://localhost:8080/cgi-bin/server.php");
$calc      = $client->proxy("Calculator");

echo sprintf("1+5.1=%.1f\n", $calc->add(1, 5.1));
echo sprintf("8-1.1=%.1f\n", $calc->subtract(8, 1.1));

echo "\nIDL metadata:\n";
$meta = $client->getMeta();
$keys = array("barrister_version", "checksum");
foreach ($keys as $i=>$key) {
  echo "$key=$meta[$key]\n";
}

?>