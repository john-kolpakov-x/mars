#!/bin/sh

cd $(dirname $0)

rm -rf build

../gradlew war

mkdir -p build/for_docker/ROOT

unzip build/libs/*.war -d build/for_docker/ROOT

docker build -t "mars:0.0.2" .
