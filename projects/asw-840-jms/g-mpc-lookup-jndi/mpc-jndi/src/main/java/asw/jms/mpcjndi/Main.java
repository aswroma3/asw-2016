package asw.jms.mpcjndi;

import javax.jms.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import asw.jms.cancellablesynchconsumer.CancellableSynchConsumer;
import asw.jms.cancellablesynchconsumer.CancellableSynchConsumerThread;
import asw.jms.simpleasynchconsumer.*;
import asw.jms.simplefilter.SimpleFilter;
import asw.jms.simplefilter.SimpleFilterThread;
import asw.jms.simplemessageprocessor.SimpleMessageProcessor;
import asw.jms.simplemessageprocessor.TextMessageProcessor;
import asw.jms.simpleproducer.*;

import asw.util.cancellable.KeyboardKiller;
import java.util.logging.Logger;
import asw.util.logging.AswLogger;
import asw.util.sleep.Sleeper;

/**
 * Main.java
 *
 * Avvia piu' produttori e piu' consumatori, in thread separati.
 * Usa il seguente scenario (che e' fissato dal codice di questa applicazione)
 *
 * Producer ALPHA --                     --  Consumer GAMMA1
 *                  \--\             /--/
 *                      |-- topic --|                                        -- Consumer GAMMA2
 *                  /--/             \--\                                /--/
 * Producer BETA  --                     --   Filter PHI    --  queue --|
 *                                                                       \--\
 *                                                                           -- Consumer GAMMA3
 *
 * Due produttori verso il topic T.
 * Al topic T sono registrati un consumatore sincrono e un filtro, che invia messaggi alla coda C.
 * Alla coda C sono registrati due consumatori (uno sincrono uno asincrono).
 *
 * @author Luca Cabibbo
 */
public class Main {

	/* In questo esempio, niente iniezione delle dipendenze
	 * Le risorse JMS sono acquisite tramite JNDI. */
    private static Queue queue;
    private static Topic topic;
    private static ConnectionFactory connectionFactory;

	private static final String QUEUE_NAME = "jms/asw/Queue";
	private static final String TOPIC_NAME = "jms/asw/Topic";
	private static final String CONNECTION_FACTORY_NAME = "jms/asw/ConnectionFactory";

	/* logger */
	private static Logger logger = AswLogger.getInstance().getLogger("asw.jms.mpcjndi");

    /*
     * Effettua il lookup delle risorse JMS.
     */
    private static void lookupResources() {
    	logger.info("MPC-JNDI: Lookup delle risorse JNDI");
    	try {
    	    /* crea un initial context */
    	    Context context = new InitialContext();
    	    /* effettua il lookup delle risorse cercate */
        	logger.info("MPC-JNDI: Lookup di " + QUEUE_NAME);
    	    queue = (Queue) context.lookup(QUEUE_NAME);
        	logger.info("MPC-JNDI: Lookup di " + TOPIC_NAME);
    	    topic = (Topic) context.lookup(TOPIC_NAME);
        	logger.info("MPC-JNDI: Lookup di " + CONNECTION_FACTORY_NAME);
    	    connectionFactory = (ConnectionFactory) context.lookup(CONNECTION_FACTORY_NAME);
        	logger.info("MPC-JNDI: Lookup riuscito");
    	} catch (NamingException e) {
        	logger.info("MPC-JNDI: Lookup fallito");
        	e.printStackTrace();
        	System.exit(1);
    	}
    }

	/**
     * Utilizzo nel caso in cui l'application client viene eseguito nell'host dell'AS:
     *     appclient -client MultipleProducersConsumers.jar [<number_of_messages>]
     *
     * @param args the command line arguments:
     * @param args[0] il numero totale di messaggi inviato dai produttori [opt] default = 20
     */
    public static void main(String[] args) {

		/* lookup delle risorse JMS */
		lookupResources();

    	int numeroTotaleMessaggi;

    	/* accesso ed analisi dei parametri */
        try {
        	numeroTotaleMessaggi = Integer.parseInt(args[0]);
        } catch(Exception e) {
        	/* indice fuori dai limiti oppure non e' un numero */
        	numeroTotaleMessaggi = 20;
        }

        /* crea due producer sul topic T */
        SimpleProducer p1 = new SimpleProducer("Producer ALPHA", topic, connectionFactory, 300);
        logger.info("Creato producer: " + p1.toString());
        SimpleProducer p2 = new SimpleProducer("Producer BETA", topic, connectionFactory, 800);
        logger.info("Creato producer: " + p2.toString());

        /* crea un consumatore sincrono sul topic T */
        CancellableSynchConsumer cs1 = new CancellableSynchConsumer("SynchConsumer GAMMA1", topic, connectionFactory, 500);
        logger.info("Creato consumer: " + cs1.toString());

        /* crea un filtro che legge dal topic T e scrive sulla coda Q */
        SimpleFilter f1 = new SimpleFilter("SimpleFilter PHI", topic, queue, connectionFactory, 300);
        logger.info("Creato filtro: " + f1.toString());

        /* crea un consumatore sincrono sulla coda Q */
        CancellableSynchConsumer cs2 = new CancellableSynchConsumer("SynchConsumer GAMMA2", queue, connectionFactory, 200);
        logger.info("Creato consumer: " + cs2.toString());

        /* crea un consumatore asincrono sulla coda Q */
        TextMessageProcessor mp1 = new SimpleMessageProcessor("AsynchConsumer GAMMA3");
        SimpleAsynchConsumer ca1 = new SimpleAsynchConsumer("AsynchConsumer GAMMA3", queue, connectionFactory, mp1, 200);
        logger.info("Creato consumer: " + ca1.toString());

        /* prepara la cancellazione dei consumatori */
        KeyboardKiller k = new KeyboardKiller();
        k.add(cs1);
        k.add(cs2);
        k.add(ca1);
        k.add(f1);
        k.start();

        /* avvia i consumer e il filtro */
        /* nota: al posto di 0 si puo' mettere il numero di messaggi max accettati */
        Thread tcs1 = new CancellableSynchConsumerThread(cs1, "Consumer GAMMA1");
        tcs1.start();
        Thread tcs2 = new CancellableSynchConsumerThread(cs2, "Consumer GAMMA2");
        tcs2.start();
        Thread tca1 = new SimpleAsynchConsumerThread(ca1, "Consumer GAMMA3");
        tca1.start();
        Thread tf1 = new SimpleFilterThread(f1, "Filtro PHI");
        tf1.start();

        /* ora un piccolo ritardo per evitare la perdita di messaggi
         * (utile in effetti solo se la destinazione e' un topic) */
        Sleeper.sleep(2000);

        /* avvia i due producer */
        int messaggiAlfa = (int)(numeroTotaleMessaggi*0.6);
        int messaggiBeta = numeroTotaleMessaggi - messaggiAlfa;
        Thread tp1 = new SimpleProducerThread(p1, "Producer ALPHA", messaggiAlfa);
        Thread tp2 = new SimpleProducerThread(p2, "Producer BETA", messaggiBeta);
        tp1.start();
        tp2.start();

        /* attende la fine di tutti questi thread, e termina */
        try {
			tp1.join();
			tp2.join();
			tcs1.join();
			tcs2.join();
			tca1.join();
			tf1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        System.exit(0);
    }

}
