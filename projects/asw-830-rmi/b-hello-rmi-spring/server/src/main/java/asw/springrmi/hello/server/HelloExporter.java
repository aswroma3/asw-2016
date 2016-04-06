package asw.springrmi.hello.server;

import java.util.logging.*;
import asw.util.logging.*;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Exporter (server) per il servizio Hello.
 * Crea un servant e lo registra sul registry RMI.
 */
public class HelloExporter {

	/* logger */
	private static Logger logger = AswLogger.getInstance().getLogger("asw.springrmi.hello.server");

	public static void main(String[] args) {
		logger.info("Avvio un server per Hello");
		ClassPathXmlApplicationContext context =
			new ClassPathXmlApplicationContext("spring/hello-rmi-server.xml");
		// context.getBean(asw.springrmi.hello.servant.HelloServant.class);
		logger.info("Server per Hello avviato");

		// context.close();
	}

}


