package Boy;
// Traffic.java -- Main
import javax.swing.*;

public class Traffic {
	public static void main(String[] args) {
		// Allocate and initialize for runnable
		BackgroundPanel bgPanel=new BackgroundPanel();
		CtrlEastWest CtrlEW = new CtrlEastWest(bgPanel);
		CtrlNorthSouth CtrlNS = new CtrlNorthSouth(bgPanel, CtrlEW);
		AddCar addCar = new AddCar(bgPanel);
		CheckCar checkCar = new CheckCar(bgPanel);
		FreshPanel freshPanel = new FreshPanel(bgPanel);
		
		// Definition of threads
		final Thread NSThread = new Thread(CtrlNS);
		final Thread EWThread = new Thread(CtrlEW);
		final Thread addCarThread = new Thread(addCar);
		final Thread freshPanelThread = new Thread(freshPanel);
		final Thread checkCarThread = new Thread(checkCar);
		
		// Run
		JFrame frame=new BackgroundFrame(NSThread, EWThread, addCarThread, freshPanelThread);
		frame.add(bgPanel);
		frame.setVisible(true);
		NSThread.start();
		EWThread.start();
		checkCarThread.start();
		freshPanelThread.start();
	}
}
