package asw.jms.simpleasynchconsumer;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

/**
 * SimpleAsynchConsumerThread.java
 *
 * Un SimpleAsynchConsumerThread riceve messaggi tramite un SimpleAsynchConsumer.
 * Opera in un thread autonomo.
 * Quindi puo' operare in modo concorrente ad altri ConsumerThread.
 *
 * @author Luca Cabibbo
 */
public class SimpleAsynchConsumerThread extends Thread {

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.jms.simpleasynchconsumer");

	/** il consumer */
    private SimpleAsynchConsumer simpleConsumer;

    /** nome del consumer */
    private String name;

    /**
     * Crea un nuovo SimpleAsynchConsumerThread per il consumer c.
     */
    public SimpleAsynchConsumerThread(SimpleAsynchConsumer c, String name) {
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

    	simpleConsumer.receiveMessages();

        simpleConsumer.disconnect();
    	logger.info("ConsumerThread: " + name + ": Done");

    }

}
