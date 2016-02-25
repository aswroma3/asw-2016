#!/bin/bash

source "/home/vagrant/shared/scripts/common.sh"

# set up Gradle constants 
GRADLE_VERSION=2.11

GRADLE_ARCHIVE=gradle-${GRADLE_VERSION}-bin.zip
# e.g., gradle-2.11-bin.zip
GET_GRADLE_URL=https://services.gradle.org/distributions
GRADLE_PATH=/usr/local/gradle-${GRADLE_VERSION} 
# e.g. /usr/local/gradle-2.11

function installLocalGradle {
	echo "================="
	echo "installing gradle"
	echo "================="
	FILE=${VAGRANT_RESOURCES}/$GRADLE_ARCHIVE
	unzip -q $FILE -d /usr/local
}

function installRemoteGradle {
	echo "=================="
	echo "downloading gradle"
	echo "=================="
	wget -q -P /tmp ${GET_GRADLE_URL}/${GRADLE_ARCHIVE} 
	echo "================="
	echo "installing gradle"
	echo "================="
	FILE=/tmp/${GRADLE_ARCHIVE}
	if fileExists $FILE; then 
		unzip -q $FILE -d /usr/local
		rm $FILE 
	else 
		echo "impossibile installare gradle" 
	fi
}

function setupGradle {
	echo "setting up gradle"
	if fileExists $GRADLE_PATH; then
		ln -s $GRADLE_PATH /usr/local/gradle
	fi
}

function setupEnvVars {
	echo "creating gradle environment variables"
	echo export GRADLE_HOME=/usr/local/gradle >> /etc/profile.d/gradle.sh
	echo export PATH=\${PATH}:\${GRADLE_HOME}/bin >> /etc/profile.d/gradle.sh
}

function installGradle {
	if resourceExists $GRADLE_ARCHIVE; then
		installLocalGradle
	else
		installRemoteGradle
	fi
}

function installPrerequisites {
	echo "installing unzip"
	apt-get install unzip 
}

echo "setup gradle"
installPrerequisites
installGradle
setupGradle
setupEnvVars
