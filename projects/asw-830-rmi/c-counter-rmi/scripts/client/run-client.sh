#!/bin/bash

# Script per l'avvio del client RMI per Counter. 

# Uso: run-client [indirizzo-del-server] 
# Se l'indirizzo-del-server non è specificato, il default 10.11.1.101 (e non localhost). 

if [ $# -gt 0 ] 
then SERVERHOST=$1
else SERVERHOST="10.11.1.101"
fi 

echo Starting counter client [servizio su ${SERVERHOST}]

# determina il path assoluto in cui si trova lo script 
# (rispetto alla posizione da cui è stata richiesta l'esecuzione dello script) 

REL_PATH_TO_SCRIPT=`dirname $0`
ABS_PATH_TO_SCRIPT=`( cd ${REL_PATH_TO_SCRIPT} && pwd )`

# base directory BD=/home/vagrant/projects/asw-830-rmi/c-counter-rmi/dist/client
BD=${ABS_PATH_TO_SCRIPT}

LIB_DIR=${BD}/libs

INTERFACEJAR=${BD}/libs/service.jar
POLICY=${BD}/client.policy
CLIENTJAR=${BD}/libs/client.jar
RMITIMEOUT=5000

java -cp "${LIB_DIR}/*" -Djava.rmi.server.codebase=file:${INTERFACEJAR} -Djava.security.policy=${POLICY} -Dsun.rmi.transport.tcp.responseTimeout=${RMITIMEOUT} -jar ${CLIENTJAR} ${SERVERHOST}

