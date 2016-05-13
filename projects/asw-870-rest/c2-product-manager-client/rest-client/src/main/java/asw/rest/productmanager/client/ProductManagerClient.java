package asw.rest.productmanager.client;

import asw.rest.productmanager.Product;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.xml.JacksonJaxbXMLProvider;

import java.util.*;

import asw.util.logging.*;
import java.util.logging.*;

/*
 * Client (adapter) per il web service REST product-manager.
 */

public class ProductManagerClient {

    /* URL del servizio REST (base URI) */
    private static final String SERVER_URL = "http://10.11.1.101:8080";
    private static final String CONTEXT_ROOT = "asw/productmanager";
    // private static final String REST_APPLICATION = "productmanager";
    // private static final String REST_SERVICE = "products";

    private static final String PRODUCT_MANAGER_SERVICE_URL = SERVER_URL
    												+ "/" + CONTEXT_ROOT
//                                                    + "/" + REST_APPLICATION
//                                                    + "/" + REST_SERVICE
    											;

    /* client jax-rs per il servizio rest */
    /* il client e' un "heavy-weight object",
     * e per questo se necessario va riutilizzato in piu' chiamate */
    private Client client;
    private WebTarget productManagerService;

	/* logger */
	private static Logger logger = AswLogger.getInstance().getLogger("asw.rest.productmanager.client");

	public ProductManagerClient() {
		/* crea il client per il servizio rest
		 * inoltre registra i provider (reader e writer) per Json e XML */
		client = ClientBuilder.newClient()
		                      .register(JacksonJaxbJsonProvider.class)
		                      .register(JacksonJaxbXMLProvider.class);
		productManagerService = client.target(PRODUCT_MANAGER_SERVICE_URL);
	}

	public void close() {
		client.close();
	}

    /* GET: Cerca un prodotto (usa JSON) */
    public Product getProductAsJson(int id) {
		Product result = productManagerService
		                     .path("product")
		                     .path("{id}")
		                     .resolveTemplate("id", id)
		                     .request(MediaType.APPLICATION_JSON)
                             .get(Product.class);
        return result;
	}

    /* GET: Cerca un prodotto (usa XML) */
    public Product getProductAsXML(int id) {
		Product result = productManagerService
		                     .path("product")
		                     .path("{id}")
		                     .resolveTemplate("id", id)
		                     .request(MediaType.APPLICATION_XML)
                             .get(Product.class);
        return result;
	}

    /* GET: Restituisce l'elenco di tutti i prodotti (JSON) */
    public Collection<Product> getProductsAsJson() {
		Collection<Product> result = productManagerService
		                     .path("products")
		                     .request(MediaType.APPLICATION_JSON)
                             .get( new GenericType<Collection<Product>>(){} );
        return result;
	}

    public String getProductsAsJsonString() {
		String result = productManagerService
		                     .path("products")
		                     .request(MediaType.APPLICATION_JSON)
                             .get( String.class );
        return result;
	}

    /* GET: Restituisce l'elenco di tutti i prodotti (XML) */
    public Collection<Product> getProductsAsXML() {
		Collection<Product> result = productManagerService
		                     .path("products")
		                     .request(MediaType.APPLICATION_XML)
                             .get( new GenericType<Collection<Product>>(){} );
        return result;
	}

    public String getProductsAsXMLString() {
		String result = productManagerService
		                     .path("products")
		                     .request(MediaType.APPLICATION_XML)
                             .get( String.class );
        return result;
	}

    /* POST: Aggiunge un nuovo prodotto
     * sulla base di un form con campi id, description e price */
    public boolean createProduct(int id, String description, int price) {
		Form form = new Form().param("id", String.valueOf(id))
		                      .param("description", description)
		                      .param("price", String.valueOf(price));
		Response response = productManagerService
		                     .path("products")
		                     //.request(MediaType.APPLICATION_JSON)
		                     .request()
		                     .buildPost(Entity.form(form))
                             .invoke();
        boolean result = response.getStatus() == Response.Status.CREATED.getStatusCode();
        /* la risposta va chiusa per liberare il client per altre richieste */
        response.close();
        return result;
    }

    /* POST: Aggiunge un nuovo prodotto, passandolo con JSON */
    public boolean createProduct(Product p) {
		Response response = productManagerService
		                     .path("products")
		                     //.request(MediaType.APPLICATION_JSON)
		                     .request()
		                     .buildPost(Entity.entity(p, MediaType.APPLICATION_JSON))
                             .invoke();
        boolean result = response.getStatus() == Response.Status.CREATED.getStatusCode();
        response.close();
        return result;
	}

    /* PUT: Aggiorna un prodotto, passato con JSON */
    public boolean updateProduct(Product p) {
		int id = p.getId();
		Response response = productManagerService
		                     .path("product")
		                     .path("{id}")
		                     .resolveTemplate("id", id)
		                     //.request(MediaType.APPLICATION_JSON)
		                     .request()
		                     .buildPut(Entity.entity(p, MediaType.APPLICATION_JSON))
                             .invoke();
        boolean result = response.getStatus() == Response.Status.OK.getStatusCode();
        response.close();
        return result;
    }

    /* DELETE: Cancella un prodotto */
    public boolean deleteProduct(int id) {
		Response response = productManagerService
		                     .path("product")
		                     .path("{id}")
		                     .resolveTemplate("id", id)
		                     //.request(MediaType.APPLICATION_JSON)
		                     .request()
		                     .buildDelete()
                             .invoke();
        boolean result = response.getStatus() == Response.Status.OK.getStatusCode();
        response.close();
        return result;
    }

}

