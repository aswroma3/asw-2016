package asw.rmi.counter.impl;

import asw.rmi.counter.service.Counter;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

/**
 * Implementazione del servizio counter.
 */
public class CounterImpl implements Counter {

	/* logger */
	private static Logger logger = AswLogger.getInstance().getLogger("asw.rmi.counter.impl");

	/* il contatore */
	private int counter;

	/* Crea un nuovo contatore, inizialmente nullo. */
	public CounterImpl() {
		super();
		counter = 0;
		logger.info("CounterImpl: CounterImpl(): counter --> " + counter);
	}

	/* Incrementa il valore del contatore e lo restituisce. */
	public synchronized int getCounter() {
		counter++;
		logger.info("CounterImpl: counter.getCounter() --> " + counter);
		return counter;
	}

}
