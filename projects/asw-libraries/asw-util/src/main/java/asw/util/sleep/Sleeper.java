package asw.util.sleep;

/* Classe di utilita' per l'introduzione di "ritardi". */
public class Sleeper {

	/* Introduce un ritardo di esattamente delay millisecondi. */
	public static void sleep(int delay) {
        try {
        	// int delay = (int)(Math.random()*maxDelay);
            Thread.sleep(delay);
        } catch (InterruptedException e) {}
	}

	/* Introduce un ritardo casuale, al massimo di maxDelay millisecondi. */
	public static void randomSleep(int maxDelay) {
    	int delay = (int)(Math.random()*maxDelay);
    	sleep(delay);
	}

	/* Introduce un ritardo casuale, compreso tra minDelay e maxDelay millisecondi. */
	public static void randomSleep(int minDelay, int maxDelay) {
    	int delay = (int)(minDelay + Math.random()*(maxDelay-minDelay));
    	sleep(delay);
	}

}
