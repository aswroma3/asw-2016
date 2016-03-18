package asw.jms.simpleproducer;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

/**
 * SimpleProducerThread.java
 *
 * Un ProducerThread genera l'invio di un certo numero di messaggi a una certa destinazione,
 * tramite un SimpleProducer.
 * Opera in un thread autonomo.
 * Quindi puo' operare in modo concorrente ad altri ProducerThread.
 *
 * @author Luca Cabibbo
 */
public class SimpleProducerThread extends Thread {

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.jms.simpleproducer");

	/** il producer */
    private SimpleProducer simpleProducer;

    /** nome del thread */
    private String name;

    /** numero di messaggi da spedire */
    private int numMsgs;

    /**
     * Crea un nuovo SimpleProducerThread associato al producer p,
     * per inviare numMsgs messaggi.
     */
    public SimpleProducerThread(SimpleProducer p, String name, int numMsgs) {
        super();
        this.simpleProducer = p;
        this.name = name;
        this.numMsgs = numMsgs;
    }

    /**
     * L'attivita' di questo thread.
     */
    public void run() {
    	logger.info("SimpleProducerThread: " + name + ": I will send " + numMsgs + " messages");
    	simpleProducer.connect();
    	/* se numMsgs vale 0, invia comunque un messaggio vuoto
    	 * (utile per interrompere un consumatore) */
        if (numMsgs==0) {
        	simpleProducer.sendMessage("");
        } else {
            for (int i=1; i<=numMsgs; i++) {
            	simpleProducer.sendMessage("Message #" + i + " from " + name);
            }
        }
        simpleProducer.disconnect();
    	logger.info("SimpleProducerThread: " + name + ": Done");
    }

}
