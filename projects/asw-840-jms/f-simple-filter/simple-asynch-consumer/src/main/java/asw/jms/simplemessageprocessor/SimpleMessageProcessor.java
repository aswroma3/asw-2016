package asw.jms.simplemessageprocessor;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

/**
 * SimpleMessageProcessor.java
 *
 * Un SimpleMessageProcessor e' un consumatore finale di messaggi
 * che vengono ricevuti in modo asincrono.
 *
 * I messaggi vengono ricevuti tramite un SimpleAsynchConsumer,
 * ma l'elaborazione di ciascun messaggio viene invece fatta
 * da un SimpleMessageProcessor.
 *
 * @author Luca Cabibbo
 */
public class SimpleMessageProcessor implements TextMessageProcessor {

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.jms.simplemessageprocessor");

	/* nome di questo processor */
    private String name;

    /* numero di messaggi consumati finora */
    private int messagesReceived;

    /**
	 * Crea un nuovo SimpleMessageProcessor.
	 */
	public SimpleMessageProcessor(String name) {
		this.name = name;
		this.messagesReceived = 0;
	}

	/**
	 * Elabora il messaggio di testo message.
	 */
	public void processMessage(String message) {
		this.messagesReceived++;
    	logger.info(this.name + ": Reading message #" + this.messagesReceived + ": " + message);
	}

}
