package asw.ejb.calculator.impl;

import asw.ejb.calculator.Calculator;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import java.util.logging.Logger;

/**
 * Session Bean implementation class CalculatorImpl
 */
@Stateless(mappedName = "ejb/asw/Calculator")
public class CalculatorImpl implements Calculator {

	/* logger */
	private Logger logger;

	/* inizializza un'istanza del bean */
	@PostConstruct
	public void initialize() {
		/* fa il logging sul log del server EJB
		 * in particolare, nel file C:\glassfish3\glassfish\domains\domain1\logs\server.log */
    	logger = Logger.getLogger("asw.ejb.calculator.impl");
		logger.info("Calculator.initialize(): initializing CalculatorImpl instance");
	}

	/* Calcola la radice quadrata di x. */
	public double sqrt(double x) {
		double result = Math.sqrt(x);
		logger.info("Calculator.sqrt(" + x + ") ==> " + result);
		return result;
	}

}
