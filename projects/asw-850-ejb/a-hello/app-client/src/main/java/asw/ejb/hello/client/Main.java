package asw.ejb.hello.client;

import asw.ejb.hello.service.Hello;

import javax.ejb.EJB;

import java.util.logging.*;
import asw.util.logging.*;

/*
 * Application client per l'EJB Hello.
 */

public class Main {

		@EJB(lookup = "ejb/asw/Hello")
		public static Hello hello;

		/* logger */
		private static Logger logger = AswLogger.getInstance().getLogger("asw.ejb.hello.client");

		public static void main(String[] args) {
			new Main() . run();
		}

		public Main() {
		}

		private void run() {
	    	/* usa il servizio Hello */
	    	logger.info("Client: Ora uso il servizio Hello");

	    	/* dovrebbe restituire "Hello, Luca!" */
	    	callHello("Luca");

	    	/* dovrebbe restituire "Ciao, Paolo!" */
	    	callHello("Paolo", "it");

	    	/* dovrebbe restituire "Bonjour, Francesca!" */
	    	callHello("Francesca", "fr");

	    	logger.info("Client: Ho finito di usare il servizio Hello");

		}

	    /* chiama il servizio hello */
	    private void callHello(String name) {
	    	/* usa il servizio Hello */
			logger.info("Client: calling hello(" + name + ")");
			String result = hello.hello(name);
			logger.info("Client: hello(" + name + ") ==> " + result);
	    }

	    /* chiama il servizio hello */
	    private void callHello(String name, String language) {
	    	/* usa il servizio Hello */
			logger.info("Client: calling hello(" + name + ", " + language + ")");
			String result = hello.hello(name, language);
			logger.info("Client: hello(" + name + ", " + language + ") ==> " + result);
	    }

}