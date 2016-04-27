package asw.jms.messagebrowser;

import javax.jms.*;
import javax.annotation.Resource;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

/**
 * Main.java
 *
 * Browser di messaggi in una coda JMS.
 * nota: e' possibile solo fare il browsing di una coda, ma non di un topic.
 *
 * Variante di un esempio nel tutorial per Java EE.
 *
 * Esecuzione: dal prompt dei comandi, dalla cartella del progetto, tramite appclient:
 * appclient -client MessageBrowser
 *
 * @author Luca Cabibbo
 */
public class Main {

	/* L'iniezione delle dipendenze puo' avvenire solo nella main class. */
	@Resource(lookup = "jms/asw/Queue")
    private static Queue queue;
//    @Resource(lookup = "jms/asw/Topic")
//    private static Topic topic;
    @Resource(lookup = "jms/asw/ConnectionFactory")
    private static ConnectionFactory connectionFactory;

	/* logger */
	private static Logger logger = AswLogger.getInstance().getLogger("asw.jms.messagebrowser");

	/**
     * Utilizzo nel caso in cui l'application client viene eseguito nell'host dell'AS:
     *     appclient -client MessageQueueBrowser.jar
     */
    public static void main(String[] args) {

    	/* nota: e' possibile solo fare il browsing di una coda, ma non di un topic */

    	/* browsing dei messaggi */

        /* crea un message browser */
        MessageBrowser simpleBrowser = new MessageBrowser(queue, connectionFactory);
        logger.info("Creato message browser: " + simpleBrowser.toString());

        /* mostra i messaggi nella coda */
        simpleBrowser.connect();
        simpleBrowser.showMessages();
        simpleBrowser.disconnect();

    	/* termina il programma */
        System.exit(0);

    }

}
