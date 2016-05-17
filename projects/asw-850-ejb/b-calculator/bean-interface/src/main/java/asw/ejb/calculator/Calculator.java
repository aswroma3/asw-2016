package asw.ejb.calculator;

import javax.ejb.Remote;

@Remote
public interface Calculator {

	/* Calcola la radice quadrata di x. */
	public double sqrt(double x);

}
