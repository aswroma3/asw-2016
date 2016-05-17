package asw.ejb.messagefilter.consumer;

import javax.jms.*;

import asw.jms.simplemessageprocessor.TextMessageProcessor;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

/*
 * Ascoltatore di messaggi.
 * Implementa MessageListener.
 * Visualizza il messaggio ricevuto.
 */
public class ConsumatoreMessageProcessor implements TextMessageProcessor {

	/* logger */
	private static Logger logger = AswLogger.getInstance().getLogger("asw.ejb.messagefilter.consumer");

	/* numero di messaggi ricevuti */
	private int messaggiRicevuti;

	/**
	 * Crea un nuovo ConsumatoreMessageProcessor.
	 */
	public ConsumatoreMessageProcessor() {
		this.messaggiRicevuti = 0;
	}

	/*
	 * Gestisce la ricezione di un messaggio:
	 * visualizza il messaggio ricevuto.
	 */
	public void processMessage(String message) {
		/* conta il messaggio ricevuto */
		this.messaggiRicevuti++;
		/* visualizza il messaggio */
		logger.info("MessageProcessor: Ricevuto messaggio # " + messaggiRicevuti + ": "+ message);
	}

}
