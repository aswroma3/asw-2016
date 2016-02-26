package asw.jms.cancellablesynchconsumer;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

/**
 * CancellableSynchConsumerThread.java
 *
 * Un CancellableSynchConsumerThread riceve messaggi tramite un CancellableSynchConsumer.
 * Opera in un thread autonomo.
 * Quindi puo' operare in modo concorrente ad altri ConsumerThread.
 *
 * @author Luca Cabibbo
 */
public class CancellableSynchConsumerThread extends Thread {

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.jms.cancellablesynchconsumer");

	/** il consumer */
    private CancellableSynchConsumer simpleConsumer;

    /** nome del consumer */
    private String name;

    /**
     * Crea un nuovo CancellableSynchConsumerThread per il consumer c.
     */
    public CancellableSynchConsumerThread(CancellableSynchConsumer c, String name) {
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
        	simpleConsumer.receiveMessage();
        	/* se il consumatore e' stato cancellato, allora interrompe la ricezione di messaggi */
        	if (simpleConsumer.isCancelled())
        		break;
    	}
    	simpleConsumer.stop();
        simpleConsumer.disconnect();
    	logger.info("ConsumerThread: " + name + ": Done");
    }

}
