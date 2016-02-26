package asw.jms.simpleasynchconsumer;

import javax.jms.*;

import asw.jms.simplemessageprocessor.TextMessageProcessor;

import asw.util.cancellable.Cancellable;
import asw.util.sleep.Sleeper;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

/**
 * SimpleAsynchConsumer.java
 *
 * Un SimpleAsynchConsumer e' un endpoint per ricevere messaggi
 * da una generica destinazione JMS (coda o argomento).
 * La ricezione dei messaggi avviene secondo la modalita' asincrona.
 *
 * Introduce un ritardo per simulare il tempo di elaborazione
 * di ciascun messaggio.
 *
 * Questo consumer e' anche un listener di messaggi testuali JMS,
 * poiche' implementa MessageListener.
 *
 * I messaggi potranno essere inviati da SimpleProducer.
 *
 * Variante di un esempio nel tutorial per Java EE.
 *
 * @author Luca Cabibbo
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

    /* ritardo massimo tra messaggi */
    private int maxDelay;

    /* contesto jms */
    private JMSContext context = null;
    /* per la ricezione di messaggi da destination */
    JMSConsumer messageConsumer = null;

    /* numero di messaggi consumati finora */
    private int messagesReceived;

    /* e' stato cancellato? */
    private boolean cancelled;

    /**
     * Crea un nuovo SimpleAsynchConsumer, di nome n, per la destinazione d,
     * che inviera' i messaggi ricevuti al message processor mp.
     */
    public SimpleAsynchConsumer(String n, Destination d, ConnectionFactory cf, TextMessageProcessor mp, int maxDelay) {
        this.name = n;
        this.destination = d;
        this.connectionFactory = cf;
    	this.messageProcessor = mp;
    	this.maxDelay = maxDelay;

		this.messagesReceived = 0;
        this.cancelled = false;
    }

    public SimpleAsynchConsumer(String n, Destination d, ConnectionFactory cf, TextMessageProcessor mp) {
        this(n, d, cf, mp, 0);
    }


    /**
     * Apre la connessione alla destinazione JMS di questo consumer.
     */
    public void connect() {
    	logger.info(this.toString() + ": Connecting...");
        context = connectionFactory.createContext();
    	messageConsumer = context.createConsumer(destination);
    	logger.info(this.toString() + ": Connected");
    }

    /**
     * Si disconnette dalla destinazione JMS di questo consumer.
     */
    public void disconnect() {
        if (context != null) {
        	logger.info(this.toString() + ": Disconnecting...");
        	context.close();
        	context = null;
        	logger.info(this.toString() + ": Disconnected (" + messagesReceived + " message(s) received)");
        }
    }

    /**
     * Inizia la sessione di ricezione di messaggi dalla destinazione.
     */
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
		this.messagesReceived++;
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

    /**
     * Cancella questo consumer.
     */
    public void cancel() {
    	this.cancelled = true;
    }

    /**
     * Questo consumer e' stato cancellato?
     */
    public boolean isCancelled() {
    	return this.cancelled;
    }

    /**
     * Restituisce una descrizione di questo consumer.
     */
    public String toString() {
        return "SimpleAsynchConsumer[" +
                "name=" + name +
                ", maxDelay=" + maxDelay +
//                ", reading from=" + destination.toString() +
                "]";
    }

}
