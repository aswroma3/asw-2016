#!/bin/bash

# Script per l'avvio del client per un servizio. 

echo Running client 

# Uso: run-client [indirizzo-del-server] 
# Se l'indirizzo-del-server non è specificato, il default è 10.11.1.101 (e non localhost). 

if [ $# -gt 0 ] 
then SERVERHOST=$1
else SERVERHOST="10.11.1.101"
fi 

echo Starting client - server su ${SERVERHOST}

# determina il path relativo in cui si trova lo script 
# (rispetto alla posizione da cui è stata richiesta l'esecuzione dello script) 
PATH_TO_SCRIPT=`dirname $0`

LIB_DIR=${PATH_TO_SCRIPT}/libs
CLIENT_JAR=${LIB_DIR}/client.jar

java -cp "${LIB_DIR}/*" -jar ${CLIENT_JAR} ${SERVERHOST}


