package asw.rmi.sessioncounter.client;

import asw.rmi.sessioncounter.service.*;

import java.rmi.RemoteException;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

import asw.util.sleep.Sleeper;

public class SessionCounterClient {

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.rmi.sessioncounter.client");

	/* il servizio */
	SessionCounter sessionCounter;

	public SessionCounterClient(SessionCounter sessionCounter) {
		this.sessionCounter = sessionCounter;
	}

	public void run() {
        /* usa il servizio remoto */
        try {
            int counterId = sessionCounter.getId();
        	logger.info("SessionCounterClient: Ora uso il Session Counter: SessionCounter[" + counterId + "]");
        	for (int i=0; i<25; i++) {
        		int result = sessionCounter.getCounter();
            	logger.info("SessionCounterClient: SessionCounter[" + counterId + "].getCounter() ==> " + result);
        		/* introduci un ritardo causale (comunque meno di 1 secondo) */
            	Sleeper.randomSleep(400,800);
        	}
        } catch (RemoteException e) {
            logger.info("SessionCounterClient: RemoteException: " + e.getMessage());
        }
    }

}
