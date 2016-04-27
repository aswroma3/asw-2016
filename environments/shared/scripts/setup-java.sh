#!/bin/bash

source "/home/vagrant/shared/scripts/common.sh"

# set up Java constants 
JAVA_VERSION=8
JAVA_MINOR_VERSION=91
JAVA_BUILD=14

JAVA_FILE_NAME=jdk-${JAVA_VERSION}u${JAVA_MINOR_VERSION}-linux-x64
# e.g., jdk-8u91-linux-x64
JAVA_ARCHIVE=${JAVA_FILE_NAME}.tar.gz
GET_JAVA_URL=http://download.oracle.com/otn-pub/java/jdk/${JAVA_VERSION}u${JAVA_MINOR_VERSION}-b${JAVA_BUILD}
# e.g. http://download.oracle.com/otn-pub/java/jdk/8u91-b14
JAVA_JDK_PATH=/usr/local/jdk1.${JAVA_VERSION}.0_${JAVA_MINOR_VERSION} 
# e.g., /usr/local/jdk1.8.0_73
JAVA_JRE_PATH=/usr/lib/jvm/jre 

function installLocalJava {
	echo "====================="
	echo "installing oracle jdk"
	echo "====================="
	FILE=${VAGRANT_DOWNLOADS}/$JAVA_ARCHIVE
	tar -xzf $FILE -C /usr/local
}

function installRemoteJava {
	echo "======================"
	echo "downloading oracle jdk"
	echo "======================"
	wget -nv -P ${VAGRANT_DOWNLOADS} --header "Cookie: oraclelicense=accept-securebackup-cookie;" "${GET_JAVA_URL}/${JAVA_ARCHIVE}" 
	installLocalJava 
}

function setupJava {
	echo "setting up java"
	if downloadExists $JAVA_ARCHIVE; then
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
	if downloadExists $JAVA_ARCHIVE; then
		installLocalJava
	else
		installRemoteJava
	fi
}

echo "setup java"
installJava
setupJava
setupEnvVars
