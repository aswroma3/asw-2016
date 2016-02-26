package asw.jms.simplefilter;

import asw.jms.simpleasynchconsumer.SimpleAsynchConsumer;
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
 * @author Luca Cabibbo
 */
public class SimpleFilter implements Cancellable, TextMessageProcessor {

	/* logger */
	private static Logger logger = AswLogger.getInstance().getLogger("asw.jms.simplefilter");

	/* nome di questo filtro */
    private String name;
    /* la destinazione da cui questo filtro legge messaggi */
    private Destination sorgenteMessaggi;
    /* la destinazione a cui questo filtro invia messaggi */
    private Destination destinazioneMessaggi;
    /* connection factory di questo filtro */
    private ConnectionFactory connectionFactory;

    /* ritardo massimo tra messaggi */
    private int maxDelay;

    /* il consumatore di questo filtro */
    private SimpleAsynchConsumer consumer = null;
    /* il produttore di questo filtro */
    private SimpleProducer producer = null;

    /* numero di messaggi consumati finora */
    private int messagesReceived;
    /* e' stato cancellato? */
    private boolean cancelled;

    /*
	 * Crea un nuovo SimpleFilter di nome name
	 * che legge messaggi dalla destinazione sorgenteMessaggi
	 * e invia messaggi alla destinazione destinazioneMessaggi.
	 */
	public SimpleFilter(String name, Destination sorgenteMessaggi, Destination destinazioneMessaggi,
			ConnectionFactory connectionFactory, int maxDelay) {
		this.name = name;
    	this.sorgenteMessaggi = sorgenteMessaggi;
    	this.destinazioneMessaggi = destinazioneMessaggi;
    	this.connectionFactory = connectionFactory;
    	this.maxDelay = maxDelay;

    	/* crea un consumatore su sorgenteMessaggi: 
    	 * girera' messaggi a questo oggetto (this) */
    	this.consumer =
    			new SimpleAsynchConsumer("Consumatore messaggi per " + this.name,
    					this.sorgenteMessaggi, this.connectionFactory, this, 10);
        logger.info("Creato consumatore: " + consumer.toString());

        /* crea un produttore su destinazioneMessaggi */
    	this.producer = new SimpleProducer("Produttore messaggi per " + this.name,
    			this.destinazioneMessaggi, this.connectionFactory, 10);
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
    public void receiveMessages() {
    	this.consumer.receiveMessages();
    }

    /**
     * Gestione del messaggio ricevuto message.
     */
    public void processMessage(String message) {
		logger.info("SimpleFilter: Ricevuto messaggio: " + message);
		this.messagesReceived++;
        /* introduce un ritardo */
    	Sleeper.randomSleep(maxDelay/2,maxDelay);
		/* invia un messaggio alla destinazione */
		String messaggioDaInviare = message.toUpperCase() + " (filtered by " + name + ")";
		logger.info("SimpleFilter: Invio messaggio: " + messaggioDaInviare);
		producer.sendMessage(messaggioDaInviare);
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
//                ", reading from=" + sorgenteMessaggi.toString() +
//                ", writing to=" + destinazioneMessaggi.toString() +
                "]";
    }

}
