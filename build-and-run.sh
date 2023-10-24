#!/bin/bash

javac -cp ".:lib/*" -d classes src/**/**/*.java
java -cp ".:lib/*:classes" src/com/example/Main
