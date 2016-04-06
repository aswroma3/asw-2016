package asw.rmi.sessioncounter.service;

import java.rmi.*;

/**
 * Interfaccia del (session) counter.
 */
public interface SessionCounter extends Remote {

	/* Incrementa il valore del contatore di sessione e lo restituisce. */
	public int getCounter() throws RemoteException;

	/* Restituisce l'id del contatore (solo per il debugging). */
	public int getId() throws RemoteException;

}
