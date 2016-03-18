package asw.socket.server.connector;

import asw.socket.service.*;

import java.net.*;
import java.io.*;

import java.util.logging.Logger;
import asw.util.logging.AswLogger;

public class ServantThread extends Thread {

	private Service service;
	private Socket clientSocket;
	private DataInputStream in;
	private DataOutputStream out;

	private static int MAX_SERVANT_THREAD_ID = 0;
	private int servantThreadId;

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.socket.server.connector");

	public ServantThread(Socket clientSocket, Service service) {
		try {
			this.clientSocket = clientSocket;
			this.service = service;
			this.servantThreadId = MAX_SERVANT_THREAD_ID++;
			in = new DataInputStream(clientSocket.getInputStream());
			out = new DataOutputStream(clientSocket.getOutputStream());
			this.start();    // esegue run() in un nuovo thread
		} catch (IOException e) {
			logger.info("Server Proxy: IO Exception: " + e.getMessage());
		}
	}


	/* run eseguito in un nuovo thread */
	public void run() {
		logger.info("Server Proxy: opening connection [" + servantThreadId + "]");
		try {
			/* riceve una richiesta */
			String request = in.readUTF();  // bloccante
    		logger.info("Server Proxy: connection [" + servantThreadId + "]: received request: " + request);

            /* la richiesta ha la forma "operazione$argomento" */
            /* estrae operazione e argomento */
            String op = this.getOp(request);
            String arg = this.getParam(request);

            /* chiedi l'erogazione del servizio, ottiene il risultato, e calcola la risposta */
            /*
             * la risposta puo' avere le seguenti forme:
             * "#risultato" oppure "@messaggio per eccezione"
             */
            String reply = null;
            try {
                String result = this.executeOperation(op, arg);
                /* se siamo qui, operazione completata, la risposta ha la forma "#risultato" */
                reply = "#" + result;
    		} catch (ServiceException e) {
                /* se siamo qui, operazione NON completata, la risposta ha la forma "@messaggio" */
                reply = "@" + e.getMessage();
    		} catch (RemoteException e) {
                /* NON dovremmo essere qui, perche' il servente non dovrebbe mai sollevare RemoteExecption */
                reply = "@" + e.getMessage();
            }

    		/* invia la risposta */
    		logger.info("Server Proxy: connection [" + servantThreadId + "]: sending reply: " + reply);
			out.writeUTF(reply);    // non bloccante
		} catch (EOFException e) {
			logger.info("Server Proxy: connection [" + servantThreadId + "]: EOFException: " + e.getMessage());
		} catch (IOException e) {
			logger.info("Server Proxy: connection [" + servantThreadId + "]: IOException: " + e.getMessage());
		} finally {
			try {
				clientSocket.close();
			} catch (IOException e) {
				logger.info("Server Proxy: connection [" + servantThreadId + "]: IOException: " + e.getMessage());
			}
		}
		logger.info("Server Proxy: closing connection [" + servantThreadId + "]");
	}

    /* estrae l'operazione dalla richiesta */
    private String getOp(String request) {
        /* la richiesta ha la forma "operazione$parametro" */
        int sep = request.indexOf("$");
        String op = request.substring(0,sep);
        return op;
    }

    /* estrae il parametro dalla richiesta */
    private String getParam(String request) {
        /* la richiesta ha la forma "operazione$parametro" */
        int sep = request.indexOf("$");
        String param = request.substring(sep+1);
        return param;
    }

    /* gestisce la richiesta del servizio corretto al servente */
    private String executeOperation(String op, String param) throws ServiceException, RemoteException {
        String result = null;

        if ( op.equals("alpha") ) {
            result = service.alpha(param);
        }
        else if ( op.equals("beta") ) {
            result = service.beta(param);
        } else {
            throw new RemoteException("Operation " + op + " is not supported");
        }

        return result;
    }

}
