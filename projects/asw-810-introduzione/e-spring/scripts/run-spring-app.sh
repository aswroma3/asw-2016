#!/bin/bash

# Script per l'avvio dell'app che utilizza il servizio Service  

echo Running my app 

# determina il path relativo in cui si trova lo script 
# (rispetto alla posizione da cui è stata richiesta l'esecuzione dello script) 
PATH_TO_SCRIPT=`dirname $0`

exec ${PATH_TO_SCRIPT}/app/bin/app 

