#!/bin/bash

# Script per la rimozione del servizio rest product manager 
# rimuove il componente per il servizio rest e cancella anche la base di dati dei prodotti 

echo "Undeploying Product Manager REST Service"

# general 
ADMINPORT=4848
HOST=10.11.1.101
ASADMIN="asadmin --echo=true --host ${HOST} --port ${ADMINPORT}"

# jdbc 
JDBC_CONNECTION_POOL=product_db_derby_pool
JDBC_DATA_SOURCE=jdbc/ProductDB

# javadb 
JAVADB_HOME=${GLASSFISH_HOME}/javadb
EXEC_SQL=${JAVADB_HOME}/bin/ij 

# libraries 
# ASW_UTIL_JAR=asw-util.jar 

# rest service(s) 
REST_CONTEXT_ROOT=asw/productmanager
# REST_APPLICATION=productmanager 
REST_SERVICE=products 
REST_SERVICE_WAR=product-manager-rest.war 
REST_SERVICE_NAME=ProductManagerRest
REST_SERVICE_URL=http://10.11.1.101:8080/${REST_CONTEXT_ROOT}/${REST_SERVICE}

# determina il path relativo e assoluto in cui si trova lo script 
# (rispetto alla posizione da cui è stata richiesta l'esecuzione dello script) 
REL_PATH_TO_SCRIPT=`dirname $0`
# ABS_PATH_TO_SCRIPT=`( cd ${REL_PATH_TO_SCRIPT} && pwd )`

# base dir 
BD=${REL_PATH_TO_SCRIPT}

LIB_DIR=${BD}/libs


###
echo "Undeploying REST services"

sudo ${ASADMIN} undeploy ${REST_SERVICE_NAME} 

echo "REST services for the service undeployed"

###
# echo "Undeploying libraries"
# sudo ${ASADMIN} remove-library --type app ${ASW_UTIL_JAR}
# echo "Libraries for the service undeployed"

###
echo "Dropping ProductDB"

${EXEC_SQL} ${BD}/sql/drop.sql 

echo "ProductDB dropped"


###
echo "Deleting JDBC resources for the service"

sudo ${ASADMIN} delete-jdbc-resource ${JDBC_DATA_SOURCE}
sudo ${ASADMIN} delete-jdbc-connection-pool ${JDBC_CONNECTION_POOL}

echo "JDBC resources for the service deleted"



