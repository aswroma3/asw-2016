#!/bin/bash

# Script per l'arresto di Java DB Server 

ASADMIN=asadmin 

ADMINPORT=4848
HOST=10.11.1.101

echo "Stopping Java DB Server"

${ASADMIN} --echo=true --host ${HOST} --port ${ADMINPORT} stop-database 

echo "Java DB Server stopped"
