package asw.jms.cancellablesynchconsumer;

import javax.jms.*;
import javax.annotation.Resource;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

import asw.util.cancellable.KeyboardKiller;
import asw.util.sleep.Sleeper;

/**
 * Main.java
 *
 * Classe principale per l'application client CancellableSynchProducer.
 *
 * Esecuzione: dal prompt dei comandi, dalla cartella del progetto, tramite appclient:
 * appclient -client build\SimpleSynchConsumer <destinazione (queue o topic)> <nomeConsumer> <ritardo tra messaggi> <#messaggi>
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

	// private static final String BASE_CONSUMER_NAME = "CancellableSynchConsumer";
	private static final String BASE_CONSUMER_NAME = "CSC";

	/* logger */
	private static Logger logger = AswLogger.getInstance().getLogger("asw.jms.cancellablesynchconsumer");

	/**
     * Utilizzo nel caso in cui l'application client viene eseguito nell'host dell'AS:
     *     appclient -client SimpleSynchConsumer.jar [<queue|topic> [<client-name> [<delay> [<number_of_messages>] ] ] ]
     *
     * @param args the command line arguments:
     * @param args[0] il tipo di destinazione: "queue" o "topic" [opt] default = queue
     * @param args[1] il nome del consumer [opt] default = nome casuale
     * @param args[2] ritardo massimo per il consumo dei messaggi, in ms [opt] default = 400
     * @param args[3] numero di messaggi ricevuti - 0 per infinito [opt] default = 0
     */
    public static void main(String[] args) {
    	String                  consumerName = null;
    	String destinationName = null;
    	Destination destination = null;
        int                     maxDelay;
        int                     numMsgs;

        /* accesso ed analisi dei parametri */

        /* valori di default */
    	destinationName = "queue";
    	consumerName = BASE_CONSUMER_NAME + "[" + (int)(Math.random()*1000) + "]";
    	maxDelay = 400;
        numMsgs = 0;  /* 0 vuol dire: ricevi messaggi all'infinito */

    	/* prova a vedere i parametri specificati dall'utente */
        try {
        	destinationName = new String(args[0]);
        	consumerName = new String(args[1]);
        	maxDelay = (new Integer(args[2])).intValue();
            numMsgs = (new Integer(args[3])).intValue();
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
         * riceve una sequenza di messaggi tramite un CancellableSynchConsumer
         */
        logger.info("cancellableconsumer.Main: " + consumerName + " ready to receive " + numMsgs + " messages from the " + destinationName);

        /* crea un consumer */
        CancellableSynchConsumer cancellableConsumer = new CancellableSynchConsumer(consumerName, destination, connectionFactory);
        logger.info("cancellableconsumer.Main: Creato consumer: " + cancellableConsumer.toString());

        /* la cancellazione del consumer avviene premendo il tasto INVIO dalla tastiera */

        KeyboardKiller k = new KeyboardKiller();
        k.add(cancellableConsumer);
        k.start();

        /* connessione */
    	cancellableConsumer.connect();
    	cancellableConsumer.start();

    	/* ricezione messaggi */
    	for (int i=0; i<numMsgs || numMsgs==0; i++) {
        	String message = cancellableConsumer.receiveMessage();
        	logger.info("ConsumerThread: " + consumerName + ": Received message: " + message);
        	Sleeper.randomSleep(maxDelay/2,maxDelay);
        	/* se il consumatore e' stato cancellato, allora interrompe la ricezione di messaggi */
        	if (cancellableConsumer.isCancelled()) {
        		break;
        	}
    	}

    	/* disconnessione */
    	cancellableConsumer.stop();
        cancellableConsumer.disconnect();

        logger.info("cancellableconsumer.Main: Consumer: " + consumerName + ": Done");
        System.exit(0);

    }

}
