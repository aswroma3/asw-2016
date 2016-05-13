package asw.rest.productmanager.client;

import asw.rest.productmanager.Product;

import java.util.*;

import asw.util.logging.*;
import java.util.logging.*;

/*
 * Client per il web service REST product-manager.
 */

public class Main {

	/* proxy al servizio rest */
	private ProductManagerClient service;

	/* logger */
	private static Logger logger = AswLogger.getInstance().getLogger("asw.rest.productmanager.client");

	public static void main(String[] args) {
		Main main = new Main();
		main.runReadOnlyOperations();
		if (args.length>0) {
			main.runModifyingOperations();
		}
	}

	public Main() {
		/* crea il client (adapter) per il servizio rest */
		service = new ProductManagerClient();
	}

	private void runReadOnlyOperations() {
		Product product;
		Collection<Product> products;
		String result;
		boolean esito;

	   	/* usa il servizio rest Product Manager Service */
	   	logger.info("Client: Ora uso il servizio REST ProductManagerService");

	   	logger.info("");
	   	logger.info("Client: getProductAsJson(1)");
		product = service.getProductAsJson(1);
	   	logger.info("Client: --> " + product.toString());

	   	logger.info("");
	   	logger.info("Client: getProductAsXML(3)");
		product = service.getProductAsXML(3);
	   	logger.info("Client: --> " + product.toString());

	 	logger.info("");
	   	logger.info("Client: getProductsAsJsonString");
		result = service.getProductsAsJsonString();
	   	logger.info("Client: --> " + result);

	 	logger.info("");
	   	logger.info("Client: getProductsAsXMLString");
		result = service.getProductsAsXMLString();
	   	logger.info("Client: --> " + result);

	 	logger.info("");
	   	logger.info("Client: getProductsAsJson");
		products = service.getProductsAsJson();
	   	logger.info("Client: --> " + products.toString());

	   	logger.info("");
	   	logger.info("Client: getProductsAsXML");
		products = service.getProductsAsXML();
	   	logger.info("Client: --> " + products.toString());
	}

	private void runModifyingOperations() {
		Product product;
		Collection<Product> products;
		String result;
		boolean esito;

	   	logger.info("");
	   	logger.info("Client: createProduct(16, \"NUOVO PRODOTTO FORM 16\", 160)");
		esito = service.createProduct(16, "NUOVO PRODOTTO FORM 16", 160);
	   	logger.info("Client: --> " + toString(esito));

	   	logger.info("");
	   	product = new Product(43, "NUOVO PRODOTTO JSON 43", 444);
	   	logger.info("Client: createProduct(" + product.toString() + ")");
		esito = service.createProduct(product);
	   	logger.info("Client: --> " + toString(esito));

	   	logger.info("");
	   	logger.info("Client: deleteProduct(5)");
		esito = service.deleteProduct(5);
	   	logger.info("Client: --> " + toString(esito));

	   	logger.info("");
	   	product = new Product(9, "PRODOTTO 9 MODIFICATO", 999);
	   	logger.info("Client: updateProduct(" + product.toString() + ")");
		esito = service.updateProduct(product);
	   	logger.info("Client: --> " + toString(esito));

		service.close();
	}

	private static String toString(boolean esito) {
		if (esito) {
			return "OK";
		} else {
			return "ERROR";
		}
	}

}
