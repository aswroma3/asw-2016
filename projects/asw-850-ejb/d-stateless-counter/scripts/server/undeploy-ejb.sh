#!/bin/bash

# Script per la rimozione dell'EJB 

echo Undeploying stateless counter EJB 

EJB_INTERFACE_JAR=stateless-counter-ejb-interface.jar 
EJB_IMPL_JAR=stateless-counter-ejb-impl.jar 
EJB_NAME=StatelessCounter 

sudo asadmin undeploy ${EJB_NAME} 
sudo asadmin remove-library --type app ${EJB_INTERFACE_JAR}



