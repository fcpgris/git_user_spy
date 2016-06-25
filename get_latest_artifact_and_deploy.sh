#!/bin/bash

rm -f git-user-spy*
wget `echo -n "http://45.32.8.39:8081/artifactory/libs-snapshot-local/com/eirc/git-user-spy/0.0.1-SNAPSHOT/";curl --silent http://45.32.8.39:8081/artifactory/libs-snapshot-local/com/eirc/git-user-spy/0.0.1-SNAPSHOT/ | grep git-user-spy-0.0.1 | grep -v md5 | grep -v sha1 | tail -n 1 | sed -e 's|^<a.*">\(.*\)</a>.*|\1|'`
mv git-user-spy*.jar git-user-spy.jar
killall java
java -jar git-user-spy.jar
