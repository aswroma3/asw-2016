package asw.intro.server;

import asw.intro.service.Service;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

/* Implementazione del servizio Service. */
public class ServiceImpl implements Service {

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.intro.server");

	/* Crea una nuova istanza del servizio. */
	public ServiceImpl() {
		logger.info("Creazione di un nuovo ServiceImpl");
	}

	/* Restituisce la stringa passata come parametro,
	 * ma in lettere maiuscole. */
	public String alpha(String arg) {
		String result = arg.toUpperCase();
		logger.info("Servant: executing alpha(" + arg + ") ==> " + result);
    	return result;
    }

}

