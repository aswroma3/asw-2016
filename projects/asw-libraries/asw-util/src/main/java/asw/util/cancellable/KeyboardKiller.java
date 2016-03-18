package asw.util.cancellable;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import asw.util.logging.AswLogger;

/* Utility che consente di interrompere un gruppo di oggetti Cancellable 
 * (nel nostro caso, un gruppo di produttori o consumatori di messaggi). 
 * In particolare, quando viene premuto INVIO sulla tastiera. */  
public class KeyboardKiller extends Thread {
	
	/* logger */ 
	private Logger logger = AswLogger.getInstance().getLogger("asw.util.cancellable"); 

	/* gli oggetti controllati */ 
	private Collection<Cancellable> controlled;
	
	public KeyboardKiller() {
		super();
		this.controlled = new ArrayList<Cancellable>();
	}
	
	/* aggiunge un nuovo elemento ai controllati */ 
	public void add(Cancellable c) {
		this.controlled.add(c); 
	}
	
	public void run() {
        /* interrompe il controllato se l'utente preme INVIO */
        logger.info("KeyboardKiller: Press ENTER to cancel task(s).");
		/* aspetta che l'utente prima INVIO */ 
		InputStreamReader inputStreamReader = new InputStreamReader(System.in);
		char answer = '\0'; 
		while (answer=='\0') {
            try {
                answer = (char) inputStreamReader.read();
            } catch (IOException e) {
                logger.info(this.toString() + ": I/O exception: " + e.toString());
            }
		}
		/* ok, ora interrompe i controllati */
		this.cancel(); 
	}
	
	private void cancel() {
        logger.info("KeyboardKiller: Cancelling task(s).");
        for (Cancellable c: controlled) {
        	c.cancel(); 
        }
	}

}
