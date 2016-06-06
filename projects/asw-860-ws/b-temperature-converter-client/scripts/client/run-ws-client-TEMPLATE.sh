#!/bin/bash

# Script per l'avvio dell'application client per un ws  

# i valori vengono sostituiti dallo script gradle 
# per es., APPNAME=calculator-ws-client

APPNAME=@@@APPNAME@@@

echo Running client ${APPNAME} 

# determina il path relativo in cui si trova lo script 
# (rispetto alla posizione da cui è stata richiesta l'esecuzione dello script) 
# PATH_TO_SCRIPT="`dirname \"$0\"`"
PATH_TO_SCRIPT=`dirname $0`

LIB_DIR=${PATH_TO_SCRIPT}/libs
CLIENT_JAR=${LIB_DIR}/${APPNAME}.jar

java -cp "${LIB_DIR}/*" -jar ${CLIENT_JAR} $@
