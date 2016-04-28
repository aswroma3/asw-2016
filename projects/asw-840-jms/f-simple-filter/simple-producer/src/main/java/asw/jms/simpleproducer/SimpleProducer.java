package asw.jms.simpleproducer;

import javax.jms.*;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

/**
 * Un SimpleProducer e' in grado di inviare una sequenza di messaggi
 * ad una generica destinazione JMS (coda o argomento),
 * intervallati tra loro da un ritardo.
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

    /* contesto jms */
    private JMSContext context = null;
    /* per l'invio di messaggi a destination */
    private JMSProducer messageProducer = null;

    /* numero di messaggi inviati */
    private int messagesSent;

    /** Crea un nuovo SimpleProducer, di nome n, per una destinazione di nome dn. */
    public SimpleProducer(String n, Destination d, ConnectionFactory cf) {

        this.name = n;
        this.destination = d;
        this.connectionFactory = cf;
        this.messagesSent = 0;
    }

    /** Apre la connessione alla destinazione JMS. */
    public void connect() {
    	logger.info(this.toString() + ": Connecting...");
        context = connectionFactory.createContext();
    	messageProducer = context.createProducer();
    	logger.info(this.toString() + ": Connected");
    }

    /** Si disconnette dalla destinazione JMS. */
    public void disconnect() {
        if (context != null) {
        	logger.info(this.toString() + ": Disconnecting...");
            context.close();
            context = null;
        	logger.info(this.toString() + ": Disconnected (" + messagesSent + " message(s) sent)");
        }
    }

    /** Invia un messaggio di testo text alla destinazione */
    public void sendMessage(String text) {
        try {
        	TextMessage message = context.createTextMessage();
            message.setText(text);
            logger.info(this.name + ": Sending message: " + message.getText());
            messageProducer.send(destination, message);
            this.messagesSent++;
        } catch (JMSException e) {
        	logger.info(this.name + ": Error while sending message: " + e.toString());
        }
    }

    /** Restituisce una descrizione di questo producer. */
    public String toString() {
        return "SimpleProducer[" +
                "name=" + name +
//                ", destination=" + destination.toString() +
                "]";

    }

}
