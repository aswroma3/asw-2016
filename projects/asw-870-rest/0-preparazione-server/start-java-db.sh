#!/bin/bash

# Script per l'avvio di Java DB Server 

ADMINPORT=4848
HOST=10.11.1.101

ASADMIN="asadmin --echo=true --host ${HOST} --port ${ADMINPORT}"

JAVADB_HOME=${GLASSFISH_HOME}/javadb

echo "Starting Java DB Server"

sudo ${ASADMIN} start-database --dbhome ${JAVADB_HOME}

echo "Java DB Server started"
