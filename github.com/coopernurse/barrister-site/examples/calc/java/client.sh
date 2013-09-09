#!/bin/sh

set -e

jackson_m2=$HOME/.m2/repository/org/codehaus/jackson
CLASSPATH=$jackson_m2/jackson-mapper-asl/1.9.4/jackson-mapper-asl-1.9.4.jar:$jackson_m2/jackson-core-asl/1.9.4/jackson-core-asl-1.9.4.jar:$BARRISTER_JAVA/target/classes:target/classes
java -cp $CLASSPATH example.Client
