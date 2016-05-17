package asw.ejb.asynchronous.impl;

import asw.ejb.asynchronous.*;

import java.util.concurrent.Future;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.interceptor.InvocationContext;

import asw.util.sleep.Sleeper;

/**
 * Session Bean implementation class AsynchronousHello
 */
@Stateless(mappedName = "ejb/asw/AsynchronousHello")
public class AsynchronousHelloImpl implements AsynchronousHello {

	/* logger */
	private Logger logger;

	/* il contesto e' necessario per la cancellazione
	 * delle operazioni asincrone */
    @Resource
    SessionContext ctx;

	/* inizializza un'istanza del bean */
	@PostConstruct
	public void initialize() {
		/* fa il logging sul log del server EJB */
    	logger = Logger.getLogger("asw.ejb.asynchronous");
		logger.info("AsynchronousHello.initialize(): initializing AsynchronousHello instance");
	}

	/** Un'operazione asincrona (che puo' essere interrotta). */
	@Asynchronous
	public Future<String> hello(String name) throws HelloException, InterruptedException {
		String result = null;
		logger.info("AsynchronousHello.hello(" + name + ") called");

		if (name==null) {
			throw new HelloException("Invalid parameters for hello()");
		}

		/* diciamo che questa operazione perde un po' di tempo e
		 * poi restituisce proprio il saluto desiderato */
		for (int i=0; i<name.length()*10; i++) {
			Sleeper.sleep(40);
			if (ctx.wasCancelCalled()) {
				logger.info("AsynchronousHello.hello(" + name + ") was cancelled");
				/* credo che la cosa giusta da fare qui sia sollevare un'eccezione */
				throw new InterruptedException("Operation AsynchronousHello.hello(" + name + ") was cancelled");
			}
		}
		result = "Hello, " + name.toUpperCase() + "!";
		logger.info("AsynchronousHello.hello(" + name + ") ==> " + result);
		return new AsyncResult<String>(result);
	}

}
