package asw.rest.hello.client;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;

import asw.util.logging.*;
import java.util.logging.*;

/*
 * Adapter per il web service REST hello.
 */

public class HelloClient {

    /* URL del servizio REST (base URI) */
    private static final String SERVER_URL = "http://10.11.1.101:8080";
    private static final String CONTEXT_ROOT = "asw/hellorest";
    // private static final String REST_APPLICATION = "hello";
    private static final String REST_SERVICE = "hello";

    private static final String HELLO_SERVICE_URL = SERVER_URL + "/" +
                                                    CONTEXT_ROOT + "/" +
    //                                                REST_APPLICATION + "/" +
                                                    REST_SERVICE;

    /* client jax-rs per il servizio rest */
    private Client client;
    private WebTarget hello;

	/* logger */
	private static Logger logger = AswLogger.getInstance().getLogger("asw.rest.hello.client");

	public HelloClient() {
		/* crea il client per il servizio rest */
	   	logger.info("HelloClient: adapter per servizio REST Hello all'indirizzo " + HELLO_SERVICE_URL);
		client = ClientBuilder.newClient();
		hello = client.target(HELLO_SERVICE_URL);
	}

	public String getHelloWorld() {
	   	logger.info("HelloClient: GET " + HELLO_SERVICE_URL + "/helloworld/");
		String result = hello.path("helloworld")
		                     // equivale a .request().accept(MediaType.TEXT_PLAIN)
		                     .request(MediaType.TEXT_PLAIN)
                             .get(String.class);
	   	logger.info("HelloClient: --> " + result);
        return result;
	}


	public String getSayHello(String name) {
	   	logger.info("HelloClient: GET " + HELLO_SERVICE_URL + "/sayhello/" + name);
		String result = hello.path("sayhello")
		                     .path("{name}")
		                     .resolveTemplate("name", name)
		                     .request(MediaType.TEXT_PLAIN)
                             .get(String.class);
	   	logger.info("HelloClient: --> " + result);
        return result;
	}

	public String getSayHello(String name, String lang) {
	   	logger.info("HelloClient: GET " + HELLO_SERVICE_URL + "/sayhello/name=" + name + "&lang=" + lang);
		String result = hello.path("sayhello")
		                     .queryParam("name", name)
		                     .queryParam("lang", lang)
		                     .request(MediaType.TEXT_PLAIN)
                             .get(String.class);
	   	logger.info("HelloClient: --> " + result);
        return result;
	}

	public String postSayHello(String name, String lang) {
	   	logger.info("HelloClient: POST " + HELLO_SERVICE_URL + "/sayhello WITH name=" + name + " lang=" + lang);
		Form form = new Form().param("name", name)
		                      .param("lang", lang);
		String result = hello.path("sayhello")
		                     .request(MediaType.TEXT_PLAIN)
		                     .buildPost(Entity.form(form))
                             .invoke(String.class);
	   	logger.info("HelloClient: --> " + result);
        return result;
	}

	public String postSayHello(String name) {
	   	logger.info("HelloClient: POST " + HELLO_SERVICE_URL + "/sayhello WITH name=" + name);
		Form form = new Form().param("name", name);
		String result = hello.path("sayhello")
		                     .request(MediaType.TEXT_PLAIN)
		                     .buildPost(Entity.form(form))
                             .invoke(String.class);
	   	logger.info("HelloClient: --> " + result);
        return result;
	}

	public void close() {
	   	logger.info("HelloClient: close()");
	   	client.close();
	}

}