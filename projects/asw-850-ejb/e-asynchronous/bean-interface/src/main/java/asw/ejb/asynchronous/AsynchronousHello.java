package asw.ejb.asynchronous;

import java.util.concurrent.Future;

import javax.ejb.Asynchronous;
import javax.ejb.Remote;

@Remote
public interface AsynchronousHello {

	/** Un'operazione asincrona. */
	@Asynchronous
	public Future<String> hello(String name) throws HelloException, InterruptedException;

}
