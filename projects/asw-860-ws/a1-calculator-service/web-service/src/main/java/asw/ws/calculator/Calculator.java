package asw.ws.calculator;

import javax.jws.*;

@WebService
public class Calculator {

	public Calculator() { }

	@WebMethod
	public double sqrt(double x) {
		return Math.sqrt(x);
	}

	@WebMethod
	public double exp(double x) {
		return Math.exp(x);
	}

}
