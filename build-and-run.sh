#!/bin/bash

javac -cp ".:lib/jsch-0.1.55.jar" -d classes src/**/**/*.java
java -cp ".:lib/jsch-0.1.55.jar:classes" src/com/example/Main
