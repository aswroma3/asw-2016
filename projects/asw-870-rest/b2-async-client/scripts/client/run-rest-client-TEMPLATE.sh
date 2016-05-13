#!/bin/bash

# Script per l'avvio del client per un servizio rest 

# i valori vengono sostituiti dallo script gradle 

APPNAME=@@@APPNAME@@@
MAINCLASS=@@@MAINCLASS@@@

echo Running client ${APPNAME} 

# determina il path relativo in cui si trova lo script 
# (rispetto alla posizione da cui e' stata richiesta l'esecuzione dello script) 
PATH_TO_SCRIPT=`dirname $0`
LIB_DIR=${PATH_TO_SCRIPT}/libs

java -cp "${LIB_DIR}/*" ${MAINCLASS} $@

