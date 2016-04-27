package asw.jms.simplesynchconsumer;

import javax.jms.*;
import javax.annotation.Resource;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

import asw.util.sleep.Sleeper;

/**
 * Main.java
 *
 * Classe principale per l'application client SimpleSynchProducer.
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

	// private static final String BASE_CONSUMER_NAME = "SimpleSynchConsumer";
	private static final String BASE_CONSUMER_NAME = "SSC";

	/* logger */
	private static Logger logger = AswLogger.getInstance().getLogger("asw.jms.simplesynchconsumer");

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
         * riceve una sequenza di messaggi tramite un SimpleSynchConsumer
         */
        logger.info("simpleconsumer.Main: " + consumerName + " ready to receive " + numMsgs + " messages from the " + destinationName);

        /* crea un consumer */
        SimpleSynchConsumer simpleConsumer = new SimpleSynchConsumer(consumerName, destination, connectionFactory);
        logger.info("simpleconsumer.Main: Creato consumer: " + simpleConsumer.toString());

        /* connessione */
    	simpleConsumer.connect();
    	simpleConsumer.start();

    	/* ricezione messaggi */
    	for (int i=0; i<numMsgs || numMsgs==0; i++) {
        	String message = simpleConsumer.receiveMessage();
        	Sleeper.randomSleep(maxDelay/2,maxDelay);
        	/* se e' arrivato un messaggio vuoto, allora interrompe la ricezione di messaggi */
        	if (message==null || message.length()==0) {
        		break;
        	}
    	}

    	/* disconnessione */
    	simpleConsumer.stop();
        simpleConsumer.disconnect();

        logger.info("simpleconsumer.Main: Consumer: " + consumerName + ": Done");
        System.exit(0);
    }

}
