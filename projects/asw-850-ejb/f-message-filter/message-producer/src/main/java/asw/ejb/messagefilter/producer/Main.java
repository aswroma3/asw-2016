package asw.ejb.messagefilter.producer;

import javax.jms.*;
import javax.annotation.Resource;

public class Main {

	/* L'iniezione delle dipendenze puo' avvenire solo nella main class. */
	@Resource(lookup = "jms/asw/ejb/QueueOne")
    private static Queue queueOne;
    @Resource(lookup = "jms/asw/ConnectionFactory")
    private static ConnectionFactory connectionFactory;

    public static void main(String[] args) {
    	ProduttoreMessaggi client = new ProduttoreMessaggi(queueOne, connectionFactory);
    	client.run();
    }

}
