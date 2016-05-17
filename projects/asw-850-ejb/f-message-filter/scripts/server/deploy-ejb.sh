#!/bin/bash

# Script per il rilascio dell'EJB 

echo Deploying message-filter EJB 

EJB_JAR=message-filter-ejb.jar 
EJB_NAME=MessageFilter 
ASW_JMS_JAR=asw-jms.jar 
ASW_UTIL_JAR=asw-util.jar 

# determina il path relativo e assoluto in cui si trova lo script 
# (rispetto alla posizione da cui è stata richiesta l'esecuzione dello script) 
REL_PATH_TO_SCRIPT=`dirname $0`
# ABS_PATH_TO_SCRIPT=`( cd ${REL_PATH_TO_SCRIPT} && pwd )`

# base dir 
BD=${REL_PATH_TO_SCRIPT}

LIB_DIR=${BD}/libs

sudo asadmin add-library --type app ${LIB_DIR}/${ASW_UTIL_JAR} ${LIB_DIR}/${ASW_JMS_JAR}
sudo asadmin deploy --name ${EJB_NAME} --libraries ${ASW_UTIL_JAR},${ASW_JMS_JAR} ${LIB_DIR}/${EJB_JAR}



