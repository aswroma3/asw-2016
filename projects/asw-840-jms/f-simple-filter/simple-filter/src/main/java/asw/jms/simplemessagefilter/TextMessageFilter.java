package asw.jms.simplemessagefilter;

/**
 * Definisce l'interfaccia di un oggetto/componente in grado
 * di filtrare un messaggio di testo.
 *
 * @author Luca Cabibbo
 */
public interface TextMessageFilter {

	/**
	 * Filtra il messaggio di testo message.
	 */
	public String filterMessage(String message);

}
