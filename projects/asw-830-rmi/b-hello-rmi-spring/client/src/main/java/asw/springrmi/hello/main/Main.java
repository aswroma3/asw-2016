package asw.springrmi.hello.main;

import asw.springrmi.hello.client.*;

import java.util.logging.*;
import asw.util.logging.*;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Avvia un semplice client per Hello.
 */
public class Main {

	/* logger */
	private static Logger logger = AswLogger.getInstance().getLogger("asw.springrmi.hello.main");

	/*
	 * Applicazione che avvia un oggetto HelloClient.
	 */
	public static void main(String[] args) {
		logger.info("Avvio un client per Hello");
		ClassPathXmlApplicationContext context =
			new ClassPathXmlApplicationContext(
				"spring/hello-rmi-client.xml");
		HelloClient client = context.getBean(HelloClient.class);
		client.run();
		context.close();
		logger.info("Fine del client per Hello");
	}

}

