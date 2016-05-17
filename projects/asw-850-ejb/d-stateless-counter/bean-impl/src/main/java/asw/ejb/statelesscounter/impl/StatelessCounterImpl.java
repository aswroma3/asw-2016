package asw.ejb.statelesscounter.impl;

import asw.ejb.statelesscounter.StatelessCounter;

import javax.ejb.Stateless;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;

/**
 * Un altro counter, ma implementato come session bean STATELESS.
 * Per ragionare, in realta', sul fatto che questo bean NON va implementato come stateless.
 * Va bene (cioe', va altrettanto male, usare un session bean SINGLETON.)
 */
@Stateless(mappedName = "ejb/asw/StatelessCounter")
public class StatelessCounterImpl implements StatelessCounter {

	/* logger */
	private Logger logger;

	private int counter;

	@PostConstruct
	public void initialize() {
		logger = Logger.getLogger("asw.ejb.statelesscounter");
		logger.info("StatelessCounter: initializing StatelessCounter instance");
		counter = 0;
	}

	@Override
	/* incrementa il contatore e ne restituisce il valore */
	public synchronized int getCounter() {
		counter++;
		logger.info("StatelessCounter: getCounter() ==> " + counter);
		return counter;
	}


}
