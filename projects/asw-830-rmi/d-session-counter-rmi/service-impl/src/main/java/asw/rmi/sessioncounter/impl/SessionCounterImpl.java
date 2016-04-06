package asw.rmi.sessioncounter.impl;

import asw.rmi.sessioncounter.service.*;

import java.rmi.*;
import java.rmi.server.*;

import asw.util.logging.AswLogger;
import java.util.logging.Logger;

/**
 * Implementazione del (session) counter.
 */
public class SessionCounterImpl
		// extends UnicastRemoteObject
		implements SessionCounter {

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.rmi.sessioncounter.impl");

	/* id del contatore */
	private int id;

	/* massimo id assegnato finora */
	private static int MAX_ID = 0;

	/* il contatore di sessione */
	private int counter;

	/* Crea un nuovo contatore di sessione, inizialmente nullo. */
	public SessionCounterImpl() throws RemoteException {
		super();
		MAX_ID++;
		this.id = MAX_ID;
		this.counter = 0;
		logger.info("SessionCounterImpl: created SessionCounter[" + id + "]: counter --> " + counter);

		/* esporta l'oggetto come oggetto RMI */
		logger.info("SessionCounterImpl: exporting SessionCounter[" + id + "] as a remote object...");
		UnicastRemoteObject.exportObject(this, 0);
		/* in alternativa, la classe potrebbe dichiarare di implementare UnicastRemoteObject,
		 * l'esportazione non servirebbe */
	}

	/* Restituisce l'id del contatore. */
	public int getId() {
		return this.id;
	}

	/* Incrementa e restituisce il valore del contatore di sessione.
	 * Non e' necessario che sia synchronized, dato che il contatore non e' condiviso. */
	public int getCounter() {
		counter++;
		logger.info("SessionCounterImpl: SessionCounter[" + id + "].getCounter() --> " + counter);
		return counter;
	}

	/* Questo metodo viene invocato quando RMI (lato server) fa la garbage collection
	 * dell'oggetto remoto.
	 * Lo script e' configurato per fare garbage collection ogni 10 secondi. */
	@Override
	public void finalize() throws Throwable {
		super.finalize();
		logger.info("SessionCounterImpl: Garbage collecting: SessionCounter[" + id + "]");
	}

}
