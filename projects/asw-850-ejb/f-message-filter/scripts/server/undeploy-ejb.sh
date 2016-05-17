#!/bin/bash

# Script per la rimozione dell'EJB 

echo Undeploying message-filter EJB 

EJB_JAR=message-filter-ejb.jar 
EJB_NAME=MessageFilter 
ASW_JMS_JAR=asw-jms.jar 
ASW_UTIL_JAR=asw-util.jar 

sudo asadmin undeploy ${EJB_NAME} 
sudo asadmin remove-library --type app ${ASW_JMS_JAR}
sudo asadmin remove-library --type app ${ASW_UTIL_JAR}



