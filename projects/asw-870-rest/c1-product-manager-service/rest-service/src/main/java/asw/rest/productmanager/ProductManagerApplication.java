package asw.rest.productmanager;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.google.common.collect.ImmutableSet;

/*
 * La base URI per l'applicazione puo' essere definita in due modi:
 * - con l'annotazione @ApplicationPath in una classe che estende Application (come in questo caso)
 * - oppure con il file web.xml
 * In effetti, la URI per l'accesso al servizio sara':
 * context root (al deploy) / base uri (definita qui) / resource uri (definita con @Path)
 *
 * Il metodo getSingleton e' stato definito per risolvere il problema del Provider JSON
 * che non viene trovato alla prima richiesta.
 * Vedi https://java.net/jira/browse/GLASSFISH-21141
 */
@ApplicationPath("/")
public class ProductManagerApplication extends Application {

	/* per default, tutte le risorse dell'applicazione sono gestite come tali,
	 * dunque questa registrazione esplicita non e' necessaria */
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        // register root resource
        classes.add(ProductResource.class);
        classes.add(ProductContainer.class);
        return classes;
    }

    @Override
    /* Risolve il problema del Provider JSON non trovato alla prima richiesta.
     * Vedi https://java.net/jira/browse/GLASSFISH-21141 */
	public Set<Object> getSingletons() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JaxbAnnotationModule());
		return ImmutableSet
  				.<Object> builder()
				.add(new JacksonJaxbJsonProvider(mapper, JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS)).build();
	}
}
