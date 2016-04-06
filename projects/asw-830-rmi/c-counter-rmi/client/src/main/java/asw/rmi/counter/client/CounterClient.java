package asw.rmi.counter.client;

import asw.rmi.counter.service.Counter;

import java.rmi.RemoteException;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

import asw.util.sleep.Sleeper;

public class CounterClient {

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.rmi.counter.client");

	/* il servizio */
	private Counter counter;

	public CounterClient(Counter counter) {
		this.counter = counter;
	}

	public void run() {
        /* usa il servizio remoto */
        try {
        	logger.info("CounterClient: Ora uso il servizio Counter");
        	for (int i=0; i<25; i++) {
        		int result = counter.getCounter();
            	logger.info("CounterClient: counter.getCounter() ==> " + result);
        		/* introduci un ritardo causale (comunque meno di 1 secondo) */
            	Sleeper.randomSleep(400,800);
        	}
        } catch (RemoteException e) {
            logger.info("CounterClient: RemoteException: " + e.getMessage());
        }
    }

}
