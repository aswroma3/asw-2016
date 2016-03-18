package asw.socket.client.connector;

import asw.socket.service.*;

import java.io.IOException;
import java.net.*;    // per le socket

import java.util.logging.*;
import asw.util.logging.AswLogger;

/* Remote proxy lato client per il servizio Service. */
public class ServiceClientUDPProxy implements Service {

	private InetAddress address;    // indirizzo del server
    private int port;               // porta per il servizio

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.asw820.client.connector");

    public ServiceClientUDPProxy(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

	/* questo e' proprio il metodo alpha invocato dal client
     * (anche se il client pensa di parlare direttamente con il servant) */
    public String alpha(String arg) throws ServiceException, RemoteException {
    	return doOperation("alpha", arg);
    }

	/* questo e' proprio il metodo beta invocato dal client
     * (anche se il client pensa di parlare direttamente con il servant) */
    public String beta(String arg) throws ServiceException, RemoteException {
    	return doOperation("beta", arg);
    }

    /* metodo di supporto per la comunicazione remota */
    private String doOperation(String op, String arg) throws ServiceException, RemoteException {
    	String result = null;
    	DatagramSocket socket = null;
        try {
        	/* crea il socket per gestire la comunicazione remota */
            socket = new DatagramSocket();
            /* solleva SocketException, se il socket non puo' essere aperto */
            /* imposta il timeout ad 1 secondo */
            socket.setSoTimeout(1000);

            /* crea un datagramma che codifica la richiesta di servizio e i relativi parametri */
            /* la richiesta ha la forma "operazione$parametro" */
            String request = op + "$" + arg;
            byte[] requestMessage = request.getBytes();
            DatagramPacket requestPacket =
                 new DatagramPacket(requestMessage,
                                    requestMessage.length,
                                    this.address, this.port);
            /* invia il datagramma di richiesta */
            logger.info("Client Proxy: sending request: " + request);
            socket.send(requestPacket);    // non bloccante
            /* solleva IOException, se si verifica un errore di I/O */

            /* ricevi il datagramma di risposta */
            byte[] buffer = new byte[1000];
            DatagramPacket replyPacket =
                new DatagramPacket(buffer, buffer.length);
            socket.receive(replyPacket);    // bloccante
            /* solleva IOException, se si verifica un errore di I/O */

            /* estrai la risposta dal datagramma di risposta */
            String reply = new String( replyPacket.getData(),
           		 			 replyPacket.getOffset(),
           		 			 replyPacket.getLength() );
      		logger.info("Client Proxy: received reply: " + reply);

            /*
             * elabora la risposta, che puo' avere le seguenti forme:
             * "#risultato" oppure "@messaggio per eccezione"
             */
        	if (reply.startsWith("#")) {
        		/* e' un risultato */
        		result = reply.substring(1);
        	} else if (reply.startsWith("@")) {
        		/* si e' verificata una ServiceException */
        		String message = reply.substring(1);
        		throw new ServiceException(message);
        	} else {
        		/* risposta malformata, solleva una RemoteException */
        		throw new RemoteException("Malformed reply: " + reply);
        	}
		} catch (SocketException e) {
			logger.info("Client Proxy: SocketException: " + e.getMessage());
			throw new RemoteException("Socket Exception: " + e.getMessage());
		} catch (IOException e) {
			logger.info("Client Proxy: IOException: " + e.getMessage());
			throw new RemoteException("IO Exception: " + e.getMessage());
		} finally {
			if (socket!=null) {
				socket.close();
			}
		}
        return result;
    }

}

