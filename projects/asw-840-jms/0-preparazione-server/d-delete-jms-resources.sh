#!/bin/bash

# Script per la rimozione delle risorse JMS per le applicazioni asw840 

ASADMIN=asadmin 

ADMINPORT=4848
HOST=10.11.1.101

QUEUENAME=jms/asw/Queue
TOPICNAME=jms/asw/Topic
CFNAME=jms/asw/ConnectionFactory

echo Deleting JMS resources for asw840

${ASADMIN} --echo=true --host ${HOST} --port ${ADMINPORT} delete-jms-resource ${QUEUENAME} 

${ASADMIN} --echo=true --host ${HOST} --port ${ADMINPORT} delete-jms-resource ${TOPICNAME} 

${ASADMIN} --echo=true --host ${HOST} --port ${ADMINPORT} delete-jms-resource ${CFNAME} 

echo JMS resources for asw840 deleted
