#!/usr/bin/env php
<?php

$path = $_ENV["BARRISTER_PHP"];
include_once("$path/barrister.php");

class EchoServer {

  // echo is a reserved word in PHP. The current workaround is to
  // append an underscore to any reserved method names.  Barrister
  // will try to resolve "[name]_" if the specified
  // function name is not found.
  function echo_($s) {
    if ($s === "err") {
      throw new BarristerRpcException(99, "Error!");
    }
    else {
      return $s;
    }
  }

}

$server = new BarristerServer("../batch.json");
$server->addHandler("Echo", new EchoServer());
$server->handleHTTP();
?>