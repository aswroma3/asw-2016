#!/bin/bash

# Script per la rimozione dell'EJB 

echo Undeploying hello EJB 

EJB_INTERFACE_JAR=hello-ejb-interface.jar 
EJB_IMPL_JAR=hello-ejb-impl.jar 
EJB_NAME=Hello 

sudo asadmin undeploy ${EJB_NAME} 
sudo asadmin remove-library --type app ${EJB_INTERFACE_JAR}



