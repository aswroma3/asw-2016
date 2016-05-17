package asw.ejb.asynchronous;

/*
 * Eccezione di business sollevata dal bean Hello asincrono.
 */
public class HelloException extends Exception {

	public HelloException(String message) {
		super(message);
	}

}
