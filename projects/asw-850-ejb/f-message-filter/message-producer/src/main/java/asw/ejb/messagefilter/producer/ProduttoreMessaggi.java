/*
 * Main.java
 *
 * Classe principale per l'application client SimpleProducer.
 *
 * Esecuzione: dal prompt dei comandi, dalla cartella del progetto, tramite appclient:
 * appclient -client build\SimpleProducer <nomeProducer> <destinazione (queue o topic)> <#messaggi> <ritardo tra messaggi>
 *
 * Variante di un esempio nel tutorial per Java EE.
 *
 * @author cabibbo
 */

package asw.ejb.messagefilter.producer;

import asw.jms.simpleproducer.SimpleProducer;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import asw.util.logging.*;
import java.util.logging.*;

import asw.util.sleep.Sleeper;

public class ProduttoreMessaggi {

	/* logger */
	private static Logger logger = AswLogger.getInstance().getLogger("asw.ejb.messagefilter.producer");

	/* la destinazione a cui inviare messaggi */
	private Destination destination;

	/* connection factory */
	private ConnectionFactory connectionFactory;

	/* Crea un nuovo produttore di messaggi. */
	public ProduttoreMessaggi(Destination destination, ConnectionFactory connectionFactory) {
		this.destination = destination;
		this.connectionFactory = connectionFactory;
	}

	/*
	 * Invia una sequenza di messaggi tramite il proprio producer.
	 */
	public void run() {

		/* definizione dei parametri */
    	String name = "Produttore[" + (int)(Math.random()*1000) + "]";
        int numMsgs = 20;
    	int maxDelay = 500;

        /* crea il proprio producer */
    	SimpleProducer producer = new SimpleProducer(name, destination, connectionFactory);
        logger.info("Creato producer: " + producer.toString());

    	/* invia messaggi */
    	logger.info("ProduttoreMessaggi: " + name + ": I will send " + numMsgs + " messages");
    	producer.connect();
    	/* invia numMsgs messaggi */
        for (int i=1; i<=numMsgs; i++) {
        	String message = "Message #" + i + " from " + name;
        	logger.info("ProduttoreMessaggi: " + name + ": Sending: " + message);
        	producer.sendMessage(message);
        	Sleeper.randomSleep(maxDelay/2,maxDelay);
        }
        producer.disconnect();
    	logger.info("ProduttoreMessaggi: " + name + ": Done");

    	System.exit(0);
    }

}
