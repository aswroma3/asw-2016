package asw.ejb.counter;

import javax.ejb.Remote;

@Remote
public interface SessionCounter {

	/* incrementa il contatore e ne restituisce il valore */
	public int getCounter();

	/* termina la sessione */
	public void close();

}
