package asw.socket.server;

import asw.socket.server.connector.*;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

/* server per il servizio */
public class Server {

	private static int SERVER_PORT = 7869;

	/* logger */
	private static Logger logger = AswLogger.getInstance().getLogger("asw.socket.server");

    public static void main(String[] args) {
        logger.info("Server: starting...");
        CounterServiceServerTCPProxy server = new CounterServiceServerTCPProxy(SERVER_PORT);
        server.run();
        logger.info("Server: stopped");
    }
}


