#!/bin/bash

# Script per la creazione delle risorse JMS per le applicazioni asw840 

ASADMIN=asadmin 

ADMINPORT=4848
HOST=10.11.1.101

QUEUENAME=jms/asw/Queue
TOPICNAME=jms/asw/Topic
CFNAME=jms/asw/ConnectionFactory

echo Creating JMS resources for asw840

${ASADMIN} --echo=true --host ${HOST} --port ${ADMINPORT} create-jms-resource --restype javax.jms.Queue --enabled=true ${QUEUENAME} 

${ASADMIN} --echo=true --host ${HOST} --port ${ADMINPORT} create-jms-resource --restype javax.jms.Topic --enabled=true ${TOPICNAME} 

${ASADMIN} --echo=true --host ${HOST} --port ${ADMINPORT} create-jms-resource --restype javax.jms.ConnectionFactory --enabled=true ${CFNAME} 

echo JMS resources for asw840 created
