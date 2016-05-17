#!/bin/bash

# Script per la rimozione delle risorse JMS per le applicazioni asw850 

ASADMIN=asadmin 

ADMINPORT=4848
HOST=10.11.1.101

QUEUE_ONE_NAME=jms/asw/ejb/QueueOne
QUEUE_TWO_NAME=jms/asw/ejb/QueueTwo
CFNAME=jms/asw/ConnectionFactory

echo Deleting JMS resources for asw840

${ASADMIN} --echo=true --host ${HOST} --port ${ADMINPORT} delete-jms-resource ${QUEUE_ONE_NAME} 

${ASADMIN} --echo=true --host ${HOST} --port ${ADMINPORT} delete-jms-resource ${QUEUE_TWO_NAME} 

${ASADMIN} --echo=true --host ${HOST} --port ${ADMINPORT} delete-jms-resource ${CFNAME} 

echo JMS resources for asw840 deleted
