#!/bin/bash

# Script per l'avvio del server RMI per SessionCounter. 

# Uso: run-server [indirizzo-del-server] 
# Se l'indirizzo-del-server non è specificato, il default 10.11.1.101 (e non localhost). 

if [ $# -gt 0 ] 
then SERVERHOST=$1
else SERVERHOST="10.11.1.101"
fi 

echo Starting Session Counter server su ${SERVERHOST}

# determina il path assoluto in cui si trova lo script 
# (rispetto alla posizione da cui è stata richiesta l'esecuzione dello script) 

REL_PATH_TO_SCRIPT=`dirname $0`
ABS_PATH_TO_SCRIPT=`( cd ${REL_PATH_TO_SCRIPT} && pwd )`

# base dir BD=/home/vagrant/projects/asw-830-rmi/d-session-counter-rmi/dist/server
BD=${ABS_PATH_TO_SCRIPT}

LIB_DIR=${BD}/libs

INTERFACEJAR=${BD}/libs/service.jar
POLICY=${BD}/server.policy
SERVERJAR=${BD}/libs/server.jar

java -cp "${LIB_DIR}/*" -Djava.rmi.server.codebase=file:${INTERFACEJAR} -Djava.rmi.server.hostname=${SERVERHOST} -Djava.security.policy=${POLICY} -jar ${SERVERJAR} 



