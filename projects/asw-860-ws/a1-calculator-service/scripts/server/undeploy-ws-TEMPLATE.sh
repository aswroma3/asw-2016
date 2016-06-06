#!/bin/bash

# Script per la rimozione di un web service 

# i valori vengono sostituiti dallo script gradle 
# per es., WS_ROOT=asw/ws WS_NAME=calculator-ws WS_SERVICE=CalculatorService

WS_ROOT=@@@WSROOT@@@
WS_NAME=@@@WSNAME@@@
WS_SERVICE=@@@WSSERVICE@@@

WS_WAR=${WS_NAME}.war 
WS_URL=http://10.11.1.101:8080/${WS_ROOT}/${WS_SERVICE}?WSDL

echo Undeploying web service ${WS_NAME} from ${WS_ROOT}/${WS_SERVICE}

sudo asadmin undeploy ${WS_SERVICE} 



