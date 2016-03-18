package asw.intro.server;

import asw.intro.service.Service;

import asw.intro.server.connector.ServiceServerProxy;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

/* Server per il servizio:
 * Questa applicazione crea il servant e avvia il server. */
public class Server {

	/* logger */
	private static Logger logger = AswLogger.getInstance().getLogger("asw.intro.server");

	/* porta del server */
	private static int SERVER_PORT = 6789;

	/* Avvio del server. */
	public static void main(String[] args) {
		logger.info("Server...");
        Service service = new ServiceImpl();
        int port = SERVER_PORT;
		logger.info("Server: starting...");
        ServiceServerProxy server = new ServiceServerProxy(service, port);
        server.run();
		logger.info("Server: stopped");
	}

}


