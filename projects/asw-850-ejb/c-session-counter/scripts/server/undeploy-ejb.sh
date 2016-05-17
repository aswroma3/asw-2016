#!/bin/bash

# Script per la rimozione dell'EJB 

echo Undeploying session counter EJB 

EJB_INTERFACE_JAR=session-counter-ejb-interface.jar 
EJB_IMPL_JAR=session-counter-ejb-impl.jar 
EJB_NAME=SessionCounter 

sudo asadmin undeploy ${EJB_NAME} 
sudo asadmin remove-library --type app ${EJB_INTERFACE_JAR}



