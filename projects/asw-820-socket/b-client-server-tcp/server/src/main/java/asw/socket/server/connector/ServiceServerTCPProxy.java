package asw.socket.server.connector;

import asw.socket.service.Service;

import java.net.*;
import java.io.*;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

/* Remote proxy lato server per il servizio Service. */
public class ServiceServerTCPProxy {
    private Service service;             // il vero servizio
    private int port;                           // porta per il servizio

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.socket.server.connector");

	public ServiceServerTCPProxy(Service service, int port) {
        this.service = service;
        this.port = port;
    }

    public void run() {
        try {
            /* crea il server socket su cui ascoltare/ricevere richieste */
        	ServerSocket listenSocket = new ServerSocket(port);
            /* per il server, disabilita il timeout */
        	listenSocket.setSoTimeout(0);
            while (true) {
                /* aspetta/accetta una richiesta - crea il relativa socket */
				Socket clientSocket = listenSocket.accept();    // bloccante
				/* la richiesta sara' gestita in un nuovo thread, separato */
				ServantThread thread = new ServantThread(clientSocket, service);
            }
		} catch (IOException e) {
			logger.info("Server Proxy: IO Exception: " + e.getMessage());
		}
    }

}

