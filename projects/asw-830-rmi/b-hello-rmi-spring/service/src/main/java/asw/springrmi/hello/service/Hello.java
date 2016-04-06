package asw.springrmi.hello.service;

/**
 * Interfaccia del servizio Hello.
 */
public interface Hello {
	/**
	 * Crea un saluto specifico per il nome name.
	 * Name deve essere non nullo, altrimenti viene sollevata una HelloException.
	 */
    public String sayHello(String name) throws HelloException;
}

