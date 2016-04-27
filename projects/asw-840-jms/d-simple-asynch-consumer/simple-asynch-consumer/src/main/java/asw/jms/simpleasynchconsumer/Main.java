package asw.jms.simpleasynchconsumer;

import javax.jms.*;
import javax.annotation.Resource;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

import asw.util.cancellable.KeyboardKiller;

/**
 * Main.java
 *
 * Classe principale per l'application client SimpleAsynchProducer.
 *
 * Esecuzione: dal prompt dei comandi, dalla cartella del progetto, tramite appclient:
 * appclient -client build\SimpleAsynchConsumer <destinazione (queue o topic)> <nomeConsumer> <ritardo tra messaggi>
 *
 * Variante di un esempio nel tutorial per Java EE.
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
	private static Logger logger = AswLogger.getInstance().getLogger("asw.jms.simpleasynchconsumer");

	/**
     * Utilizzo nel caso in cui l'application client viene eseguito nell'host dell'AS:
     *     appclient -client SimpleAsynchConsumer.jar [<queue|topic> [<client-name> [<delay>] ] ]
     *
     * @param args the command line arguments:
     * @param args[0] il tipo di destinazione: "queue" o "topic" [opt] default = queue
     * @param args[1] il nome del consumer [opt] default = nome casuale
     * @param args[2] ritardo massimo per il consumo dei messaggi, in ms [opt] default = 400
     */
    public static void main(String[] args) {
    	String                  consumerName = null;
    	String destinationName = null;
    	Destination destination = null;
        int                     maxDelay;

        /* accesso ed analisi dei parametri */

        /* valori di default */
    	destinationName = "queue";
    	consumerName = BASE_CONSUMER_NAME + "[" + (int)(Math.random()*1000) + "]";
    	maxDelay = 400;

    	/* prova a vedere i parametri specificati dall'utente */
        try {
        	destinationName = new String(args[0]);
        	consumerName = new String(args[1]);
        	maxDelay = (new Integer(args[2])).intValue();
        } catch(Exception e) {
        	/* indice fuori dai limiti o altro errore (e.g., conversione) */
        	/* ok, ci sono i valori di default */
        }

        /* identifica il tipo di destinazione */
        if (destinationName.equals("queue")) {
            destination = queue;
        } else if (destinationName.equals("topic")) {
        	destination = topic;
        } else {
            System.out.println("Invalid destination type");
            System.exit(1);
        }

        /*
         * attivita' principali del programma:
         * riceve una sequenza di messaggi tramite un SimpleAsynchConsumer
         */
        logger.info("asynchconsumer.Main: " + consumerName + " ready to receive messages from the " + destinationName);

        /* crea un consumer con il suo listener */
        MessageListener listener = new SimpleMessageProcessor(consumerName, maxDelay);
        SimpleAsynchConsumer asynchConsumer = new SimpleAsynchConsumer(consumerName, destination, connectionFactory, listener);
        logger.info("asynchconsumer.Main: Creato consumer: " + asynchConsumer.toString());

        /* la cancellazione del consumer avviene premendo il tasto INVIO dalla tastiera */

        KeyboardKiller k = new KeyboardKiller();
        k.add(asynchConsumer);
        k.start();

        /* connessione */
    	asynchConsumer.connect();

    	/* ricezione messaggi */
    	asynchConsumer.receiveMessages();

    	/* disconnessione */
        asynchConsumer.disconnect();

        logger.info("asynchconsumer.Main: Consumer: " + consumerName + ": Done");
        System.exit(0);

    }

}
