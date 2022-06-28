#!/bin/sh
mvn clean package && docker build -t fr.projet.jee/otaku .
docker rm -f otaku || true && docker run -d -p 9080:9080 -p 9443:9443 --name otaku fr.projet.jee/otaku