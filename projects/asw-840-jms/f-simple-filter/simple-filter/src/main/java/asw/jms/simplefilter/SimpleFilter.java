package asw.jms.simplefilter;

import javax.jms.*;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;
import asw.jms.simpleasynchconsumer.SimpleAsynchConsumer;
import asw.jms.simplemessagefilter.TextMessageFilter;
import asw.jms.simplemessageprocessor.TextMessageProcessor;
import asw.jms.simpleproducer.SimpleProducer;
import asw.util.cancellable.Cancellable;
import asw.util.sleep.Sleeper;

/*
 * SimpleFilter.java
 *
 * Un SimpleFilter e' in grado di filtrare una sequenza di messaggi, 
 * ricevendo i messaggi (in modo asincrono) da una generica destinazione JMS (coda o argomento)
 * e inviando i messaggi a un'altra generica destinazione JMS (coda o argomento). 
 * 
 * Introduce un ritardo per simulare il tempo di elaborazione
 * di ciascun messaggio.
 *
 * Questo filter e' anche un processor di messaggi testuali,
 * poiche' implementa TextMessageProcessor.
 *
 * I messaggi possono essere inviati da un SimpleProducer (per es., su jms/asw/Queue).
 * I messaggi possono essere ricevuti da un SimpleXXConsumer (per es., su jms/asw/Topic).
 *
 * @author cabibbo
 */
public class SimpleFilter implements Cancellable, TextMessageProcessor {

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.jms.simplefilterr");

	/* nome di questo filter */
    private String name;
    /* sorgente di messaggi di questo filter */
    private Destination messageSource;
    /* destinazione dei messaggi di questo filter */
    private Destination messageDestination;
    /* connection factory di questo filter */
    private ConnectionFactory connectionFactory;

    /* consumatore associato a questo filter */
    private SimpleAsynchConsumer consumer; 
    /* produttore associato a questo filter */
    private SimpleProducer producer; 
    /* il filtro per elaborare i messaggi */
    TextMessageFilter messageFilter;

    /* ritardo introdotto nel filtraggio dei messaggi */ 
    private int maxDelay; 
    
    /* e' stato cancellato? */
    private boolean cancelled;

    /** Crea un nuovo SimpleFilter, di nome n, per leggere messaggi da una sorgente ms 
     *  e per inviarli a una destinazione md, 
     *  che inviera' i messaggi filtrati dal message filter mf. */
    public SimpleFilter(String n, Destination ms, Destination md, ConnectionFactory cf, TextMessageFilter mf, int maxDelay) {

        this.name = n;
        this.messageSource = ms;
        this.messageDestination = md;
        this.connectionFactory = cf;
    	this.messageFilter = mf;

        /* crea un consumatore da sorgenteMessaggi */
        this.consumer = new SimpleAsynchConsumer(
                           "Consumatore messaggi per " + name,
                           messageSource, connectionFactory, this, maxDelay);

        /* crea un produttore su destinazioneMessaggi */
        this.producer = new SimpleProducer( 
                           "Produttore messaggi per " + name,
                           messageDestination, connectionFactory);
    	
        this.cancelled = false;
        this.maxDelay = maxDelay; 

    }

    /** Si connette alle destinazioni JMS. */
	public void connect() {
		logger.info(this.toString() + ": Connecting...");
		producer.connect();
		consumer.connect();
		logger.info(this.toString() + ": Connected");
	}

    /** Si disconnette dalle destinazioni JMS. */
	public void disconnect() {
		logger.info(this.toString() + ": Disconnecting...");
		consumer.disconnect();
		producer.disconnect();
		logger.info(this.toString() + ": Disconnected");
	}

    /** Abilita il filtraggio dei messaggi. */
	public void filterMessages() {
		consumer.receiveMessages();
	}

    /**
     * Riceve e filtra un messaggio
     * (implementa TextMessageProcessor).
     */
	public void processMessage(String inMessage) {
		/* delega il filtraggio del messaggio ricevuto al suo message filter */
		logger.fine("SimpleFilter.processMessage(): received " + inMessage);
		String outMessage = messageFilter.filterMessage(inMessage);
		/* introduce un ritardo */
		Sleeper.randomSleep(maxDelay/2, maxDelay);		
		logger.fine("SimpleFilter.processMessage(): sending " + outMessage);
		/* trasmette il nuovo messaggio alla destinazione successiva */
		producer.sendMessage(outMessage);
	}

    /** Restituisce una descrizione di questo filter. */
    public String toString() {
        return "SimpleFilter[" +
                "name=" + name +
//                ", source=" + source.toString() +
//                ", destination=" + destination.toString() +
                "]";

    }

    public void cancel() {
    	this.cancelled = true;
    	this.consumer.cancel();
    }

    public boolean isCancelled() {
    	return this.cancelled;
    }

}
