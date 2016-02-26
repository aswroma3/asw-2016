package asw.jms.simplesynchconsumer;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

/**
 * SimpleSynchConsumerThread.java
 *
 * Un SimpleSynchConsumerThread riceve una sequenza di messaggi tramite un SimpleSynchConsumer.
 * Opera in un thread autonomo.
 * Quindi puo' operare in modo concorrente ad altri ConsumerThread.
 *
 * @author Luca Cabibbo
 */
public class SimpleSynchConsumerThread extends Thread {

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.jms.simplesynchconsumer");

	/** il consumer */
    private SimpleSynchConsumer simpleConsumer;

    /** nome del consumer */
    private String name;

    /**
     * Crea un nuovo SimpleSynchConsumerThread per il consumer c.
     */
    public SimpleSynchConsumerThread(SimpleSynchConsumer c, String name) {
        super();
        this.simpleConsumer = c;
        this.name = name;
    }

    /**
     * L'attivita' di questo thread.
     */
    public void run() {
		logger.info("ConsumerThread: " + name + " ready to receive messages");
    	simpleConsumer.connect();
    	simpleConsumer.start();
    	while (true) {
        	String message = simpleConsumer.receiveMessage();
        	/* se e' arrivato un messaggio vuoto, 
        	 * allora interrompe la ricezione di messaggi */
        	if (message==null || message.length()==0)
        		break;
    	}
    	simpleConsumer.stop();
        simpleConsumer.disconnect();
    	logger.info("ConsumerThread: " + name + ": Done");
    }

}
