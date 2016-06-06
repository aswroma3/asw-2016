#!/bin/bash

# Script per il rilascio di un web service 

# i valori vengono sostituiti dallo script gradle 
# per es., WS_ROOT=asw/ws WS_NAME=calculator-ws WS_SERVICE=CalculatorService

WS_ROOT=@@@WSROOT@@@
WS_NAME=@@@WSNAME@@@
WS_SERVICE=@@@WSSERVICE@@@

WS_WAR=${WS_NAME}.war 
WS_URL=http://10.11.1.101:8080/${WS_ROOT}/${WS_SERVICE}?WSDL

echo Deploying web service ${WS_ARCHIVE} on ${WS_ROOT}/${WS_SERVICE}

# determina il path relativo e assoluto in cui si trova lo script 
# (rispetto alla posizione da cui è stata richiesta l'esecuzione dello script) 
REL_PATH_TO_SCRIPT=`dirname $0`

# base dir 
BD=${REL_PATH_TO_SCRIPT}

LIB_DIR=${BD}/libs

sudo asadmin deploy --contextroot ${WS_ROOT} --name ${WS_SERVICE} ${LIB_DIR}/${WS_WAR}

echo Il WSDL del web service ${WS_SERVICE} si trova su ${WS_URL}


