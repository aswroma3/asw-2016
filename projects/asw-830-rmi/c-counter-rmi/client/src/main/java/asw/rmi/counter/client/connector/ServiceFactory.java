package asw.rmi.counter.client.connector;

import asw.rmi.counter.service.Counter;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

/* Factory per il servizio Counter. */
public class ServiceFactory {

	/* registryHost e' "localhost" oppure "10.11.1.101" */
	/* per default e' "localhost" */
	private static String registryHost = "localhost";

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.rmi.counter.client.connector");

	private static ServiceFactory instance = null;    // per singleton

    public static synchronized ServiceFactory getInstance() {
        if (instance==null) instance = new ServiceFactory();
        return instance;
    }

    /* Factory method per il servizio counter. */
    public Counter getCounter(String[] args) {
    	if (args.length>0) {
    		registryHost = args[0];
    	}
        Counter counter = null;
        try {
        	/* cerca un riferimento al servizio remoto */
        	String counterServiceName = "rmi:/asw/Counter";
            logger.info("Main: looking for " + counterServiceName + " on " + registryHost);
            Registry registry = LocateRegistry.getRegistry(registryHost);
            counter = (Counter) registry.lookup(counterServiceName);
            logger.info("CounterClient: " + counterServiceName + " found");
        } catch (RemoteException e) {
        	logger.info("CounterClient: RemoteException: " + e.getMessage());
        } catch (NotBoundException e) {
        	logger.info("CounterClient: NotBoundException: " + e.getMessage());
        }
        return counter;
    }
}

