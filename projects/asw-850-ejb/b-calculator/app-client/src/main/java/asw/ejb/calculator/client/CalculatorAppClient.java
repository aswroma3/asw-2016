package asw.ejb.calculator.client;

import asw.ejb.calculator.Calculator;

// import asw.util.sleep.Sleeper;

import java.util.logging.*;
import asw.util.logging.*;

/*
 * Application client testuale per il bean Calculator.
 */
public class CalculatorAppClient {

	/* logger */
	private Logger logger = AswLogger.getInstance().getLogger("asw.ejb.calculator.client");

	private Calculator calculator;

	public CalculatorAppClient(Calculator calculator) {
		this.calculator = calculator;
	}

	public void run() {

		this.println("Calculator Application Client");

//		Sleeper.sleep(1000);

		this.println("Ora accedo al bean Calculator per calcolare la radice quadrata di 16");
		this.println("calculator.sqrt(16) ==> " + calculator.sqrt(16));

//		Sleeper.sleep(1000);

		this.println("Ora accedo al bean Calculator per calcolare altre radici quadrate");
		for (int i=0; i<=20; i++) {
			this.println("calculator.sqrt( " + i + " ) ==> " + calculator.sqrt(i));
		}

//		Sleeper.sleep(1000);

		this.println("Ho finito di usare il bean Calculator");
	}

	private void println(String x) {
		logger.info(x);
	}

}