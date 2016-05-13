package asw.rest.async;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.container.*;

import java.util.*;

import asw.util.sleep.*;

// import javax.ejb.*;

/*
 * La URI per l'accesso al servizio sara' formata da queste parti:
 * - context root (asw/asyncrest, specificata al momento del deploy)
 * - base uri ("", specificata da AsycnApplication o dal file web.xml)
 * - resource uri (/async/... definita da @Path per la classe e poi eventualmente estesa dal singolo metodo)
 */
//@Stateless
@Path("/async")
public class AsyncService {
    @Context
    private UriInfo context;

    /* Crea una nuova istanza di AsyncService. */
    public AsyncService() {
	}

    /* Operazione asincrona
     * acceduta come GET /asw/asyncrest/async/{string} */
	@Path("{string}/")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public void asyncGet(@PathParam("string") String string, @Suspended final AsyncResponse asyncResponse) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = veryExpensiveOperation(string);
                asyncResponse.resume(result);
            }

            private String veryExpensiveOperation(String string) {
				long startingTime = System.currentTimeMillis();
				/* ci mette un paio di decimi di secondo a carattere */
				for (int i=0; i<string.length(); i++) {
					Sleeper.sleep(200);
				}
				long endingTime = System.currentTimeMillis();
				return "Hello, " + string + " (" + (endingTime-startingTime) + " ms)!";
			}
        }).start();
    }

}
