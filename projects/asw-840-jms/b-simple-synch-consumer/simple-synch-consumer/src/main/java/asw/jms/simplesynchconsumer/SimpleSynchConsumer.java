package asw.jms.simplesynchconsumer;

import javax.jms.*;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

/*
 * SimpleSynchConsumer.java
 *
 * Un SimpleSynchConsumer e' in grado di ricevere una sequenza di messaggi
 * (in modo sincrono) da una generica destinazione JMS (coda o argomento).
 * Introduce un ritardo per simulare il tempo di elaborazione
 * di ciascun messaggio.
 *
 * I messaggi sono inviati da SimpleProducer.
 *
 * Variante di un esempio nel tutorial per Java EE.
 *
 * @author cabibbo
 */
public class SimpleSynchConsumer {

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.jms.simplesynchconsumer");

	/* nome di questo consumer */
    private String name;
    /* destinazione di questo consumer */
    private Destination destination;
    /* connection factory di questo consumer */
    private ConnectionFactory connectionFactory;
    /* numero di messaggi consumati finora */
    private int messagesReceived;

    /* contesto jms */
    private JMSContext context = null;
    /* per la ricezione di messaggi da destination */
    private JMSConsumer messageConsumer = null;


    /** Crea un nuovo SimpleSynchConsumer, di nome n, per una destinazione di nome dn. */
    public SimpleSynchConsumer(String n, Destination d, ConnectionFactory cf) {

        this.name = n;
        this.destination = d;
        this.connectionFactory = cf;
        this.messagesReceived = 0;

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
        	logger.info(this.toString() + ": Disconnected (" + messagesReceived + " message(s) received)");
        }
    }

    /** Avvia la ricezione dei messaggi */
    public void start() {
        /* avvia la ricezione di messaggi per la connessione */
    	logger.info(this.toString() + ": Starting message reception");
        context.start();
    }

    /** Arresta la ricezione dei messaggi */
    public void stop() {
        /* arresta la ricezione di messaggi per la connessione */
    	logger.info(this.toString() + ": Stopping message reception");
        context.stop();
    }


    /** Restituisce una descrizione di questo consumer. */
    public String toString() {
        return "SimpleSynchConsumer[" +
                "name=" + name +
//                ", destination=" + destination.toString() +
                "]";

    }


    /** Riceve un messaggio dalla destinazione */
    public String receiveMessage() {

        /* riceve un messaggio dalla sua destinazione */
        try {
        	// MessageConsumer messageConsumer = session.createConsumer(destination);
            /* (ri)avvia la consegna di messaggi per la connessione */
            // connection.start();
            /* riceve un messaggio */
            Message m = messageConsumer.receive();
            if (m instanceof TextMessage) {
            	TextMessage message = (TextMessage) m;
            	this.messagesReceived++;
                logger.info(this.name + ": Reading message #" + this.messagesReceived + ": " + message.getText());
                return message.getText();
            }
            /* arresta (temporaneamente) la ricezione di messaggi */
            // connection.stop();
        } catch (JMSException e) {
            logger.info(this.toString()+ ": Error while reading message: " + e.toString());
            System.exit(1);
        }
        return null;
    }

}
