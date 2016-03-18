package asw.intro.client.connector;

import asw.intro.service.Service;

import java.net.*;    // per le socket

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

/* remote proxy lato client per il servizio */
public class ServiceClientProxy implements Service {
    private InetAddress address;    // indirizzo del server
    private int port;                           // porta per il servizio

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.intro.client.connector");

	public ServiceClientProxy(InetAddress address, int port) {
		logger.info("ServiceClientProxy: Created a new Remote Proxy for Service " + address.toString() + ":" + port);
        this.address = address;
        this.port = port;
    }

    /* questo e' proprio il metodo invocato dal client
     * (anche se il client pensa di parlare direttamente con il servant) */
    public String alpha(String arg) {
    	String reply = null;
        try {
            /* crea un datagramma che codifica la richiesta di servizio
             * ed i relativi parametri */
            /* la richiesta ha la forma "operazione#parametro" */
        	String request = "alpha#" + arg;
            byte[] requestMessage = request.getBytes();
            DatagramPacket requestPacket =
                 new DatagramPacket(requestMessage,
                                    requestMessage.length,
                                    this.address, this.port);
            /* invia il datagramma di richiesta */
            DatagramSocket socket = new DatagramSocket();
            socket.setSoTimeout(5000);  // il timeout e' 5 secondi
     		logger.info("Client Proxy: calling alpha(" + arg + ")");
     		logger.info("Client Proxy: sending message: " + request);
            socket.send(requestPacket);    // non bloccante
            /* ricevi il datagramma di risposta */
            byte[] buffer = new byte[1000];
            DatagramPacket replyPacket =
                new DatagramPacket(buffer, buffer.length);
            socket.receive(replyPacket);    // bloccante
            /* estrai la risposta dal datagramma di risposta */
            /* la risposta ha la forma "risposta" */
            reply = new String( replyPacket.getData(),
                                replyPacket.getOffset(),
                                replyPacket.getLength() );
      		logger.info("Client Proxy: received message: " + reply);
     		logger.info("Client Proxy: alpha(" + arg + ") ==> " + reply);
     		socket.close();
        } catch(Exception e) {
        	e.printStackTrace();
        }
        return reply;
    }
}

