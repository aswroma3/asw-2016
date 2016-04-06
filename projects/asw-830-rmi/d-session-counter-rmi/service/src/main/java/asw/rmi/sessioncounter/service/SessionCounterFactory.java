package asw.rmi.sessioncounter.service;

import java.rmi.*;

/**
 * Interfaccia per la factory del (session) counter.
 */
public interface SessionCounterFactory extends Remote {
	/* Crea e restituisce un nuovo contatore di sessione. */
    public SessionCounter getSessionCounter() throws RemoteException;
}
