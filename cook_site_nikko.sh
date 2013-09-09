#!/bin/sh

set -e

export GH=/home/james/github
export BARRISTER=$GH/barrister
export BARRISTER_JAVA=$GH/barrister-java
export BARRISTER_NODE=$GH/barrister-js
export BARRISTER_RUBY=$GH/barrister-ruby
export BARRISTER_PHP=$GH/barrister-php
export PYTHONPATH=$BARRISTER
export NODE_PATH=/usr/local/lib/node_modules:$BARRISTER/node_modules

export JAVA_HOME=/usr/local/java
export PATH=$BARRISTER/bin:$BARRISTER_JAVA:/usr/local/bin:$PATH:/home/james/github/barrister-site:$JAVA_HOME/bin:/usr/local/maven/bin

git pull

. $BARRISTER/env/bin/activate
pip install -r $BARRISTER/requirements.txt
pip install pystache msgpack-python redis 
npm install express@2.5.9
./cook_examples.py all

jekyll build
rsync -avz _site/ james@barrister.bitmechanic.com:/home/james/barrister-site/
