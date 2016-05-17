package asw.ejb.messagefilter.filter;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

import javax.jms.*;

import asw.jms.simpleproducer.SimpleProducer;

/**
 * Message-Driven Bean implementation class for: FiltroMessaggi
 *
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destinationType",
				propertyValue = "javax.jms.Queue"
		) },
		mappedName = "jms/asw/ejb/QueueOne")
public class FiltroMessaggi implements MessageListener {

	@Resource(lookup = "jms/asw/ejb/QueueTwo")
	/* la destinazione a cui inviare i messaggi filtrati */
    private Queue queueTwo;
    @Resource(lookup = "jms/asw/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    /* produttore di messaggi associato a questo message-driven bean */
    private SimpleProducer simpleProducer;

    /* nome di questo Filtro */
    private String name;

	/* numero di messaggi ricevuti */
	private int messaggiRicevuti;

	/* logger */
	private Logger logger;

	/** Default constructor. */
    // public FiltroMessaggi() { }

	/* crea un produttori di messaggi per il MDB
	 * ed apre la connessione JMS */
	@PostConstruct
	public void initialize() {
		this.logger = Logger.getLogger("asw.ejb.messagefilter.filter");
		this.name = "Filtro[" + (int)(Math.random()*1000) + "]";
		this.simpleProducer = new SimpleProducer("Produttore per " + name, queueTwo, connectionFactory);
		logger.info("Creato producer per " + name);
        simpleProducer.connect();
		logger.info("Connessione aperta per " + name);
		this.messaggiRicevuti = 0;
	}

	/* chiude la connessione JMS */
	@PreDestroy
	public void destroy() {
        simpleProducer.disconnect();
		logger.info("Connessione chiusa per " + name);
	}

	/**
     * Invocato quando viene ricevuto un messaggio dalla coda QueueOne.
     * Lo modifica (aggiungendo il nome del filtro che lo ha elaborato) e lo invia a QueueTwo.
     */
    public void onMessage(Message m) {
    	if (m instanceof TextMessage) {
    		TextMessage message = (TextMessage) m;
    		try {
    			/* gestisce la ricezione del messaggio message */
    			String text = message.getText();
    	    	/* delega la vera gestione del messaggio
    	    	 * al metodo processMessage */
    	    	this.processMessage(text);
    		} catch (JMSException e) {
    			logger.info("Filtro.onMessage(): " + e.toString());
    		}
    	}
    }

	/*
	 * Gestisce la ricezione di un messaggio:
	 * lo trasforma e lo invia alla coda queueTwo mediante il proprio producer.
	 */
	private void processMessage(String message) {
		/* conta il messaggio ricevuto */
		this.messaggiRicevuti++;
		/* visualizza il messaggi */
		logger.info("FiltroMessaggi: Ricevuto messaggio # " + messaggiRicevuti + ": "+ message);
		/* lo trasforma in maiuscolo e lo invia alla coda queueTwo */
		String outgoingMessage = message.toUpperCase() + " (filtered by " + this.name + ")";
		logger.info("FiltroMessaggi: Invio messaggio # " + messaggiRicevuti + ": "+ outgoingMessage);
		this.simpleProducer.sendMessage( outgoingMessage );
	}

}
