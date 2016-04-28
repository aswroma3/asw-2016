package asw.jms.simpleasynchconsumer;

import javax.jms.*;

import asw.jms.simplemessageprocessor.TextMessageProcessor;

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
 * Questo consumer e' anche un listener di messaggi testuali JMS,
 * poiche' implementa MessageListener.
 *
 * I messaggi sono inviati da SimpleProducer.
 *
 * Variante di un esempio nel tutorial per Java EE.
 *
 * @author cabibbo
 */
public class SimpleAsynchConsumer implements Cancellable, MessageListener {

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.jms.simpleasynchconsumer");

	/* nome di questo consumer */
    private String name;
    /* destinazione di questo consumer */
    private Destination destination;
    /* connection factory di questo consumer */
    private ConnectionFactory connectionFactory;

    /* il destinatario finale dei messaggi */
    TextMessageProcessor messageProcessor;

    /* contesto jms */
    private JMSContext context = null;
    /* per la ricezione di messaggi da destination */
    private JMSConsumer messageConsumer = null;

    /* ritardo introdotto nella ricezione dei messaggi */ 
    private int maxDelay; 
    
    /* e' stato cancellato? */
    private boolean cancelled;

    /** Crea un nuovo SimpleAsynchConsumer, di nome n, per una destinazione di nome dn.
     * che inviera' i messaggi ricevuti al message processor mp. */
    public SimpleAsynchConsumer(String n, Destination d, ConnectionFactory cf, TextMessageProcessor mp, int maxDelay) {

        this.name = n;
        this.destination = d;
        this.connectionFactory = cf;
    	this.messageProcessor = mp;

        this.cancelled = false;
        this.maxDelay = maxDelay; 

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
    	/* registra se stesso come ascoltatore dei messaggi per questo consumer */
		messageConsumer.setMessageListener(this);
        /* avvia la consegna di messaggi per la connessione */
		context.start();
		/* va avanti, fino a quando non viene fermato */
		while (!isCancelled()) {
			Sleeper.sleep(10);
		}
        /* e' stato fermato, arresta la consegna di messaggi per la connessione */
		context.stop();
    }

    /**
     * Gestisce la ricezione di un messaggio JMS m
     * (implementa MessageListener).
     */
    public synchronized void onMessage(Message m) {
    	/* ricevuto un messaggio, ne delega l'elaborazione
    	 * al message processor associato */
		logger.fine("SimpleAsynchConsumer.onMessage()");
    	if (m instanceof TextMessage) {
    		TextMessage  message = (TextMessage) m;
    		try {
    			String text = message.getText();
    			/* delega la ricezione del messaggio al message processor
    			 * associato a questo consumer */
    			this.messageProcessor.processMessage(text);
    			/* introduce un ritardo */
    			Sleeper.randomSleep(maxDelay/2, maxDelay);
    		} catch (JMSException e) {
    			logger.info("SimpleAsynchConsumer.onMessage(): JMSException: " + e.toString());
    		}
    	}
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
