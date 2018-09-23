#!/usr/bin/env bash
mvn clean compile assembly:single
mv ./target/lexical-analyzer-1.0-SNAPSHOT-jar-with-dependencies.jar .