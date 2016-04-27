package asw.jms.messagebrowser;

import javax.jms.*;

import java.util.Enumeration;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

/*
 * Un MessageBrowser consente di esaminare i messaggi di una coda JMS
 * che non sono stati ancora consumati.
 *
 * I messaggi esaminati dal browser NON sono consumati,
 * quindi potranno essere successivamente consumati da un consumetore.

 * @author Luca Cabibbo
 */
public class MessageBrowser {

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.jms.messagebrowser");

    /* destinazione di questo browser */
    private Queue destination;
    /* connection factory di questo browser */
    private ConnectionFactory connectionFactory;

    /* contesto jms */
    private JMSContext context = null;
    /* per la ricezione di messaggi da destination */
    private QueueBrowser browser = null;


    /** Crea un nuovo MessageBrowser, per una destinazione di nome dn. */
    public MessageBrowser(Queue queue, ConnectionFactory cf) {

        this.destination = queue;
        this.connectionFactory = cf;

    }

    /** Apre la connessione alla destinazione JMS. */
    public void connect() {
    	logger.info(this.toString() + ": Connecting...");
        context = connectionFactory.createContext();
        browser = context.createBrowser(destination);
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


    /** Restituisce una descrizione di questo consumer. */
    public String toString() {
        return "MessageBrowser";

    }


    /** Legge un messaggio dalla destinazione */
    public void showMessages() {

    	/* itera sui messaggi nella coda */
        try {
        	Enumeration msgs = browser.getEnumeration();
        	if ( !msgs.hasMoreElements() ) {
        	    logger.info("MessageBrowser: No messages in queue");
        	} else {
        	    while (msgs.hasMoreElements()) {
        	        Message tempMsg = (Message)msgs.nextElement();
        	        logger.info("MessageBrowser: " + tempMsg.toString());
        	    }
        	}
        } catch (JMSException e) {
            logger.info(this.toString()+ ": Error while reading message: " + e.toString());
            System.exit(1);
        }
    }

}
