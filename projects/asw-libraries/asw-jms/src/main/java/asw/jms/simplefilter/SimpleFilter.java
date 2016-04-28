package asw.jms.simplefilter;

import asw.jms.simpleasynchconsumer.SimpleAsynchConsumer;
import asw.jms.simplemessagefilter.TextMessageFilter;
import asw.jms.simplemessageprocessor.TextMessageProcessor;
import asw.jms.simpleproducer.SimpleProducer;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import asw.util.cancellable.Cancellable;
import asw.util.sleep.Sleeper;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

/**
 * Un SimpleFilter e' un endpoint che legge messaggi da una destinazione JMS
 * e, in corrispondenza, invia messaggi a un'altra destinazione JMS.
 *
 * Il filtraggio dei messaggi viene effettuato da un TextMessageFilter. 
 * 
 * Questo filter e' anche un consumatore di messaggi testuali,
 * poiche' implementa TextMessageProcessor.
 *
 * Introduce un ritardo per simulare il tempo di elaborazione
 * di ciascun messaggio.
 *
 * I messaggi in ingresso possono essere inviati da un SimpleProducer.
 * I messaggi in uscita possono essere consumati da un SimpleXConsumer.
 *
 * @author Luca Cabibbo
 */
public class SimpleFilter implements Cancellable, TextMessageProcessor {

	/* logger */
	private static Logger logger = AswLogger.getInstance().getLogger("asw.jms.simplefilter");

	/* nome di questo filtro */
    private String name;
    /* la destinazione da cui questo filtro legge messaggi */
    private Destination messageSource;
    /* la destinazione a cui questo filtro invia messaggi */
    private Destination messageDestination;
    /* connection factory di questo filtro */
    private ConnectionFactory connectionFactory;

    /* ritardo massimo tra messaggi */
    private int maxDelay;

    /* il consumatore di questo filtro */
    private SimpleAsynchConsumer consumer;
    /* il produttore di questo filtro */
    private SimpleProducer producer;
    /* il filtro di messaggi per elaborare i messaggi */
    TextMessageFilter messageFilter;

    /* numero di messaggi ricevuti finora */
    private int messagesReceived;
    /* e' stato cancellato? */
    private boolean cancelled;

    /*
	 * Crea un nuovo SimpleFilter di nome name
	 * che legge messaggi dalla destinazione sorgenteMessaggi
	 * e invia messaggi alla destinazione destinazioneMessaggi, 
	 * che inviera' i messaggi filtrati dal message filter mf.
	 */
	public SimpleFilter(String name, Destination sorgenteMessaggi, Destination destinazioneMessaggi,
			ConnectionFactory connectionFactory, TextMessageFilter mf, int maxDelay) {
		this.name = name;
    	this.messageSource = sorgenteMessaggi;
    	this.messageDestination = destinazioneMessaggi;
    	this.connectionFactory = connectionFactory;
    	this.messageFilter = mf;

    	this.maxDelay = maxDelay;

    	/* crea un consumatore su sorgenteMessaggi: 
    	 * girera' messaggi a questo oggetto (this) */
    	this.consumer =
    			new SimpleAsynchConsumer("Consumatore messaggi per " + this.name,
    					this.messageSource, this.connectionFactory, this, 10);
        logger.info("Creato consumatore: " + consumer.toString());

        /* crea un produttore su destinazioneMessaggi */
    	this.producer = new SimpleProducer("Produttore messaggi per " + this.name,
    			this.messageDestination, this.connectionFactory, 10);
        logger.info("Creato produttore: " + producer.toString());

        this.messagesReceived = 0;
        this.cancelled = false;
	}

    /**
     * Apre le connessioni per le destinazioni JMS di questo filtro.
     */
    public void connect() {
        /* avvia il produttore */
        producer.connect();
        /* avvia il consumatore */
        consumer.connect();
    }

    /**
     * Si disconnette dalla destinazione JMS di questo consumer.
     */
    public void disconnect() {
        /* disconnette il consumatore */
        consumer.disconnect();
		/* disconnette il produttore */
		producer.disconnect();

		logger.info(this.toString() + ": Disconnected (" + messagesReceived + " message(s) received)");
    }

    /**
     * Inizia la sessione di ricezione di messaggi dalla destinazione.
     */
    public void filterMessages() {
    	this.consumer.receiveMessages();
    }

    /**
     * Riceve e filtra un messaggio
     * (implementa TextMessageProcessor).
     */
    public void processMessage(String inMessage) {
		logger.info("SimpleFilter: Ricevuto messaggio: " + inMessage);
		this.messagesReceived++;
		/* invia un messaggio alla destinazione */
		String outMessage = inMessage.toUpperCase() + " (filtered by " + name + ")";
        /* introduce un ritardo */
    	Sleeper.randomSleep(maxDelay/2,maxDelay);
		logger.info("SimpleFilter: Invio messaggio: " + outMessage);
		producer.sendMessage(outMessage);
    }

    /**
     * Cancella questo filtro.
     */
    public void cancel() {
    	this.cancelled = true;
    	/* cancella anche il suo consumatore */
    	this.consumer.cancel();
    }

    /**
     * Questo filtro e' stato cancellato?
     */
    public boolean isCancelled() {
    	return this.cancelled;
    }

    /**
     * Restituisce una descrizione di questo filtro.
     */
    public String toString() {
        return "SimpleFilter[" +
                "name=" + name +
                ", maxDelay=" + maxDelay +
//                ", reading from=" + messageSource.toString() +
//                ", writing to=" + messageDestination.toString() +
                "]";
    }

}
