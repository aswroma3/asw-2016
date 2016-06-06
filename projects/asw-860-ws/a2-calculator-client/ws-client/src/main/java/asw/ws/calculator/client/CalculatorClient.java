package asw.ws.calculator.client;

import asw.ws.calculator.endpoint.CalculatorService;
import asw.ws.calculator.endpoint.Calculator;

import javax.xml.ws.WebServiceRef;

import asw.util.logging.*;
import java.util.logging.*;

/*
 * Application client per il web service Calculator.
 */

public class CalculatorClient {

	@WebServiceRef(wsdlLocation =
    	"http://10.11.1.101:8080/asw/ws/CalculatorService?wsdl")
    private static CalculatorService service;

	private Calculator calculator;

	/* logger */
	private static Logger logger = AswLogger.getInstance().getLogger("asw.ws.calculator.client");

	public static void main(String[] args) {
		new CalculatorClient() . run();
	}

	public CalculatorClient() {
		this.calculator = service.getCalculatorPort();
	}

	private void run() {
	   	/* usa il servizio Calculator */
	   	logger.info("Client: Ora uso il web service Calculator");

    	/* dovrebbe restituire "12.0" */
    	callSqrt(144);

    	/* dovrebbe restituire 2.71 */
    	callExp(1);

    	logger.info("Client: Ho finito di usare il web service Calculator");
	}

    /* chiama il servizio sqrt */
    private void callSqrt(double x) {
		logger.info("Client: calling sqrt(" + x + ")");
		double result = calculator.sqrt(x);
		logger.info("Client: sqrt(" + x + ") ==> " + result);
    }

    /* chiama il servizio exp */
    private void callExp(double x) {
		logger.info("Client: calling exp(" + x + ")");
		double result = calculator.exp(x);
		logger.info("Client: exp(" + x + ") ==> " + result);
    }

}