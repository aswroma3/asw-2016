#!/bin/bash

source "/home/vagrant/shared/scripts/common.sh"

# set up Java EE constants 
JAVAEE_FILE_NAME=java_ee_sdk-7u2
JAVAEE_ARCHIVE=java_ee_sdk-7u2.zip
JAVAEE_DEST_PATH=/opt 
JAVAEE_PATH=/opt/glassfish4 

GET_JAVAEE_URL=http://download.oracle.com/otn-pub/java/java_ee_sdk/7u3 

GLASSFISH_ACC_FILE=glassfish-acc.xml 

function installLocalJavaEE {
	echo "=================="
	echo "installing java ee"
	echo "=================="
	FILE=${VAGRANT_DOWNLOADS}/$JAVAEE_ARCHIVE
	unzip -q $FILE -d ${JAVAEE_DEST_PATH}
}

function installRemoteJavaEE {
	echo "==================="
	echo "downloading java ee"
	echo "==================="
	wget -nv -P ${VAGRANT_DOWNLOADS} --header "Cookie: oraclelicense=accept-securebackup-cookie;" "${GET_JAVAEE_URL}/${JAVAEE_ARCHIVE}" 
	installLocalJavaEE 
}

function setupEnvVars {
	echo "creating javaee environment variables"
	echo export GLASSFISH_HOME=${JAVAEE_PATH} >> /etc/profile.d/javaee.sh
	echo export AS_HOME=${JAVAEE_PATH}/glassfish >> /etc/profile.d/javaee.sh
	echo export PATH=\${PATH}:\${AS_HOME}/bin:\${GLASSFISH_HOME}/bin >> /etc/profile.d/javaee.sh
}

function createModifiedAccFile {
	INFILE=$1 
	OUTFILE=$2 
	# Legge il file $INFILE e lo copia in $OUTFILE, ma sostituisce 
	# - <target-server name="sc11152545.us.oracle.com" address="localhost" port="3700"/>
	# con 
	# - <target-server name="server" address="10.11.1.101" port="3700"/>
	sed -r 's#(<target-server name)="sc11152545.us.oracle.com" address="localhost" (port="3700"/>)#\1="server" address="10.11.1.101" port="3700"/>#' $INFILE > $OUTFILE
#	sed -r 's#<target-server name="sc11152545.us.oracle.com" address="localhost" port="3700"/>#<target-server name="server" address="10.11.1.101" port="3700"/>#' $INFILE > $OUTFILE
}

function setupGlassfishAccFile {
	echo "modifying ${GLASSFISH_ACC_FILE} file"
	AS_HOME=${JAVAEE_PATH}/glassfish
	FILE=${AS_HOME}/domains/domain1/config/${GLASSFISH_ACC_FILE}
	createModifiedAccFile ${FILE} ${FILE}.new 
	mv ${FILE} ${FILE}.bak
	mv ${FILE}.new ${FILE}
}

function setupJavaEE {
	echo "configuring glassfish"
	AS_HOME=${JAVAEE_PATH}/glassfish
	
	# corregge un problema di Glassfish 4.1.1 relativo a eclipse link 
	# vedi: https://java.net/jira/browse/GLASSFISH-21440 
	# e in particolare la soluzione suggerita da xwibao 
	echo "fixing a Glassfish 4.1.1/Eclipselink bug"
	mv ${AS_HOME}/modules/org.eclipse.persistence.moxy.jar ${AS_HOME}/modules/org.eclipse.persistence.moxy.jar.bak
	cp ${VAGRANT_RESOURCES}/org.eclipse.persistence.moxy-FIXED.jar ${AS_HOME}/modules/org.eclipse.persistence.moxy.jar

	# aggiorna il file di configurazione dell'appclient
	setupGlassfishAccFile
	
	echo "setting up glassfish service"
	# definisce java ee come servizio 
	${AS_HOME}/bin/asadmin create-service 
	
	# il servizio può essere avviato come segue ... ma lo facciamo in modo diverso 
	# echo "starting glassfish service"
	# /etc/init.d/GlassFish_domain1 start

	# avvio del dominio 
	echo "starting glassfish domain"
	${AS_HOME}/bin/asadmin start-domain

	# volendo, la configurazione di glassfish si può esaminare così 
	# ${AS_HOME}/bin/asadmin get "*"  

	# volendo, il server HTTP può essere esposto sulla porta 80 anziché 8080
	# ${AS_HOME}/bin/asadmin set configs.config.server-config.network-config.network-listeners.network-listener.http-listener-1.port=80 

	# ulteriori configurazioni per JMS (non tutte sono ideali, perché il dominio sia chiama "localhost..." ma non è su localhost 
	# in effetti sembrano necessarie solo le ultime due (e per questo le altre sono state commentate) 
	# ${AS_HOME}/bin/asadmin set nodes.node.localhost-domain1.name=server-domain1 
	# ${AS_HOME}/bin/asadmin set nodes.node.localhost-domain1.node-host=10.11.1.101 
	${AS_HOME}/bin/asadmin set configs.config.default-config.jms-service.jms-host.default_JMS_host.host=10.11.1.101 
	${AS_HOME}/bin/asadmin set configs.config.server-config.jms-service.jms-host.default_JMS_host.host=10.11.1.101 

	echo "configuring glassfish appclient"
	${AS_HOME}/bin/package-appclient 
	cp ${AS_HOME}/lib/appclient.jar ${VAGRANT_DOWNLOADS}/appclient-10.11.1.101.jar 
}

function installJavaEE {
	if downloadExists $JAVAEE_ARCHIVE; then
		installLocalJavaEE
	else
		installRemoteJavaEE
	fi
}

function installPrerequisites {
	echo "installing unzip"
	apt-get install unzip 
}

echo "setup javaee"
installPrerequisites
installJavaEE
setupEnvVars
setupJavaEE
