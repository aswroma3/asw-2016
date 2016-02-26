package asw.intro.connector;

import asw.intro.service.Service;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

public class ServiceProxy implements Service {

	/* il vero servizio */
	private Service service;

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.intro.connector");

	/* Crea un nuovo Service Proxy per il servizio Service. */
	public ServiceProxy(Service service) {
		logger.info("Creazione di un nuovo ServiceProxy");
		this.service = service;
	}

    /* Questo e' proprio il metodo alpha invocato dal client.
     * (Anche se il client pensa di parlare direttamente con il servant.) */
	public String alpha(String arg) {
		/* chiama il vero servizio */
		logger.info("Proxy: calling alpha(" + arg + ")");
		String result = service.alpha(arg);
		logger.info("Proxy: alpha(" + arg + ") ==> " + result);
		return result;
	}
}
