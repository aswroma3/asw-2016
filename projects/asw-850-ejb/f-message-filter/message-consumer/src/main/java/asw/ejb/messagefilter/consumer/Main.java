package asw.ejb.messagefilter.consumer;

import javax.jms.*;
import javax.annotation.Resource;

public class Main {

	/* L'iniezione delle dipendenze puo' avvenire solo nella main class. */
	@Resource(lookup = "jms/asw/ejb/QueueTwo")
    private static Queue queueTwo;
    @Resource(lookup = "jms/asw/ConnectionFactory")
    private static ConnectionFactory connectionFactory;

    public static void main(String[] args) {
    	ConsumatoreMessaggi client = new ConsumatoreMessaggi(queueTwo, connectionFactory);
    	client.run();
    }

}
