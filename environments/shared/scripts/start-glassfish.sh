#!/bin/bash

# Script per l'avvio di GlassFish. 
# (L'avvio viene richiesto solo se il dominio non è già avviato). 

echo Starting GlassFish service 

# prova ad avviare il dominio 
asadmin start-domain

if [ $? -eq 0 ]
then 
    echo "GlassFish started"
else 
    echo "GlassFish is already running"
fi 

exit 0 
