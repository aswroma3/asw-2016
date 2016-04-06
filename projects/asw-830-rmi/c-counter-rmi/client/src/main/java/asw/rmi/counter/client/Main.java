package asw.rmi.counter.client;

import asw.rmi.counter.service.Counter;

import asw.rmi.counter.client.connector.ServiceFactory;

/* Applicazione client: ottiene e avvia il client. */
public class Main {

	/* Crea e avvia un oggetto CounterClient. */
	public static void main(String[] args) {
		Counter counter = ServiceFactory.getInstance().getCounter(args);
		CounterClient client = new CounterClient(counter);
		client.run();
	}

}
