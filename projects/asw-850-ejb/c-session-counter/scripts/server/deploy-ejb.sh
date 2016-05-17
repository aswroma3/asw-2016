#!/bin/bash

# Script per il rilascio dell'EJB 

echo Deploying session counter EJB 

EJB_INTERFACE_JAR=session-counter-ejb-interface.jar 
EJB_IMPL_JAR=session-counter-ejb-impl.jar 
EJB_NAME=SessionCounter 

# determina il path relativo e assoluto in cui si trova lo script 
# (rispetto alla posizione da cui è stata richiesta l'esecuzione dello script) 
REL_PATH_TO_SCRIPT=`dirname $0`
# ABS_PATH_TO_SCRIPT=`( cd ${REL_PATH_TO_SCRIPT} && pwd )`

# base dir 
BD=${REL_PATH_TO_SCRIPT}

LIB_DIR=${BD}/libs

sudo asadmin add-library --type app ${LIB_DIR}/${EJB_INTERFACE_JAR}
sudo asadmin deploy --name ${EJB_NAME} --libraries ${EJB_INTERFACE_JAR} ${LIB_DIR}/${EJB_IMPL_JAR}



