#!/usr/bin/env php
<?php

$path = $_ENV["BARRISTER_PHP"];
include_once("$path/barrister.php");

class Calculator {

  function add($a, $b) {
    return $a + $b;
  }

  function subtract($a, $b) {
    return $a - $b;
  }

}

$server = new BarristerServer("../calc.json");
$server->addHandler("Calculator", new Calculator());
$server->handleHTTP();
?>