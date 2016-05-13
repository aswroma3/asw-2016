package asw.rest.productmanager;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import javax.persistence.*;
import javax.transaction.*;

import java.net.URI;
import javax.servlet.http.HttpServletResponse;

import java.util.*;

import javax.ejb.*;

// import javax.annotation.PostConstruct;

/*
 * L'annotazione @Stateless (e' un EJB) serve soprattutto a consentire
 * l'iniezione della dipendenza dell'entity manager (em).
 * In alternativa si potrebbe fare con Java CDI (@ApplicationScoped o @RequestScoped)
 * ma nessuno degli scopi previsti e' adeguato per l'iniezione dell'em.
 * Pertanto va bene @Stateless.
 */

@Stateless
@Path("/products")
public class ProductContainer {
	@Context
	private UriInfo uriInfo;

    @PersistenceContext(unitName="product-manager-pu")
    private EntityManager em;

    public ProductContainer() { }

    /* GET: Restituisce la collezione di tutti i prodotti */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
    public Collection<Product> getProducts() {
		try {
			Collection<Product> products = em.createQuery("SELECT p FROM Product p").getResultList();
			if (products==null) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
			} else {
				return products;
			}
		} catch (Exception e) {
			String errorMessage = "Error while finding all products: " + e.getMessage();
    		throw new WebApplicationException(
				Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				                        .entity(errorMessage).type("text/plain").build());
		}
    }

    /* POST: Aggiunge un nuovo prodotto
     * sulla base di un form con campi id, description e price */
    @POST
    @Transactional
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createProduct(
			@FormParam("id") int id,
			@FormParam("description") String description,
			@FormParam("price") int price) {
    	Product p = new Product(id, description, price);
		/* fa questa ricerca per evitare che venga sollevata un'eccezione al momento del commit */
		Product oldProduct = em.find(Product.class, id);
		if (oldProduct==null) {
			try {
				em.persist(p);
	            return Response.created(URI.create("/" + id)).entity(p).build();
			} catch (Exception e) {
	    		String errorMessage = "Error while creating Product " + p.toString() + ": " + e.getMessage();
	    		throw new WebApplicationException(
					Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				                        .entity(errorMessage).type("text/plain").build());
			}
		} else {
    		String errorMessage = "Error while creating Product with id " + id + ": a product with the same id already exists";
    		throw new WebApplicationException(
			Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			                        .entity(errorMessage).type("text/plain").build());
		}
    }

    /* POST: Aggiunge un nuovo prodotto, passato con JSON o XML */
    @POST
    @Transactional
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    public Response createProduct(Product p) {
		int id = p.getId();
		/* fa questa ricerca per evitare che venga sollevata un'eccezione al momento del commit */
		Product oldProduct = em.find(Product.class, id);
		if (oldProduct==null) {
			try {
				em.persist(p);
	            return Response.created(URI.create("/" + id)).entity(p).build();
			} catch (Exception e) {
	    		String errorMessage = "Error while creating Product " + p.toString() + ": " + e.getMessage();
	    		throw new WebApplicationException(
					Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				                        .entity(errorMessage).type("text/plain").build());
			}
		} else {
    		String errorMessage = "Error while creating Product with id " + id + ": a product with the same id already exists";
    		throw new WebApplicationException(
				Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			                        .entity(errorMessage).type("text/plain").build());
		}
    }

}