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
@Path("/product/{id}")
public class ProductResource {
	@Context
	private UriInfo uriInfo;

    @PersistenceContext(unitName="product-manager-pu")
    private EntityManager em;

    public ProductResource() { }

    /* GET: Cerca un prodotto */
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    public Product getProduct(@PathParam("id") int id) {
		try {
			Product p = em.find(Product.class, id);
	    	if (p==null) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
	    	} else {
		    	return p;
			}
		} catch (Exception e) {
    		String errorMessage = "Error while finding Product with id: " + id +  ": " + e.getMessage();
    		throw new WebApplicationException(
				Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				                        .entity(errorMessage).type("text/plain").build());
		}
    }

    /* PUT: Aggiorna un prodotto, passato con JSON o XML */
    @PUT
    @Transactional
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    public Response updateProduct(@PathParam("id") int id, Product p) {
		/* fa questa ricerca per evitare che venga sollevata un'eccezione al momento del commit */
		Product oldProduct = em.find(Product.class, id);
		if (oldProduct==null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} else {
			try {
				em.merge(p);
				return Response.ok(p).status(Response.Status.OK).build();
			} catch (Exception e) {
	    		String errorMessage = "Error while updating Product " + p.toString() + ": " + e.getMessage();
	    		throw new WebApplicationException(
					Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					                        .entity(errorMessage).type("text/plain").build());
			}
		}
    }

    /* DELETE: Cancella un prodotto */
    @DELETE
    @Transactional
    public Response deleteProduct(@PathParam("id") int id) {
		try {
			Product product = em.find(Product.class, id);
	    	if (product==null) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
			} else {
				em.remove(product);
				return Response.ok(product).status(Response.Status.OK).build();
	    	}
		} catch (Exception e) {
    		String errorMessage = "Error while deleting Product with id: " + id + ": " + e.getMessage();
    		throw new WebApplicationException(
				Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				                        .entity(errorMessage).type("text/plain").build());
		}
    }

}