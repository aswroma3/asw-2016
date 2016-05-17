package asw.ejb.calculator.client;

import asw.ejb.calculator.Calculator;

import javax.ejb.EJB;

/*
 * Normalmente viene eseguito il client con un'interfaccia testuale,
 * ma l'uso di un argomento X11 fa' si' che venga eseguito il client
 * con un'interfaccia Swing.
 */
public class Main {

	@EJB(mappedName = "ejb/asw/Calculator")
	private static Calculator calculator;

	public static void main(String[] args) {
		if (args.length>0 && args[0].equals("X11")) {
			CalculatorAppClientJFrame client = new CalculatorAppClientJFrame(calculator);
			client.run();
		} else {
			CalculatorAppClient client = new CalculatorAppClient(calculator);
			client.run();
		}
	}

}