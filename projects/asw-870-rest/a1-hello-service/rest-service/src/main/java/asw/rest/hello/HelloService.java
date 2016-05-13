package asw.rest.hello;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

/*
 * La URI per l'accesso al servizio sara' formata da queste parti:
 * - context root (asw/hellorest, specificata al momento del deploy)
 * - base uri ("", specificata da HelloApplication o dal file web.xml)
 * - resource uri (/hello/... definita da @Path per la classe e poi eventualmente estesa dal singolo metodo)
 */
@Path("/hello")
public class HelloService {
    @Context
    private UriInfo context;

    /* nel modo verboso restituisce anche informazioni sulla chiamata */
    private static final boolean VERBOSE = false;

	/* mappa (dei formati) dei saluti nelle diverse lingue */
	private Map<String, String> saluti;

    /* Crea una nuova istanza di HelloService. */
    public HelloService() {
		/* inizializza la mappa dei saluti */
		saluti = new HashMap<>();
		saluti.put("it", "Ciao, %s!");
		saluti.put("en", "Hello, %s!");
		saluti.put("fr", "Bonjour, %s!");
		saluti.put("es", "Hola, %s!");
	}

    /* Restituisce Hello, world!
     * acceduta come GET /asw/hellorest/hello/helloworld */
	@Path("helloworld/")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getHelloWorld() {
		String result = "Hello, world!";
		if (!VERBOSE) {
			return result;
		} else {
			return "getHelloWorld() -> " + result;
		}
    }

    /* Restituisce un saluto a "name"
     * acceduta come GET /asw/hellorest/hello/sayhello/{name} */
	@Path("sayhello/{name}/")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getSayHello(@PathParam("name") String name) {
		String format = "Hello, %s!";
		String result = String.format(format, name);
		if (!VERBOSE) {
			return result;
		} else {
			return "getSayHello(" + name + ") -> " + result;
		}
    }

    /* Restituisce un saluto a "name" nel "language" specificato
     * acceduta come GET /asw/hellorest/hello/sayhello?name={name}&lang={language} */
	@Path("sayhello/")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getSayHello(@DefaultValue("world") @QueryParam("name") String name,
                              @DefaultValue("en") @QueryParam("lang") String lang) {
		String format = saluti.get(lang);
		String result = String.format(format, name);
		if (!VERBOSE) {
			return result;
		} else {
			return "getSayHello(" + name + ", " + lang + ") -> " + result;
		}
    }

    /* Restituisce un saluto a "name" nel "language" specificato
     * acceduta come POST /asw/hellorest/hello/sayhello
     * con campi name={name} e lang={language} */
	@Path("sayhello/")
    @POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String postSayHello(@DefaultValue("world") @FormParam("name") String name,
                               @DefaultValue("en") @FormParam("lang") String lang) {
		String format = saluti.get(lang);
		String result = String.format(format, name);
		if (!VERBOSE) {
			return result;
		} else {
			return "postSayHello(" + name + ", " + lang + ") -> " + result;
		}
    }

    /* Restituisce informazioni sul servizio Hello
     * acceduta come GET /asw/hellorest/hello/info */
	@Path("info/")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getInfo(@Context HttpServletRequest request) {
		String baseUri = context.getBaseUri().toString();
		String path = context.getPath();

    	String remoteHost = request.getRemoteHost();
    	String remoteAddr = request.getRemoteAddr();
    	int remotePort = request.getRemotePort();

      	String msg = "INFO: GET " + path + " ON " + baseUri + " FROM " + remoteHost + " (" + remoteAddr + ":" + remotePort + ")";
    	return Response.ok(msg).build();
    }

}
