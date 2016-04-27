#!/bin/bash

# Script per elencare le risorse JMS disponibili 

ASADMIN=asadmin 

ADMINPORT=4848
HOST=10.11.1.101

echo Listing JMS resources 

${ASADMIN} --echo=true --host ${HOST} --port ${ADMINPORT} list-jms-resources 

