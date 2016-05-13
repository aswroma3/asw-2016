#!/bin/bash

# Script per il rilascio del servizio rest 

echo Deploying Async rest service 

REST_CONTEXT_ROOT=asw/asyncrest
# REST_APPLICATION=async 
REST_SERVICE=async 
REST_SERVICE_WAR=${REST_SERVICE}.war 
REST_SERVICE_NAME=AsyncRest

ASW_UTIL_JAR=asw-util.jar 

# URL del servizio  
# REST_SERVICE_URL=http://10.11.1.101:8080/${REST_CONTEXT_ROOT}/${REST_APPLICATION}/${REST_SERVICE}
REST_SERVICE_URL=http://10.11.1.101:8080/${REST_CONTEXT_ROOT}/${REST_SERVICE}

# determina il path relativo e assoluto in cui si trova lo script 
# (rispetto alla posizione da cui è stata richiesta l'esecuzione dello script) 
REL_PATH_TO_SCRIPT=`dirname $0`
# ABS_PATH_TO_SCRIPT=`( cd ${REL_PATH_TO_SCRIPT} && pwd )`

# base dir 
BD=${REL_PATH_TO_SCRIPT}

LIB_DIR=${BD}/libs

sudo asadmin add-library --type app ${LIB_DIR}/${ASW_UTIL_JAR} ${LIB_DIR}/${ASW_JMS_JAR}

sudo asadmin deploy --name ${REST_SERVICE_NAME} --contextroot ${REST_CONTEXT_ROOT} --libraries ${ASW_UTIL_JAR} ${LIB_DIR}/${REST_SERVICE_WAR}

echo "Il servizio rest ${REST_SERVICE} e' stato rilasciato su ${REST_SERVICE_URL}"


