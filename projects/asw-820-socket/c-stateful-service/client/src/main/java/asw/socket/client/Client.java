package asw.socket.client;

import asw.socket.service.*;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

import asw.util.sleep.Sleeper;

/* client del servizio */
public class Client {

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.socket.client");

	/* il servizio */
	private RemoteCounterService service;

	/* crea un nuovo oggetto Client */
	public Client() {
	}

	/* setta il servizio per il Client */
	public void setService(RemoteCounterService service) {
		this.service = service;
	}

	/* richiede il servizio */
	public void run() {
		try {
			logger.info("Client: Ora uso il servizio CounterService");

			logger.info("Client: connect()");
			service.connect();

			for (int i=0; i<10; i++) {
				logger.info("Client: calling service.incrementCounter()");
				service.incrementCounter();
				logger.info("Client: calling service.getGlobalCounter()");
				int globalCount = service.getGlobalCounter();
				logger.info("Client: calling service.getSessionCounter()");
				int sessionCount = service.getSessionCounter();
				logger.info("Client: session count = " + sessionCount + ", global count = " + globalCount);
				/* introduce un ritardo */
				Sleeper.randomSleep(500, 1000);
			}

			logger.info("Client: disconnect()");
			service.disconnect();

			logger.info("Client: Ho finito di usare il servizio CounterService");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}