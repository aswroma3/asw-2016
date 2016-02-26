package asw.util.cancellable;

public interface Cancellable {
	void cancel();
	boolean isCancelled();
}
