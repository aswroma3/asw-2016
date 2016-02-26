package asw.jms.simplefilter;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

/**
 * SimpleFilterThread.java
 *
 * Un SimpleFilterThread riceve messaggi tramite un SimpleFilter.
 * Opera in un thread autonomo.
 * Quindi puo' operare in modo concorrente ad altri ConsumerThread.
 *
 * @author Luca Cabibbo
 */
public class SimpleFilterThread extends Thread {

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.jms.simplefilter");

	/** il filtro */
    private SimpleFilter simpleFilter;

    /** nome del filtro */
    private String name;

    /**
     * Crea un nuovo SimpleFilterThread per il filtro f.
     */
    public SimpleFilterThread(SimpleFilter f, String name) {
        super();
        this.simpleFilter = f;
        this.name = name;
    }

    /**
     * L'attivita' di questo thread.
     */
    public void run() {
		logger.info("FilterThread: " + name + " ready to filter messages");

    	simpleFilter.connect();

    	simpleFilter.receiveMessages();

    	simpleFilter.disconnect();
    	logger.info("FilterThread: " + name + ": Done");
    }

}
