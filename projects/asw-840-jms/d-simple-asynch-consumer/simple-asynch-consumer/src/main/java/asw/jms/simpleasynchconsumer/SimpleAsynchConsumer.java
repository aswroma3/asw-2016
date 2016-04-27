package asw.jms.simpleasynchconsumer;

import javax.jms.*;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

import asw.util.cancellable.Cancellable;
import asw.util.sleep.Sleeper;

/*
 * SimpleAsynchConsumer.java
 *
 * Un SimpleAsynchConsumer e' in grado di ricevere una sequenza di messaggi
 * (in modo asincrono) da una generica destinazione JMS (coda o argomento).
 * Introduce un ritardo per simulare il tempo di elaborazione
 * di ciascun messaggio.
 *
 * I messaggi sono inviati da SimpleProducer.
 *
 * Variante di un esempio nel tutorial per Java EE.
 *
 * @author cabibbo
 */
public class SimpleAsynchConsumer implements Cancellable {

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.jms.simpleasynchconsumer");

	/* nome di questo consumer */
    private String name;
    /* destinazione di questo consumer */
    private Destination destination;
    /* connection factory di questo consumer */
    private ConnectionFactory connectionFactory;

    /* listener di questo consumer */
    MessageListener listener;

    /* contesto jms */
    private JMSContext context = null;
    /* per la ricezione di messaggi da destination */
    JMSConsumer messageConsumer = null;

    /* e' stato cancellato? */
    private boolean cancelled;

    /** Crea un nuovo SimpleAsynchConsumer, di nome n, per una destinazione di nome dn. */
    public SimpleAsynchConsumer(String n, Destination d, ConnectionFactory cf, MessageListener l) {

        this.name = n;
        this.destination = d;
        this.connectionFactory = cf;
        this.listener = l;

        this.cancelled = false;

    }

    /** Apre la connessione alla destinazione JMS. */
    public void connect() {
    	logger.info(this.toString() + ": Connecting...");
        context = connectionFactory.createContext();
    	messageConsumer = context.createConsumer(destination);
    	logger.info(this.toString() + ": Connected");
    }

    /** Si disconnette dalla destinazione JMS. */
    public void disconnect() {
        if (context != null) {
        	logger.info(this.toString() + ": Disconnecting...");
        	context.close();
        	context = null;
        	logger.info(this.toString() + ": Disconnected");
        }
    }

    /** Riceve (infiniti) messaggi dalla destinazione (sessione di ricezione). */
    public void receiveMessages() {
		messageConsumer.setMessageListener(this.listener);
        /* avvia la consegna di messaggi per la connessione */
		context.start();
		/* va avanti, fino a quando non viene fermato */
		while (!isCancelled()) {
			Sleeper.sleep(10);
		}
        /* e' stato fermato, arresta la consegna di messaggi per la connessione */
		context.stop();
    }

    /** Restituisce una descrizione di questo consumer. */
    public String toString() {
        return "SimpleAsynchConsumer[" +
                "name=" + name +
//                ", destination=" + destination.toString() +
                "]";

    }

    public void cancel() {
    	this.cancelled = true;
    }

    public boolean isCancelled() {
    	return this.cancelled;
    }

}
