#!/bin/bash

source "/home/vagrant/shared/scripts/common.sh"

# set up Docker constants 
GET_DOCKER_URL=https://get.docker.com/
DOCKER_SETUP_FILE=docker-setup.sh 

function installDocker {
	echo "================="
	echo "installing docker"
	echo "================="
	FILE=/tmp/${DOCKER_SETUP_FILE}
	wget ${GET_DOCKER_URL} -O ${FILE} 
	sh ${FILE} 
}

echo "setup docker"
installDocker
