#!/bin/bash

# Script per la rimozione del web service REST

echo Undeploying Async rest service 

REST_CONTEXT_ROOT=asw/asyncrest
# REST_APPLICATION=async 
REST_SERVICE=async 
REST_SERVICE_WAR=${REST_SERVICE}.war 
REST_SERVICE_NAME=AsyncRest

ASW_UTIL_JAR=asw-util.jar 

sudo asadmin undeploy ${REST_SERVICE_NAME} 

sudo asadmin remove-library --type app ${ASW_UTIL_JAR}




