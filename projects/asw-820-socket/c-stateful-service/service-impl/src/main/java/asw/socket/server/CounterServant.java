package asw.socket.server;

import asw.socket.service.CounterService;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

/**
 * Implementazione del servizio CounterService.
 * Verra' allocata un'istanza di servente per ciascun client/connessione.
 */
public class CounterServant implements CounterService {

	private CounterApplicationState appState;
	private CounterSessionState sessionState;

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.socket.server");

	public CounterServant() {
		this.appState = CounterApplicationState.getInstance();
		this.sessionState = new CounterSessionState();
	}

    public int getSessionCounter() {
    	int result = sessionState.getSessionCounter();
    	logger.info("CounterServant: executing getSessionCounter() ==> " + result);
    	return result;
    }

	/*
	 * nota: il fatto che incrementCounter sia sincronizzata
	 * NON garantisce che i valori restituiti da questa operazione
	 * siano tutti diversi tra loro
	 */
    public int getGlobalCounter() {
    	int result = appState.getGlobalCounter();
    	logger.info("CounterServant: executing getGlobalCounter() ==> " + result);
    	return result;
    }

    public synchronized void incrementCounter() {
    	logger.info("CounterServant: executing incrementCounter()");
    	appState.incrementGlobalCounter();
    	sessionState.incrementSessionCounter();
    }
}

