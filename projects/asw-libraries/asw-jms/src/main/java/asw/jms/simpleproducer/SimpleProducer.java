package asw.jms.simpleproducer;

import javax.jms.*;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

import asw.util.sleep.Sleeper;

/**
 * SimpleProducer.java
 *
 * Un SimpleProducer e' un endpoint per inviare messaggi
 * ad una certa destinazione JMS (coda o argomento).
 *
 * Introduce un breve ritardo dopo l'invio di ciascun messaggio.
 *
 * I messaggi sono ricevuti da oggetti SynchConsumer (in modo sincrono)
 * oppure da AsynchConsumer (in modo asincrono).
 *
 * Variante di un esempio nel tutorial per Java EE.
 *
 * @author Luca Cabibbo
 */
public class SimpleProducer {

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.jms.simpleproducer");

	/* nome di questo producer */
    private String name;
    /* destinazione di questo producer */
    private Destination destination;
    /* connection factory di questo producer */
    private ConnectionFactory connectionFactory;

    /* ritardo massimo tra messaggi */
    private int maxDelay;

    /* contesto jms */
    private JMSContext context = null;
    /* per l'invio di messaggi a destination */
    private JMSProducer messageProducer = null;

    /* numero di messaggi inviati */
    private int messagesSent;

    /**
     * Crea un nuovo SimpleProducer, di nome n, per una destinazione d.
     */
    public SimpleProducer(String n, Destination d, ConnectionFactory cf, int maxDelay) {
        this.name = n;
        this.destination = d;
        this.connectionFactory = cf;
        this.maxDelay = maxDelay;

        this.messagesSent = 0;
    }

    public SimpleProducer(String n, Destination d, ConnectionFactory cf) {
        this(n, d, cf, 0);
    }

    /**
     * Apre la connessione alla destinazione JMS di questo producer.
     */
    public void connect() {
    	logger.info(this.toString() + ": Connecting...");
        context = connectionFactory.createContext();
    	messageProducer = context.createProducer();
    	logger.info(this.toString() + ": Connected");
    }

    /**
     * Si disconnette dalla destinazione JMS di questo producer.
     */
    public void disconnect() {
        if (context != null) {
        	logger.info(this.toString() + ": Disconnecting...");
            context.close();
            context = null;
        	logger.info(this.toString() + ": Disconnected (" + messagesSent + " message(s) sent)");
        }
    }

    /**
     * Invia un messaggio di testo text alla destinazione di questo producer.
     */
    public void sendMessage(String text) {
        try {
        	TextMessage message = context.createTextMessage();
            message.setText(text);
            logger.info(this.name + ": Sending message: " + message.getText());
            messageProducer.send(destination, message);
            this.messagesSent++;
            /* introduce un ritardo */
        	Sleeper.randomSleep(maxDelay/2,maxDelay);
        } catch (JMSException e) {
        	logger.info(this.name + ": Error while sending message: " + e.toString());
        }
    }

    /**
     * Restituisce una descrizione di questo producer.
     */
    public String toString() {
        return "SimpleProducer[" +
                "name=" + name +
                ", maxDelay=" + maxDelay +
//                ", writing to=" + destination.toString() +
                "]";

    }

}
