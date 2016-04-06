package asw.rmi.sessioncounter.client;

import asw.rmi.sessioncounter.service.SessionCounter;
import asw.rmi.sessioncounter.client.connector.ServiceFactory;

/* Applicazione client: ottiene e avvia il client. */
public class Main {

	/* Crea e avvia un oggetto SessionCounterClient. */
	public static void main(String[] args) {
		SessionCounter sessionCounter = ServiceFactory.getInstance().getSessionCounter(args);
		SessionCounterClient client = new SessionCounterClient(sessionCounter);
		client.run();
	}

}
