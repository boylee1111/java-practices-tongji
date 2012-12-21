package boy;

import java.awt.event.*;
import javax.swing.*;

public class FileSysFrame extends JFrame implements WindowListener, ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JMenuBar menuBar = null;
	private JMenu fileMenu, helpMenu = null;
	private JMenuItem exitMenuItem = null, helpMenuItem = null, aboutMenuItem = null;
	
	public FileSysFrame() {
		super("File System Management");
		this.setSize(Constants.FRAME_LENGTH, Constants.FRAME_WIDTH);
		// Create menu
		menuBar = new JMenuBar();
		
		fileMenu = new JMenu("File(F)");
		helpMenu = new JMenu("Help(H)");
		fileMenu.setMnemonic('F');
		helpMenu.setMnemonic('H');
		
		exitMenuItem = new JMenuItem("exit(E)");
		helpMenuItem = new JMenuItem("help(H)");
		aboutMenuItem = new JMenuItem("about(A)");
		exitMenuItem.setMnemonic('E');
		helpMenuItem.setMnemonic('H');
		aboutMenuItem.setMnemonic('A');
		
		// Add ActionListener
		exitMenuItem.addActionListener(this);
		helpMenuItem.addActionListener(this);
		aboutMenuItem.addActionListener(this);
	
		// Add to frame
		fileMenu.add(exitMenuItem);
		helpMenu.add(helpMenuItem);
		helpMenu.add(aboutMenuItem);
		
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		
		// Add to Listener
		this.addWindowListener(this);
		
		this.setJMenuBar(menuBar);
		// TODO 设置窗口初始位置
		this.setLocation(100, 100);
		this.setResizable(false);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object event = e.getSource();
		if (event == exitMenuItem) {
			System.exit(0);
		} else if (event == helpMenuItem) {
			JOptionPane.showMessageDialog(null,
					"It's easy to use~~~",
					"Help",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (event == aboutMenuItem) {
			JOptionPane.showMessageDialog(null,
					"Boy Lee. All rights reserved.",
					"Copyright(c)",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	// TODO window的监听
	@Override
	public void windowOpened(WindowEvent e) {
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		
	}
}