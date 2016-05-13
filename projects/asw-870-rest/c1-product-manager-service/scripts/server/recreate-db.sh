#!/bin/bash

# Script per ri-creare la base di dati dei prodotti 

# general 
ADMINPORT=4848
HOST=10.11.1.101
ASADMIN="asadmin --echo=true --host ${HOST} --port ${ADMINPORT}"

# javadb 
JAVADB_HOME=${GLASSFISH_HOME}/javadb
EXEC_SQL=${JAVADB_HOME}/bin/ij 

# determina il path relativo e assoluto in cui si trova lo script 
# (rispetto alla posizione da cui è stata richiesta l'esecuzione dello script) 
REL_PATH_TO_SCRIPT=`dirname $0`
# ABS_PATH_TO_SCRIPT=`( cd ${REL_PATH_TO_SCRIPT} && pwd )`

# base dir 
BD=${REL_PATH_TO_SCRIPT}

LIB_DIR=${BD}/libs

###
echo "Re-creating ProductDB"

${EXEC_SQL} ${BD}/sql/drop.sql 
${EXEC_SQL} ${BD}/sql/create-and-load.sql 

echo "ProductDB re-created"

