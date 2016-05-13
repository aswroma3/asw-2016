#!/bin/bash

# Script per la rimozione del web service REST

echo Undeploying hello rest service 

REST_CONTEXT_ROOT=asw/hellorest
# REST_APPLICATION=hello 
REST_SERVICE=hello 
REST_SERVICE_WAR=${REST_SERVICE}.war 
REST_SERVICE_NAME=HelloRest

sudo asadmin undeploy ${REST_SERVICE_NAME} 



