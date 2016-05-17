package asw.ejb.calculator.client;

import asw.ejb.calculator.Calculator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import asw.util.sleep.Sleeper;

/*
 * GUI Swing per l'application client di Calculator Bean.
 */
public class CalculatorAppClientJFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private Calculator calculator;

	private JTextArea textArea;

	public CalculatorAppClientJFrame(Calculator calculator) {
		super();
		this.calculator = calculator;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Calculator Application Client");
		this.inizializzaGUI();
		this.pack();
		this.setVisible(true);
	}

	private void inizializzaGUI() {
		JPanel intermedio;
		intermedio = new JPanel();
		/* 40 righe per 80 colonne */
		this.textArea = new JTextArea(40, 80);
		intermedio.add(this.textArea);
		this.getContentPane().add(intermedio);
	}

	public void run() {

		this.println("Calculator Application Client");

		Sleeper.sleep(1000);

		this.println("Ora accedo al bean Calculator per calcolare la radice quadrata di 16");
		this.println("calculator.sqrt(16) ==> " + calculator.sqrt(16));

		Sleeper.sleep(1000);

		this.println("Ora accedo al bean Calculator per calcolare altre radici quadrate");
		for (int i=0; i<=20; i++) {
			this.println("calculator.sqrt( " + i + " ) ==> " + calculator.sqrt(i));
		}

		Sleeper.sleep(1000);

		this.println("Ho finito di usare il bean Calculator");
	}

	private void println(String x) {
		this.textArea.append(x+"\n");
	}

}