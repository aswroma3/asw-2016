package asw.jms.simpleasynchconsumer;

import javax.jms.*;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

import asw.util.sleep.Sleeper;

/**
 * Un SimpleMessageProcessor e' un destinatario finale di messaggi JMS.
 * Gli puo' essere infatti delegata la gestione di un messaggio JMS
 * da parte di un consumatore asincrono.
 *
 * In pratica, implementa MessageListener.
 *
 * @author Luca Cabibbo
 */
public class SimpleMessageProcessor implements MessageListener {

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.jms.simplemessageprocessor");

	/* nome di questo processor */
    private String name;
    /* ritardo massimo tra messaggi */
    private int maxDelay;

    /* numero di messaggi consumati finora */
    private int messagesReceived;

    /**
     * Crea un nuovo SimpleMessageProcessor.
     */
    public SimpleMessageProcessor(String name, int maxDelay) {
		this.name = name;
		this.maxDelay = maxDelay;
		this.messagesReceived = 0;
    }

    /**
     * Riceve un messaggio JMS. (Implementa MessageListener.)
     */
    public void onMessage(Message m) {
    	/* ricevuto un messaggio, ne delega l'elaborazione
    	 * al metodo processMessage */
		logger.info("SimpleMessageProcessor.onMessage()");
    	if (m instanceof TextMessage) {
    		TextMessage  message = (TextMessage) m;
    		try {
    			this.processMessage(message.getText());
    		} catch (JMSException e) {
    			logger.info("SimpleMessageProcessor.onMessage(): JMSException: " + e.toString());
    		}
    	}
    }

	/**
	 * Elabora il messaggio di testo message.
	 */
	public void processMessage(String message) {
		this.messagesReceived++;
    	logger.info(this.name + ": Reading message #" + this.messagesReceived + ": " + message);
    	Sleeper.randomSleep(maxDelay/2,maxDelay);
	}


}
