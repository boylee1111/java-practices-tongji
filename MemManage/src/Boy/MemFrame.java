package Boy;
// MemFrame.java -- Initialize the frame
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class MemFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	CtrlFirstFit firstFit = null;
	CtrlBestFit bestFit = null;

	// MenuBar¡¢Menu and MenuItem
	private JMenuBar menuBar;
	private JMenu fileMenu, aboutMenu;
	private JMenuItem exitItem, aboutItem;

	JLayeredPane firstMemPane, bestMemPane; // Memory Block Panel
	private JPanel firstCtrlPane, bestCtrlPane; // Use GridLayout to layout
	private JPanel cutoffPane; // Cut different algorithm
	private JPanel contentPane; // Global Panel
	
	private Font font, titleFont, demoFont; // Different font
	
	// Constants Label
	private JLabel firstLabel, bestLabel;
	private JLabel firstKLabel, bestKLabel;
	private JLabel firstJobLabel, bestJobLabel;
	private JLabel firstBottomLabel, bestBottomLabel;
	
	// Buttons, use as their name
	JButton firstDemoButton, bestDemoButton;
	JButton firstPackButton, bestPackButton;
	JButton firstLogButton, bestLogButton;
	JButton firstAllocButton, bestAllocButton;
	JButton firstFreeButton, bestFreeButton;
	
	// Interaction
	JTextField firstAllocText, bestAllocText;
	JTextField firstFreeText, bestFreeText;

	public MemFrame() {
		super("Memory Management");
		this.setSize(800, 600);
	}
	
	public void initWithAlgo(CtrlFirstFit firstFitDel, CtrlBestFit bestFitDel) {
		firstFit = firstFitDel;
		bestFit = bestFitDel;
		
		contentPane = new JPanel();
		contentPane.setLayout(null);
//		contentPane.setBackground(Color.BLACK);
		this.add(contentPane);
		
		// Create menu bar, menus and menu items
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File(F)");
		aboutMenu = new JMenu("About(A)");
		exitItem = new JMenuItem("Exit(E)", 'E');
		aboutItem = new JMenuItem("About(A)", 'A');

		// set shortcuts
		fileMenu.setMnemonic('F');
		aboutMenu.setMnemonic('A');
		exitItem.setMnemonic('E');
		aboutItem.setMnemonic('A');
		
		// Add
		exitItem.addActionListener(this);
		aboutItem.addActionListener(this);
		fileMenu.add(exitItem);
		aboutMenu.add(aboutItem);
		menuBar.add(fileMenu);
		menuBar.add(aboutMenu);
		this.setJMenuBar(menuBar);
		
		font = new Font("Times New Roman", Font.BOLD, 13);
		titleFont = new Font("Times New Roman", Font.BOLD, 25);
		demoFont = new Font("Times New Roman", Font.BOLD, 18);
		
		// Title
		firstLabel = new JLabel("First-Fit Algorithm", JLabel.CENTER);
		bestLabel = new JLabel("Best-Fit Algorithm", JLabel.CENTER);
		firstLabel.setForeground(Color.RED);
		bestLabel.setForeground(Color.RED);
		firstLabel.setFont(titleFont);
		bestLabel.setFont(titleFont);
		firstLabel.setBounds(50, 10, 300, 50);
		bestLabel.setBounds(450, 10, 300, 50);
		contentPane.add(firstLabel);
		contentPane.add(bestLabel);

		firstDemoButton = new JButton("First-Fit Demo");
		bestDemoButton = new JButton("Best-Fit Demo");
		firstDemoButton.setFont(demoFont);
		bestDemoButton.setFont(demoFont);
		firstDemoButton.setBounds(125, 390, 150, 50);
		bestDemoButton.setBounds(525, 390, 150, 50);
		contentPane.add(firstDemoButton);
		contentPane.add(bestDemoButton);
		
		firstPackButton = new JButton("First-Fit Pack");
		bestPackButton = new JButton("Best-Fit Pack");
		firstPackButton.setFont(font);
		bestPackButton.setFont(font);
		firstPackButton.setBounds(20, 450, 125, 40);
		bestPackButton.setBounds(420, 450, 125, 40);
		
		firstPackButton.addActionListener(firstFit);
		bestPackButton.addActionListener(bestFit);
		
		contentPane.add(firstPackButton);
		contentPane.add(bestPackButton);
		
		firstLogButton = new JButton("First-Fit LogCat");
		bestLogButton = new JButton("Best-Fit LogCat");
		firstLogButton.setFont(font);
		bestLogButton.setFont(font);
		firstLogButton.setBounds(20, 490, 125, 40);
		bestLogButton.setBounds(420, 490, 125, 40);
		contentPane.add(firstLogButton);
		contentPane.add(bestLogButton);
		
		firstCtrlPane = new JPanel();
		bestCtrlPane = new JPanel();
		cutoffPane = new JPanel();
		
		// Set the layout
		firstCtrlPane.setLayout(null);
		firstCtrlPane.setLayout(new GridLayout(2, 3, 5, 5));
		firstCtrlPane.setBounds(160, 450, 200, 80);
//		firstPane.setBackground(Color.WHITE);
		bestCtrlPane.setLayout(null);
		bestCtrlPane.setLayout(new GridLayout(2, 3, 5, 5));
		bestCtrlPane.setBounds(560, 450, 200, 80);
//		bestPane.setBackground(Color.WHITE);
		contentPane.add(firstCtrlPane);
		contentPane.add(bestCtrlPane);
		
		cutoffPane.setLayout(null);
		cutoffPane.setBounds(399, 0, 2, 600);
		cutoffPane.setBackground(Color.GREEN);
		contentPane.add(cutoffPane);
		
		firstAllocButton = new JButton("Alloc");
		firstAllocText = new JTextField("0");
		firstKLabel = new JLabel("K");
		firstFreeButton = new JButton("Free");
		firstJobLabel = new JLabel("Job Num:", JLabel.CENTER);
		firstFreeText = new JTextField("0");

		firstAllocButton.setFont(font);
		firstAllocText.setFont(font);
		firstKLabel.setFont(font);
		firstFreeButton.setFont(font);
		firstJobLabel.setFont(font);
		firstFreeText.setFont(font);
		firstAllocText.setHorizontalAlignment(JTextField.RIGHT);

		// Add delegate
		firstAllocButton.addActionListener(firstFit);
		firstFreeButton.addActionListener(firstFit);
		firstAllocText.addKeyListener(firstFit);
		firstFreeText.addKeyListener(firstFit);

		firstCtrlPane.add(firstAllocButton);
		firstCtrlPane.add(firstAllocText);
		firstCtrlPane.add(firstKLabel);
		firstCtrlPane.add(firstFreeButton);
		firstCtrlPane.add(firstJobLabel);
		firstCtrlPane.add(firstFreeText);

		bestAllocButton = new JButton("Alloc");
		bestAllocText = new JTextField("0");
		bestKLabel = new JLabel("K");
		bestFreeButton = new JButton("Free");
		bestJobLabel = new JLabel("Job Num:", JLabel.CENTER);
		bestFreeText = new JTextField("0");

		bestAllocButton.setFont(font);
		bestAllocText.setFont(font);
		bestKLabel.setFont(font);
		bestFreeButton.setFont(font);
		bestJobLabel.setFont(font);
		bestFreeText.setFont(font);
		bestAllocText.setHorizontalAlignment(JTextField.RIGHT);

		// Add delegate
		bestAllocButton.addActionListener(bestFit);
		bestFreeButton.addActionListener(bestFit);
		bestAllocText.addKeyListener(bestFit);
		bestFreeText.addKeyListener(bestFit);
		
		bestCtrlPane.add(bestAllocButton);
		bestCtrlPane.add(bestAllocText);
		bestCtrlPane.add(bestKLabel);
		bestCtrlPane.add(bestFreeButton);
		bestCtrlPane.add(bestJobLabel);
		bestCtrlPane.add(bestFreeText);
		
		// Memory Block
		firstMemPane = new JLayeredPane();
		bestMemPane = new JLayeredPane();
		firstMemPane.setLayout(null);
		bestMemPane.setLayout(null);
		firstMemPane.setBounds(Constants.firstPaneRec);
		bestMemPane.setBounds(Constants.bestPaneRec);
		firstMemPane.setBackground(Color.GREEN);
		bestMemPane.setBackground(Color.GREEN);
		firstMemPane.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		bestMemPane.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		contentPane.add(firstMemPane);
		contentPane.add(bestMemPane);
		
		firstBottomLabel = new JLabel();
		bestBottomLabel = new JLabel();
		firstBottomLabel.setBounds(2, 2, Constants.blockWidth, Constants.memSize / 2);
		bestBottomLabel.setBounds(2, 2, Constants.blockWidth, Constants.memSize / 2);
		firstBottomLabel.setOpaque(true);
		bestBottomLabel.setOpaque(true);
		firstBottomLabel.setBackground(Color.GREEN);
		bestBottomLabel.setBackground(Color.GREEN);
		firstMemPane.add(firstBottomLabel, JLayeredPane.FRAME_CONTENT_LAYER);
		bestMemPane.add(bestBottomLabel, JLayeredPane.FRAME_CONTENT_LAYER);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
//		String cmd = e.getActionCommand();
//		System.out.println(cmd);
		if (e.getSource() == exitItem)
			System.exit(0);
		if ((JMenuItem)e.getSource() == aboutItem)
			JOptionPane.showMessageDialog(null, 
					"Boy Lee. All rights reserved.",
					"Copyright(c)",
					JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}
}