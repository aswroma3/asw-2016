package asw.rmi.counter.service;

import java.rmi.*;

/**
 * Interfaccia counter.
 */
public interface Counter extends Remote {
	/* Incrementa il valore del contatore e lo restituisce. */
	public int getCounter() throws RemoteException;
}
