package asw.rmi.hello.client;

import asw.rmi.hello.service.*;

import java.rmi.*;
import java.rmi.registry.*;

import java.util.logging.*;

import asw.util.logging.*;

/**
 * Un semplice client per il servizio Hello.
 */
public class HelloClient {

	/* registryHost e' "localhost" oppure "10.11.1.101" */
	/* per default e' "localhost" */
	private static String registryHost = "localhost";

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.rmi.hello.client");

	/* il servizio */
	private Hello hello;

	/*
	 * Applicazione che crea e avvia un oggetto HelloClient.
	 */
	public static void main(String[] args) {
		/* se il caso, cambia il registry host */
		if (args.length>0) {
			registryHost = args[0];
		}
		HelloClient client = new HelloClient();
		client.run();
	}

	/*
	 * Qui avviene l'accesso al servizio service.
	 */
    public void run() {
    	/* ottiene un proxy al servizio remoto */
    	logger.info("Client: Ora cerco il servizio Hello");
    	hello = getHelloService();

    	/* usa il servizio Hello */
    	logger.info("Client: Ora uso il servizio Hello");

    	/* dovrebbe restituire "Hello, Luca!" */
    	callSayHello("Luca");

    	/* dovrebbe sollevare un'eccezione */
    	callSayHello(null);

    	/* dovrebbe sollevare una remote exception
    	 * infatti, nello script per il client e' specificato un timeout massimo di 5 secondi
    	 * mentre il servente introduce un ritardo di 10 secondi se il nome e' lungo pie' di 20 caratteri */
    	callSayHello("un nome molto molto molto lungo potrebbe portare ad un'eccezione remota");

    	/* dovrebbe restituire "Hello, Paolo!" */
    	callSayHello("Paolo");

    	logger.info("Client: Ho finito di usare il servizio Hello");
    }

    /* chiama il servizio sayHello */
    private void callSayHello(String arg) {
    	/* usa il servizio Hello */
        try {
			logger.info("Client: calling sayHello(" + arg + ")");
			String result = hello.sayHello(arg);
			logger.info("Client: sayHello(" + arg + ") ==> " + result);
        } catch (HelloException e) {
            logger.info("Client: HelloException: " + e.getMessage());
        } catch (java.rmi.RemoteException e) {
            logger.info("Client: RemoteException: " + e.getMessage());
        }
    }

    /* ottiene un proxy al servizio remoto */
    private Hello getHelloService() {
    	Hello hello = null;
        /* crea il security manager */
    	if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
    	/* trova il servizio Hello */
        try {
        	/* ottiene un proxy al servizio remoto */
        	String helloServiceName = "rmi:/asw/Hello";
            logger.info("Client: looking for " + helloServiceName + " on " + registryHost);
            Registry registry = LocateRegistry.getRegistry(registryHost);
            hello = (Hello) registry.lookup(helloServiceName);
            if (hello!=null) {
	            logger.info("Client: " + helloServiceName + " found");
			} else {
	            logger.info("Client: " + helloServiceName + " not found");
		}
        } catch (RemoteException e) {
        	logger.info("Client: RemoteException: " + e.getMessage());
        } catch (NotBoundException e) {
        	logger.info("Client: NotBoundException: " + e.getMessage());
        }
        return hello;
    }
}

