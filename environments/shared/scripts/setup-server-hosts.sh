#!/bin/bash

HOSTS_FILE=/etc/hosts 
ENTRY_TO_ADD="10.11.1.101	server"

function createModifiedHostsFile
{
	INFILE=$1 
	OUTFILE=$2 
	# Legge il file $INFILE e lo copia in $OUTFILE, ma: 
	# - aggiunge un # all'inizio delle linee che iniziano con 127.0.1.1 
	sed s/^'127.0.1.1'/'# 127.0.1.1'/ $INFILE > $OUTFILE
}

# - aggiunge un # all'inizio delle linee che iniziano con 127.0.1.1 
# aggiunge "10.11.1.101 server" a /etc/hosts 
function setupServerHostsFile {
	echo "modifying /etc/hosts file"
	createModifiedHostsFile ${HOSTS_FILE} ${HOSTS_FILE}.new 
	mv ${HOSTS_FILE} ${HOSTS_FILE}.bak
	mv ${HOSTS_FILE}.new ${HOSTS_FILE}
	
	echo "adding ${ENTRY_TO_ADD}"
	echo "${ENTRY_TO_ADD}" >> ${HOSTS_FILE}
}

echo "setup server /etc/hosts file"
setupServerHostsFile