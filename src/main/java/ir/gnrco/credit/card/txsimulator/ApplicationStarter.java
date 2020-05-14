package ir.gnrco.credit.card.txsimulator;

import javax.swing.SwingUtilities;

public class ApplicationStarter {

	public static void main(String [] args) {
	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new TxSimulatorAppGui();
			}
		});
	}
}