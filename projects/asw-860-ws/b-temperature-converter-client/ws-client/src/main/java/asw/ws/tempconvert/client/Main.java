package asw.ws.tempconvert.client;

import com.w3schools.tempconvert.endpoint.TempConvert;
import com.w3schools.tempconvert.endpoint.TempConvertSoap;

import javax.xml.ws.WebServiceRef;

import asw.util.logging.*;
import java.util.logging.*;

/*
 * Application client per il web service TempConvert:
 * http://www.w3schools.com/webservices/tempconvert.asmx?WSDL
 */

public class Main {

//	@WebServiceRef(wsdlLocation =
//    	"http://www.w3schools.com/webservices/tempconvert.asmx?WSDL")
//    private static TempConvert service = new TempCovert();

	private TempConvertSoap tempConvert;

	/* logger */
	private static Logger logger = AswLogger.getInstance().getLogger("asw.ws.tempconvert.client");

	public static void main(String[] args) {
		new Main() . run();
	}

	public Main() {
		this.tempConvert = new TempConvert().getTempConvertSoap12();
	}

	private void run() {
		String result;

	   	/* usa il servizio TempCovert */
	   	logger.info("Client: Ora uso il web service TempConvert");

    	/* dovrebbe restituire "32.0" */
		logger.info("Client: calling celsiusToFahrenheit(0.0)");
		result = tempConvert.celsiusToFahrenheit("0.0");
		logger.info("Client: calling celsiusToFahrenheit(0.0) --> " + result);

    	/* dovrebbe restituire "10.0" */
		logger.info("Client: calling fahrenheitToCelsius(50.0)");
		result = tempConvert.fahrenheitToCelsius("50.0");
		logger.info("Client: calling fahrenheitToCelsius(50.0) --> " + result);

    	logger.info("Client: Ho finito di usare il web service TempConvert");
	}

}