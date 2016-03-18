#!/bin/bash

source "/home/vagrant/shared/scripts/common.sh"

# set up Java constants 
JAVA_VERSION=8
JAVA_MINOR_VERSION=73

JAVA_FILE_NAME=jdk-${JAVA_VERSION}u${JAVA_MINOR_VERSION}-linux-x64
# e.g., jdk-8u73-linux-x64
JAVA_ARCHIVE=${JAVA_FILE_NAME}.tar.gz
JAVA_JDK_PATH=/usr/local/jdk1.${JAVA_VERSION}.0_${JAVA_MINOR_VERSION} 
# e.g., /usr/local/jdk1.8.0_73
JAVA_JRE_PATH=/usr/lib/jvm/jre 

function installLocalJava {
	echo "====================="
	echo "installing oracle jdk"
	echo "====================="
	FILE=${VAGRANT_RESOURCES}/$JAVA_ARCHIVE
	tar -xzf $FILE -C /usr/local
}

# function installRemoteJava {
# 	echo "install open jdk"
# 	yum install -y $JAVA_FILE_NAME
# }

function setupJava {
	echo "setting up java"
	if resourceExists $JAVA_ARCHIVE; then
		ln -s $JAVA_JDK_PATH /usr/local/java
	else
		ln -s $JAVA_JRE_PATH /usr/local/java
	fi
}

function setupEnvVars {
	echo "creating java environment variables"
	echo export JAVA_HOME=/usr/local/java >> /etc/profile.d/java.sh
	echo export PATH=\${JAVA_HOME}/bin:\${PATH} >> /etc/profile.d/java.sh
}

function installJava {
	if resourceExists $JAVA_ARCHIVE; then
		installLocalJava
	else
		echo "missing resource: " ${VAGRANT_RESOURCES}/$JAVA_ARCHIVE
	fi
}

echo "setup java"
installJava
setupJava
setupEnvVars
