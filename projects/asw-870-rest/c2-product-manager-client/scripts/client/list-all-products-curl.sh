#!/bin/bash

# Script per l'accesso al servizio rest 

REST_CONTEXT_ROOT=asw/productmanager
# REST_APPLICATION=productmanager 
# REST_SERVICE=products 
REST_SERVICE_WAR=product-manager-rest.war 
REST_SERVICE_NAME=ProductManagerRest
# REST_SERVICE_URL=http://10.11.1.101:8080/${REST_CONTEXT_ROOT}/${REST_SERVICE}
REST_SERVICE_URL=http://10.11.1.101:8080/${REST_CONTEXT_ROOT}

echo Accessing service rest ${REST_SERVICE_NAME} at ${REST_SERVICE_URL}

# accede a tutti i prodotti (JSON) 
echo 
echo "GET ${REST_SERVICE_URL}/products"
echo $(curl -s -H "Accept:application/json" --get "${REST_SERVICE_URL}/products") 
