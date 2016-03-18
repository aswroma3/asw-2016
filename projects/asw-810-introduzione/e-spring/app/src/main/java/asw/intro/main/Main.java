package asw.intro.main;

import asw.intro.client.Client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/* Applicazione client: crea ed avvia il client. */
public class Main {

	/* Crea e avvia un oggetto Client. */
	public static void main(String[] args) {
    	ApplicationContext context = new ClassPathXmlApplicationContext("spring/app-beans.xml");
    	Client client = (Client) context.getBean("client");
		client.run();
	}

}
