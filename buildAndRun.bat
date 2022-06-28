@echo off
call mvn clean package
call docker build -t fr.projet.jee/otaku .
call docker rm -f otaku
call docker run -d -p 9080:9080 -p 9443:9443 --name otaku fr.projet.jee/otaku