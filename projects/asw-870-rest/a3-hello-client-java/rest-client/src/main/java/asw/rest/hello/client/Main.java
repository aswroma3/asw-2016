package asw.rest.hello.client;

import asw.util.logging.*;
import java.util.logging.*;

/*
 * Client per il web service REST hello.
 */

public class Main {

	/* logger */
	private static Logger logger = AswLogger.getInstance().getLogger("asw.rest.hello.client");

	public static void main(String[] args) {
		HelloClient hello = new HelloClient();
		String result;

	   	/* usa il servizio rest Hello */
	   	logger.info("Client: Ora uso il servizio REST Hello");

	   	logger.info("Client: getHelloWorld()");
		result = hello.getHelloWorld();
	   	logger.info("Client: --> " + result);

	   	logger.info("Client: getSayHello(\"Luca\")");
		result = hello.getSayHello("Luca");
	   	logger.info("Client: --> " + result);

	   	logger.info("Client: getSayHello(\"Francesca\", \"fr\")");
		result = hello.getSayHello("Francesca", "fr");
	   	logger.info("Client: --> " + result);

	   	logger.info("Client: postSayHello(\"Paolo\", \"es\")");
		result = hello.postSayHello("Paolo", "es");
	   	logger.info("Client: --> " + result);

	   	logger.info("Client: postSayHello(\"Riccardo\")");
		result = hello.postSayHello("Riccardo");
	   	logger.info("Client: --> " + result);

        hello.close();
    	logger.info("Client: Ho finito di usare il servizio REST Hello");
	}

}