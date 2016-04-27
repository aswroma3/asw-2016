package asw.jms.cancellablesynchconsumer;

import javax.jms.*;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

import asw.util.cancellable.Cancellable;
import asw.util.sleep.Sleeper;

/**
 * Un CancellableSynchConsumer e' in grado di ricevere una sequenza di messaggi
 * (in modo sincrono) da una generica destinazione JMS (coda o argomento).
 * Introduce un ritardo per simulare il tempo di elaborazione
 * di ciascun messaggio.
 *
 * Puo' essere interrotto da un KeyboardKiller.
 *
 * I messaggi sono inviati da SimpleProducer.
 *
 * @author Luca Cabibbo
 */
public class CancellableSynchConsumer implements Cancellable {

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.jms.cancellablesynchconsumer");

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

    /* e' stato cancellato? */
    private boolean cancelled;


    /** Crea un nuovo CancellableSynchConsumer, di nome n, per una destinazione di nome dn. */
    public CancellableSynchConsumer(String n, Destination d, ConnectionFactory cf) {

        this.name = n;
        this.destination = d;
        this.connectionFactory = cf;
        this.messagesReceived = 0;
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
        return "CancellableSynchConsumer[" +
                "name=" + name +
//                ", destination=" + destination.toString() +
                "]";

    }

    /** Riceve un messaggio dalla destinazione
     * Se nel frattempo viene cancellato, restituisce null. */
    public String receiveMessage() {

        /* riceve un messaggio dalla sua destinazione */
        Message m = null;
        try {
            while (m==null && ! isCancelled()) {
            	m = messageConsumer.receiveNoWait();
            	Sleeper.sleep(10);
            }
            if (m instanceof TextMessage) {
            	TextMessage message = (TextMessage) m;
            	this.messagesReceived++;
                logger.info(this.name + ": Reading message #" + this.messagesReceived + ": " + message.getText());
                return message.getText();
            }
        } catch (JMSException e) {
            logger.info(this.toString()+ ": Error while reading message: " + e.toString());
        }
        return null;
    }

    public void cancel() {
    	this.cancelled = true;
    }

    public boolean isCancelled() {
    	return this.cancelled;
    }

}
