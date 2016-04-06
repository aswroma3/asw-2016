#!/bin/bash

# Script per l'avvio del registry RMI. 

# Uso: start-rmiregistry 
# Nota: si potrebbe specificare la porta, ma non l'indirizzo del server. 

echo Starting rmiregistry 

# determina il path assoluto in cui si trova lo script 
# (rispetto alla posizione da cui è stata richiesta l'esecuzione dello script) 

REL_PATH_TO_SCRIPT=`dirname $0`
ABS_PATH_TO_SCRIPT=`( cd ${REL_PATH_TO_SCRIPT} && pwd )`

# base dir BD=/home/vagrant/projects/asw-830-rmi/c-counter-rmi/dist/server
BD=${ABS_PATH_TO_SCRIPT}

LIB_DIR=${BD}/libs

# unset CLASSPATH
export CLASSPATH=${LIB_DIR}/service.jar

rmiregistry & 

