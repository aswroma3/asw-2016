package asw.rmi.hello.service;

/**
 * L'eccezione HelloException rappresenta un'eccezione "funzionale" legata al servizio,
 * che puo' essere sollevata direttamente dal servente.
 */
public class HelloException extends Exception {
	public HelloException(String message) {
		super(message);
	}
}
