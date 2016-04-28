package asw.jms.simplemessagefilter;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

/**
 * SimpleMessageFilter.java
 *
 * Un SimpleMessageFilter definisce come filtrare dei messaggi testuali 
 * (nell'ipotesi semplificativa che a ciascun messaggio in entrata debba corrispondere 
 * esattamente un messaggio in uscita. 
 *
 * I messaggi vengono ricevuti tramite un SimpleFilter,
 * ma l'elaborazione di ciascun messaggio viene invece fatta
 * da un SimpleMessageFilter.
 *
 * @author Luca Cabibbo
 */
public class SimpleMessageFilter implements TextMessageFilter {

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.jms.simplemessagefilter");

	/* nome di questo filter */
    private String name;

    /* numero di messaggi filtrati finora */
    private int messagesReceived;

    /**
	 * Crea un nuovo SimpleMessageFilter.
	 */
	public SimpleMessageFilter(String name) {
		this.name = name;
		this.messagesReceived = 0;
	}

	/**
	 * Filtra il messaggio di testo inMessage.
	 */
	public String filterMessage(String inMessage) {
		this.messagesReceived++;
		String outMessage = "* " + inMessage + " * (filtered by " + name + ")"; 
    	logger.info(this.name + ": Reading message #" + this.messagesReceived + ": " + inMessage);
    	logger.info(this.name + ": Sending message #" + this.messagesReceived + ": " + outMessage);
    	return outMessage; 
	}

}
