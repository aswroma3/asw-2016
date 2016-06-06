#!/bin/bash

# Script per l'avvio dell'application client per un ws  

# i valori vengono sostituiti dallo script gradle 
# per es., APPNAME=calculator-ws-client

APPNAME=@@@APPNAME@@@

echo Running application client ${APPNAME} 

# determina il path relativo in cui si trova lo script 
# (rispetto alla posizione da cui è stata richiesta l'esecuzione dello script) 
# PATH_TO_SCRIPT="`dirname \"$0\"`"
PATH_TO_SCRIPT=`dirname $0`

# $@ vuol dire che i parametri dello script sono passati all'appclient 
appclient -xml ${PATH_TO_SCRIPT}/glassfish-acc.xml -client ${PATH_TO_SCRIPT}/libs/${APPNAME}.jar $@
