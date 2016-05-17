#!/bin/bash

# Script per la rimozione dell'EJB 

echo Undeploying calculator EJB 

EJB_INTERFACE_JAR=calculator-ejb-interface.jar 
EJB_IMPL_JAR=calculator-ejb-impl.jar 
EJB_NAME=Calculator 

sudo asadmin undeploy ${EJB_NAME} 
sudo asadmin remove-library --type app ${EJB_INTERFACE_JAR}



