package asw.rest.async.client;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;

import java.util.concurrent.Future;

import asw.util.logging.*;
import java.util.logging.*;

import asw.util.sleep.*;

/*
 * Client per il web service REST Async.
 */

public class AsyncClient {

    /* URL del servizio REST (base URI) */
    private static final String SERVER_URL = "http://10.11.1.101:8080";
    private static final String CONTEXT_ROOT = "asw/asyncrest";
    // private static final String REST_APPLICATION = "async";
    private static final String REST_SERVICE = "async";

    private static final String ASYNC_SERVICE_URL = SERVER_URL + "/" +
                                                    CONTEXT_ROOT + "/" +
    //                                                REST_APPLICATION + "/" +
                                                    REST_SERVICE;

    /* client jax-rs per il servizio rest */
    /* il client e' un "heavy-weight object", e per questo andrebbe riutilizzato in piu' chiamate;
     * tuttavia, l'implementazione sembra essere single-threaded, e dunque chiamate concorrenti
     * devono essere fatte con client diversi - che e' la situazione che si puo' verificare con il callback;
     * questi client e target sono condivisi solo dalle chiamate senza callback */
    private Client client;
    private WebTarget asyncService;

	/* logger */
	private static Logger logger = AswLogger.getInstance().getLogger("asw.rest.async.client");

	public AsyncClient() {
		/* crea il client per il servizio rest (usato solo per le chiamate senza callback) */
    	logger.info("Client: Inizializzo il client per il servizio REST Async");
		client = ClientBuilder.newClient();
		asyncService = client.target(ASYNC_SERVICE_URL);
	}

	public void run() {
	   	/* usa il servizio rest Async */
	   	logger.info("Client: Ora uso il servizio REST Async all'indirizzo " + ASYNC_SERVICE_URL);

		callGetAsync("Luca");

		callGetAsyncConCallback("Benedetta e Dario");

		callGetAsyncConCallback("Ugo");

    	logger.info("");
    	logger.info("Client: ora attendo qualche altro secondo la conclusione delle chiamate asincrone");
    	logger.info("");
		Sleeper.sleep(8000);

        client.close();
    	logger.info("Client: Ho finito di usare il servizio REST Async");
	}

    /* chiama l'operazione asincrona (primo metodo, semplice) */
	private void callGetAsync(String string) {
	   	logger.info("");
	   	logger.info("Client: GET " + ASYNC_SERVICE_URL + "/" + string);
		AsyncInvoker asyncInvoker = asyncService
		                     .path("{string}")
		                     .resolveTemplate("string", string)
		                     .request(MediaType.TEXT_PLAIN)
							 .async();
		long startingTime = System.currentTimeMillis();
		Future<Response> futureResponse = asyncInvoker.get();
		while (!futureResponse.isDone()) {
			long currentTime = System.currentTimeMillis();
			logger.info("Client: [in attesa di risposta da " + (currentTime-startingTime) + " ms]");
	    	Sleeper.sleep(250);
		}
		Response response = null;
		try {
			response = futureResponse.get();
		} catch (Exception e) {}
		long endingTime = System.currentTimeMillis();
		String result = response.readEntity(String.class);
	   	logger.info("Client: --> " + result + " [attesa: " + (endingTime-startingTime) + " ms]");
	}

    /* chiama l'operazione asincrona (secondo metodo, con callback) */
    private void callGetAsyncConCallback(String string) {
    	logger.info("Client: Creo un nuovo client per una chiamata asincrona con callback");
		Client myClient = ClientBuilder.newClient();
		WebTarget myService = myClient.target(ASYNC_SERVICE_URL);

	   	logger.info("");
	   	logger.info("Client: GET " + ASYNC_SERVICE_URL + "/" + string + " (con callback)");
		AsyncInvoker asyncInvoker = myService
		                     .path("{string}")
		                     .resolveTemplate("string", string)
		                     .request(MediaType.TEXT_PLAIN)
							 .async();
		long startingTime = System.currentTimeMillis();
		Future<Response> futureResponse = asyncInvoker.get(
			new InvocationCallback<Response>() {
	            @Override
	            public void completed(Response response) {
					long endingTime = System.currentTimeMillis();
					String result = response.readEntity(String.class);
				   	logger.info("Client: GET " + ASYNC_SERVICE_URL + "/" + string + " --> " + result +
								" [dopo un'attesa di " + (endingTime-startingTime) + " ms]");
					myClient.close();
	            }
	            @Override
	            public void failed(Throwable throwable) {
				   	logger.info("Client: GET " + ASYNC_SERVICE_URL + "/" + string + " FAILED --> " + throwable.getMessage());
					myClient.close();
	            }
	        } );
    }


}