package asw.jms.simpleproducer;

import javax.jms.*;
import javax.annotation.Resource;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

import asw.util.sleep.Sleeper;

/**
 * Main.java
 *
 * Classe principale per l'application client SimpleProducer.
 *
 * Esecuzione: dal prompt dei comandi, dalla cartella del progetto, tramite appclient:
 * appclient -client build\SimpleProducer <destinazione (queue o topic)> <nomeProducer> <ritardo tra messaggi> <#messaggi>
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

	// private static final String BASE_PRODUCER_NAME = "SimpleProducer";
	private static final String BASE_PRODUCER_NAME = "SP";

	/* logger */
	private static Logger logger = AswLogger.getInstance().getLogger("asw.jms.simpleproducer");

	/**
     * Utilizzo nel caso in cui l'application client viene eseguito nell'host dell'AS:
     *     appclient -client SimpleProducer.jar [<queue|topic> [<client-name> [<delay> [<number_of_messages>] ] ] ]
     *
     * @param args the command line arguments:
     * @param args[0] il tipo di destinazione: "queue" o "topic" [opt] default = queue
     * @param args[1] il nome del producer [opt] default = nome casuale
     * @param args[2] ritardo massimo per la generazione dei messaggi, in ms [opt] default = 500
     * @param args[3] numero di messaggi inviati - 0 per inviare un solo messaggio vuoto (finale) [opt] default = 20
     */
    public static void main(String[] args) {
    	String                  producerName = null;
    	String destinationName = null;
    	Destination destination = null;
        int                     maxDelay;
        int                     numMsgs;


        /* accesso ed analisi dei parametri */

        /* valori di default */
    	destinationName = "queue";
    	producerName = BASE_PRODUCER_NAME + "[" + (int)(Math.random()*1000) + "]";
    	maxDelay = 500;
        numMsgs = 20;

    	/* prova a vedere i parametri specificati dall'utente */
        try {
        	destinationName = new String(args[0]);
        	producerName = new String(args[1]);
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
         * invia una sequenza di messaggi tramite un SimpleProducer
         */

        logger.info("simpleproducer.Main: " + producerName + " will send " + numMsgs + " messages to the " + destinationName);

        /* crea un producer */
        SimpleProducer simpleProducer = new SimpleProducer(producerName, destination, connectionFactory);
        logger.info("simpleproducer.Main: Creato producer: " + simpleProducer.toString());

        /* connessione */
        simpleProducer.connect();

        /* invio di nomMsgs messaggi */
        for (int i=1; i<=numMsgs; i++) {
        	simpleProducer.sendMessage("Message #" + i + " from " + producerName);
        	Sleeper.randomSleep(maxDelay/2,maxDelay);
        }

        /* se richiesto, infine invia un messaggio vuoto (usato qui per interrompere il consumer) */
        if (numMsgs==0) {
        	simpleProducer.sendMessage("");
        }

        /* disconnessione */
    	simpleProducer.disconnect();

    	logger.info("simpleproducer.Main: producer " + producerName + ": Done");
    	System.exit(0);
    }

}
