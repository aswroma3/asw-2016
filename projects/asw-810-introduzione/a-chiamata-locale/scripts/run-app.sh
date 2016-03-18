#!/bin/bash

# Script per l'avvio dell'app che utilizza il servizio Service  

echo Running my app 

# determina il path relativo in cui si trova lo script 
# (rispetto alla posizione da cui è stata richiesta l'esecuzione dello script) 
# PATH_TO_SCRIPT="`dirname \"$0\"`"
PATH_TO_SCRIPT=`dirname $0`
# echo ${PATH_TO_SCRIPT}

LIB_DIR=${PATH_TO_SCRIPT}/libs
APP_JAR=${LIB_DIR}/app.jar

java -cp "${LIB_DIR}/*" -jar ${APP_JAR} 

