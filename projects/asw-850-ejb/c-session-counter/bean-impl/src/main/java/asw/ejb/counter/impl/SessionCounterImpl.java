package asw.ejb.counter.impl;

import asw.ejb.counter.SessionCounter;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Remove;
import javax.ejb.Stateful;

/**
 * Session Bean implementation class SessionCounterImpl
 */
@Stateful(mappedName = "ejb/asw/SessionCounter")
public class SessionCounterImpl implements SessionCounter {

	/* logger */
	private Logger logger;

	private int counter;

	/* inizializza un'istanza del bean, assegnando un valore iniziale al contatore */
	@PostConstruct
	public void initialize() {
		/* fa il logging sul log del server EJB
		 * in particolare, nel file C:\glassfish3\glassfish\domains\domain1\logs\server.log */
		logger = Logger.getLogger("asw.ejb.counter.impl");
		logger.info("SessionCounter: initializing SessionCounter instance");
		this.counter = 0;
	}

	@Override
	/* incrementa il contatore e ne restituisce il valore */
	public int getCounter() {
		counter++;
		logger.info("SessionCounter: getCounter() ==> " + counter);
		return counter;
	}

	@Override
	@Remove
	/* termina la sessione */
	public void close() {
		/* effettivamente, il contenitore termina la sessione */
	}

}
