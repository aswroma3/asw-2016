package asw.ejb.hello.impl;

import asw.ejb.hello.service.Hello;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class HelloImpl
 */
@Stateless(mappedName = "ejb/asw/Hello")
public class HelloImpl implements Hello {

	/* logger */
	private Logger logger;

	/* mappa (dei formati) dei saluti nelle diverse lingue */
	private Map<String, String> saluti;

	/* inizializza un'istanza del bean */
	@PostConstruct
	public void initialize() {
		/* fa il logging sul log del server EJB
		 * in particolare, nel file C:\glassfish4\glassfish\domains\domain1\logs\server.log */
    	logger = Logger.getLogger("asw.ejb.hello.impl");
		logger.info("Hello.initialize(): initializing Hello instance");
		/* inizializza la mappa dei saluti */
		saluti = new HashMap<>();
		saluti.put("it", "Ciao, %s!");
		saluti.put("en", "Hello, %s!");
		saluti.put("fr", "Bonjour, %s!");
		saluti.put("es", "Hola, %s!");
	}

	/** Saluta il nome (in modo generico). */
	public String hello(String name) {
		String format = "Hello, %s!";
		String result = String.format(format, name);
		logger.info("Hello.hello(" + name + ") ==> " + result);
		return result;
	}

	/** Saluta il nome in una specifica lingua. */
	public String hello(String name, String language) {
		String format = saluti.get(language);
		String result = String.format(format, name);
		logger.info("Hello.hello(" + name + ", " + language + ") ==> " + result);
		return result;
	}

}
