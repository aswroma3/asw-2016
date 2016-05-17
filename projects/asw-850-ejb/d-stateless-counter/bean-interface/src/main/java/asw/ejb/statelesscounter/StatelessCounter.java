package asw.ejb.statelesscounter;

import javax.ejb.Remote;

@Remote
public interface StatelessCounter {

	/* incrementa il contatore e ne restituisce il valore */
	public int getCounter();

}
