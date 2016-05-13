package asw.rest.hello;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/*
 * La base URI per l'applicazione puo' essere definita in due modi:
 * - con l'annotazione @ApplicationPath in una classe che estende Application (come in questo caso)
 * - oppure con il file web.xml
 * In effetti, la URI per l'accesso al servizio sara':
 * context root (al deploy) / base uri (definita qui) / resource uri (definita con @Path)
 */
//@ApplicationPath("/hello")
@ApplicationPath("/")
public class HelloApplication extends Application {

	/* per default, tutte le risorse dell'applicazione sono gestite come tali,
	 * dunque questa registrazione esplicita non e' necessaria */
//    @Override
//    public Set<Class<?>> getClasses() {
//        final Set<Class<?>> classes = new HashSet<>();
//        // register root resource
//        classes.add(HelloService.class);
//        return classes;
//    }

}
