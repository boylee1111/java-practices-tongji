package boy;
// BackgroundFrame.java -- Initialize the frame of the programming
import java.awt.event.*;
import javax.swing.*;

public class BackgroundFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	// One menu bar, two menus, and six menu items totally 
	private JMenuBar menuBar;
	private JMenu editMenu, helpMenu;
	private JMenuItem continueItem, stopItem, quitItem, helpItem, aboutItem;
	
	public BackgroundFrame(final Thread northSouth,final Thread eastWest,final Thread addCar,final Thread freshPanel) {
		super("Traffic Simulator System");
		setSize(616, 662);

		// Create JMenuBar, JMenu and set shortcuts
		menuBar = new JMenuBar();		
		setJMenuBar(menuBar);
		editMenu = new JMenu("Edit(E)");
		helpMenu = new JMenu("Help(H)");
		editMenu.setMnemonic('E');
		helpMenu.setMnemonic('H');

		// Create JMenuItem and set shortcuts
		continueItem = new JMenuItem("continue(C)", 'C');
		stopItem = new JMenuItem("stop(S)", 'S');
		quitItem = new JMenuItem("quit(Q)", 'Q');
		helpItem = new JMenuItem("help(H)", 'H');
		aboutItem = new JMenuItem("about(A)", 'A');
		continueItem.setMnemonic('C');
		stopItem.setMnemonic('S');
		quitItem.setMnemonic('Q');
		helpItem.setMnemonic('H');
		aboutItem.setMnemonic('A');

		// Add JMenuItem to JMenu, and add JMenu to JMenuBar
		menuBar.add(editMenu);
		menuBar.add(helpMenu);		
		editMenu.add(continueItem);
		editMenu.add(stopItem);
		editMenu.addSeparator();
		editMenu.add(quitItem);
		helpMenu.add(helpItem);
		helpMenu.addSeparator();
		helpMenu.add(aboutItem);

		continueItem.setEnabled(false);
		addCar.start();

		// Continue item, when stop it can be uses to resume the programming
		continueItem.addActionListener(new ActionListener()	{
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				continueItem.setEnabled(false);
				stopItem.setEnabled(true);
				northSouth.resume();
				eastWest.resume();
				addCar.resume();
				freshPanel.resume();
				Car.v = 1;
			}
		});

		// Stop item, used to stop the programming
		stopItem.addActionListener(new ActionListener()	{
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				continueItem.setEnabled(true);
				stopItem.setEnabled(false);
				northSouth.suspend();
				eastWest.suspend();
				addCar.suspend();
				freshPanel.suspend();
				Car.v = 0;
			}
		});

		// Quit
		quitItem.addActionListener(new ActionListener()	{
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				northSouth.stop();
				eastWest.stop();
				addCar.stop();
				freshPanel.stop();
				System.exit(0);
			}
		});

		// Help
		helpItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "It's simple to use!", "Help", JOptionPane.YES_OPTION);
			}
		});

		// About
		aboutItem.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Boy Lee. All rights reserved.", "Copyright(c)", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		// Close
		addWindowListener(new WindowAdapter() {
			@SuppressWarnings("deprecation")
			public void windowClosing(WindowEvent e) {
				northSouth.stop();
				eastWest.stop();
				addCar.stop();
				freshPanel.stop();
				System.exit(0);
			}
		});
	}
}