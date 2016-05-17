package asw.ejb.hello.service;

import javax.ejb.Remote;

@Remote
public interface Hello {

	/** Saluta il nome (in modo generico). */
	public String hello(String name);

	/** Saluta il nome in una specifica lingua. */
	public String hello(String name, String language);

}
