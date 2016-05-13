#!/bin/bash

# Script per l'accesso al servizio rest 

REST_CONTEXT_ROOT=asw/hellorest
# REST_APPLICATION=hello 
REST_SERVICE=hello 
REST_SERVICE_WAR=${REST_SERVICE}.war 
REST_SERVICE_NAME=HelloRest

# URL del servizio  
# REST_SERVICE_URL=http://10.11.1.101:8080/${REST_CONTEXT_ROOT}/${REST_APPLICATION}/${REST_SERVICE}
REST_SERVICE_URL=http://10.11.1.101:8080/${REST_CONTEXT_ROOT}/${REST_SERVICE}

echo Accessing service rest ${REST_SERVICE_NAME} at ${REST_SERVICE_URL}

# Nota: curl puo' essere installato con il seguente comando 
# sudo apt-get install curl 

echo 
echo "GET ${REST_SERVICE_URL}/info/"
echo $(curl -s -X GET "${REST_SERVICE_URL}/info/") 

