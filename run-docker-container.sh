#!/bin/bash

image_name=waes-assignment
container_name=assignment

mvn clean install

docker build -t $image_name .
docker container stop $container_name && docker container rm $container_name
docker run -d -p 8080:8080 --name $container_name $image_name