#!/bin/bash

source "/home/vagrant/shared/scripts/common.sh"

# set up Java EE constants 
APPCLIENT_ARCHIVE=appclient-10.11.1.101.jar
EXTRACT_PATH=/opt
APPCLIENT_PATH=/opt/appclient

function installLocalJavaEEAppclient {
	echo "==========================="
	echo "installing javaee appclient"
	echo "==========================="
	FILE=${VAGRANT_DOWNLOADS}/$APPCLIENT_ARCHIVE
	cd ${EXTRACT_PATH}
	jar -xf $FILE 
	chmod a+x ${APPCLIENT_PATH}/glassfish/bin/appclient 
}

function setupEnvVars {
	echo "creating javaee appclient environment variables"
	echo export APPCLIENT_HOME=${APPCLIENT_PATH} >> /etc/profile.d/javaee-appclient.sh
	echo export PATH=\${PATH}:\${APPCLIENT_HOME}/glassfish/bin >> /etc/profile.d/javaee-appclient.sh
}

function setupJavaEEAppclient {
	echo "setting up java ee appclient"
	# niente da fare 
}

function installJavaEEAppclient {
	if downloadExists $APPCLIENT_ARCHIVE; then
		installLocalJavaEEAppclient
	else
		echo "missing resource: " ${VAGRANT_DOWNLOADS}/$APPCLIENT_ARCHIVE
	fi
}

echo "setup javaee appclient"
installJavaEEAppclient
setupEnvVars
setupJavaEEAppclient
