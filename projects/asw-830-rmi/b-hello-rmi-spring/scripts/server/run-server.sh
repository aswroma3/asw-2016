#!/bin/bash

# Script per l'avvio del server (exporter) RMI/Spring per Hello. 

echo Running hello server 

# determina il path relativo in cui si trova lo script 
# (rispetto alla posizione da cui è stata richiesta l'esecuzione dello script) 
PATH_TO_SCRIPT=`dirname $0`

exec ${PATH_TO_SCRIPT}/server/bin/server 


