package asw.rmi.sessioncounter.impl;

import asw.rmi.sessioncounter.service.*;

import java.rmi.*;

import asw.util.logging.AswLogger;
import java.util.logging.Logger;

/*
 * Implementazione della factory per il session counter.
 */
public class SessionCounterFactoryImpl implements SessionCounterFactory {

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.rmi.sessioncounter.server");

    public SessionCounterFactoryImpl() {
    	super();
    }

	/* Crea e restituisce un nuovo contatore di sessione. */
    public SessionCounter getSessionCounter() throws RemoteException {
		logger.info("SessionCounterFactoryImpl: creating a new SessionCounter...");
		SessionCounterImpl sessionCounter = new SessionCounterImpl();
		return sessionCounter;
		/* essendo un tipo remoto, restituisce un riferimento remoto */

//		SessionCounterInterface stub =
//			(SessionCounterInterface) UnicastRemoteObject.exportObject(sessionCounter, 0);
//		return stub;
    }

}
