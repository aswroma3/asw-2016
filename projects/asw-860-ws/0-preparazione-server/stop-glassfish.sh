#!/bin/bash

# Script per l'arresto di GlassFish. 

echo Stopping GlassFish service 

# sudo /etc/init.d/GlassFish_domain1 stop
sudo asadmin stop-domain 
