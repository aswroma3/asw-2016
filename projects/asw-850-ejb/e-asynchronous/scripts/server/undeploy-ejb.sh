#!/bin/bash

# Script per la rimozione dell'EJB 

echo Undeploying asynchronous EJB 

EJB_INTERFACE_JAR=asynchronous-ejb-interface.jar 
EJB_IMPL_JAR=asynchronous-ejb-impl.jar 
EJB_NAME=AsynchronousBean 
ASW_UTIL_JAR=asw-util.jar 

sudo asadmin undeploy ${EJB_NAME} 
sudo asadmin remove-library --type app ${EJB_INTERFACE_JAR}
sudo asadmin remove-library --type app ${ASW_UTIL_JAR}



