package asw.rmi.hello.service;

import java.rmi.*;

/**
 * Interfaccia del servizio Hello.
 */
public interface Hello extends Remote {
	/**
	 * Crea un saluto specifico per il nome name.
	 * Name deve essere non nullo, altrimenti viene sollevata una HelloException.
	 */
    public String sayHello(String name) throws HelloException, RemoteException;
}

