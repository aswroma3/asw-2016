package asw.springrmi.hello.client;

import asw.springrmi.hello.service.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.*;
import asw.util.logging.*;

/**
 * Un semplice client per il servizio Hello.
 */
public class HelloClient {

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.springrmi.hello.client");

	/* il servizio remoto */
	private Hello hello;

	@Autowired
	public void setHello(Hello hello) {
		this.hello = hello;
	}

	/*
	 * Qui avviene l'accesso al servizio service.
	 */
    public void run() {

    	/* usa il servizio Hello */
    	logger.info("Client: Ora uso il servizio Hello");

    	/* dovrebbe restituire "Hello, Luca!" */
    	callSayHello("Luca");

    	/* dovrebbe sollevare un'eccezione */
    	callSayHello(null);

    	/* dovrebbe sollevare una remote exception
    	 * infatti, nello script per il client e' specificato un timeout massimo di 5 secondi
    	 * mentre il servente introduce un ritardo di 10 secondi se il nome e' lungo piu' di 20 caratteri */
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
        } catch (Exception e) {
			/* potrebbe essere un'eccezione remota rmi */
            logger.info("Client: General Exception: " + e.getMessage());
        }
    }

}

