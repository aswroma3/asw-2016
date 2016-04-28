package asw.jms.simplefilter;

import javax.jms.*;
import javax.annotation.Resource;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;
import asw.jms.simplemessagefilter.SimpleMessageFilter;
import asw.jms.simplemessagefilter.TextMessageFilter;
import asw.util.cancellable.KeyboardKiller;

/**
 * Main.java
 *
 * Classe principale per l'application client SimpleFilter.
 *
 * Esecuzione: dal prompt dei comandi, dalla cartella del progetto, tramite appclient:
 * appclient -client build\SimpleFilter <sorgente (queue o topic)> <destinazione (queue o topic)> <nomeConsumer> <ritardo tra messaggi>
 *
 * @author Luca Cabibbo
 */
public class Main {

	/* L'iniezione delle dipendenze puo' avvenire solo nella main class. */
	@Resource(lookup = "jms/asw/Queue")
    private static Queue queue;
    @Resource(lookup = "jms/asw/Topic")
    private static Topic topic;
    @Resource(lookup = "jms/asw/ConnectionFactory")
    private static ConnectionFactory connectionFactory;

	// private static final String BASE_CONSUMER_NAME = "SimpleAsynchConsumer";
	private static final String BASE_CONSUMER_NAME = "SAC";

	/* logger */
	private static Logger logger = AswLogger.getInstance().getLogger("asw.jms.simplefilter");

	/**
     * Utilizzo nel caso in cui l'application client viene eseguito nell'host dell'AS:
     *     appclient -client SimpleFilter.jar [<queue|topic> [<queue|topic> [<client-name> [<delay>] ] ] ]
     *
     * @param args the command line arguments:
     * @param args[0] la sorgente del filtro: "queue" o "topic" [opt] default = queue
     * @param args[1] la destinazione del filtro: "queue" o "topic" [opt] default = topic
     * @param args[2] il nome del consumer [opt] default = nome casuale
     * @param args[3] ritardo massimo per il consumo dei messaggi, in ms [opt] default = 400
     */
    public static void main(String[] args) {
    	String filterName = null;
    	String sourceName = null;
    	String destinationName = null;
    	Destination source = null;
    	Destination destination = null;
        int                     maxDelay;

        /* accesso ed analisi dei parametri */

        /* valori di default */
    	sourceName = "queue";
    	destinationName = "topic";
    	filterName = BASE_CONSUMER_NAME + "[" + (int)(Math.random()*1000) + "]";
    	maxDelay = 400;

    	/* prova a vedere i parametri specificati dall'utente */
        try {
        	sourceName = new String(args[0]);
        	destinationName = new String(args[1]);
        	filterName = new String(args[2]);
        	maxDelay = (new Integer(args[3])).intValue();
        } catch(Exception e) {
        	/* indice fuori dai limiti o altro errore (e.g., conversione) */
        	/* ok, ci sono i valori di default */
        }

        /* identifica il tipo di sorgente e di destinazione */
        if (sourceName.equals("queue")) {
            source = queue;
        } else if (sourceName.equals("topic")) {
        	source = topic;
        } else {
            System.out.println("Invalid source type");
            System.exit(1);
        }
        if (destinationName.equals("queue")) {
            destination = queue;
        } else if (destinationName.equals("topic")) {
        	destination = topic;
        } else {
            System.out.println("Invalid destination type");
            System.exit(1);
        }

        if (source==destination) {
            System.out.println("Source and destination should differ");
            System.exit(1);        	
        }
        

        /*
         * attivita' principali del programma:
         * riceve una sequenza di messaggi tramite un SimpleAsynchConsumer
         */
        logger.info("simplefilter.Main: " + filterName + " ready to receive messages from the " + sourceName);
        logger.info("simplefilter.Main: " + filterName + " ready to send messages to the " + destinationName);

        /* crea un message filter */
        TextMessageFilter messageFilter = new SimpleMessageFilter(filterName);
        /* crea il filtro */ 
        SimpleFilter simpleFilter = new SimpleFilter(filterName, source, destination, connectionFactory, messageFilter, maxDelay);
        logger.info("simplefilter.Main: Creato filter: " + simpleFilter.toString());

        /* la cancellazione del filtro avviene premendo il tasto INVIO dalla tastiera */

        KeyboardKiller k = new KeyboardKiller();
        k.add(simpleFilter);
        k.start();

        /* connessione */
    	simpleFilter.connect();

    	/* filtraggio messaggi */
    	simpleFilter.filterMessages();

    	/* disconnessione */
        simpleFilter.disconnect();

        logger.info("simplefilter.Main: Filter: " + filterName + ": Done");
        System.exit(0);

    }

}
