#!/bin/bash

# Script per l'avvio del server per un servizio. 

echo Running server 

# Uso: run-server  

echo Starting server 

# determina il path relativo in cui si trova lo script 
# (rispetto alla posizione da cui è stata richiesta l'esecuzione dello script) 
PATH_TO_SCRIPT=`dirname $0`

LIB_DIR=${PATH_TO_SCRIPT}/libs
SERVER_JAR=${LIB_DIR}/server.jar

java -cp "${LIB_DIR}/*" -jar ${SERVER_JAR} 

