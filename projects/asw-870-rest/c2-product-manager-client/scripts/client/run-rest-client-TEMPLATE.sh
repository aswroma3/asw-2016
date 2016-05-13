#!/bin/bash

# Script per l'avvio del client per un servizio rest 

# i valori vengono sostituiti dallo script gradle 
# per es., APPNAME=hello-client

APPNAME=@@@APPNAME@@@
MAINCLASS=@@@MAINCLASS@@@

echo Running client ${APPNAME} 

# determina il path relativo in cui si trova lo script 
# (rispetto alla posizione da cui è stata richiesta l'esecuzione dello script) 
PATH_TO_SCRIPT=`dirname $0`
LIB_DIR=${PATH_TO_SCRIPT}/libs

java -cp "${LIB_DIR}/*" ${MAINCLASS} $@

