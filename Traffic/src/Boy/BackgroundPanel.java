package Boy;
// BackgroundPanel.java -- Initialize panel
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BackgroundPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	// Background Image
	private Image backgroundImage;
	
	// The total time of traffic light
	private int lightTime;
	
	// Allow to put arrows and the rest time of traffic light on it
	JPanel northPanel, southPanel, eastPanel, westPanel;

	// Show the arrows of various direction
	JLabel northLabel1, northLabel2, northLabel3;
	JLabel southLabel1, southLabel2, southLabel3;
	JLabel eastLabel1, eastLabel2, eastLabel3;
	JLabel westLabel1, westLabel2, westLabel3;
	JLabel flowLabel, dateLabel, carNumLabel;

	// Show the rest time of traffic light
	JTextField northTF, southTF, eastTF, westTF, flowTF, dateTF, carNumTF;
	
	// Icon used for button
	ImageIcon buttonIcon;
	// Buttons used for adding special car
	JButton northCar, southCar, eastCar, westCar;
	JButton auto, nonAuto;

	// Button used for clear screen
	JButton callPolice;

	// Define different font
	Font infoFont, lightFont, arrowFont, dataFont;

	// Store the car
	LinkedList<Car> carList = new LinkedList<Car>();

	public BackgroundPanel() {
		// Add a picture as a background
		backgroundImage = Toolkit.getDefaultToolkit().getImage(".\\pic\\road.png");
		MediaTracker tracker = new MediaTracker(this);
		tracker.addImage(backgroundImage, 0);
		try {
			tracker.waitForID(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		setLayout(null);

		// Allocation and initialization
		infoFont = new Font("Times New Roman", Font.BOLD, 23);
		lightFont = new Font("Times New Roman", Font.BOLD, 29);
		arrowFont = new Font("Times New Roman", Font.BOLD, 33);
		dataFont = new Font("Times New Roman", Font.BOLD, 15);
		
		buttonIcon = new ImageIcon(".\\pic\\cross.png");

		northPanel = new JPanel();
		southPanel = new JPanel();
		eastPanel = new JPanel();
		westPanel = new JPanel();

		northTF = new JTextField("00", 3);
		southTF = new JTextField("00", 3);
		eastTF = new JTextField("00", 3);
		westTF = new JTextField("00", 3);
		flowTF = new JTextField("", 3);
		dateTF = new JTextField("0", 3);
		carNumTF = new JTextField("00", 3);

		northLabel1 = new JLabel("←", SwingConstants.CENTER);
		northLabel2 = new JLabel("↓", SwingConstants.CENTER);
		northLabel3 = new JLabel("→", SwingConstants.CENTER);
		southLabel1 = new JLabel("←", SwingConstants.CENTER);
		southLabel2 = new JLabel("↑", SwingConstants.CENTER);
		southLabel3 = new JLabel("→", SwingConstants.CENTER);
		eastLabel1 = new JLabel("↑", SwingConstants.CENTER);
		eastLabel2 = new JLabel("←", SwingConstants.CENTER);
		eastLabel3 = new JLabel("↓", SwingConstants.CENTER);
		westLabel1 = new JLabel("↑", SwingConstants.CENTER);
		westLabel2 = new JLabel("→", SwingConstants.CENTER);
		westLabel3 = new JLabel("↓", SwingConstants.CENTER);
		flowLabel = new JLabel("cars/h", SwingConstants.CENTER);
		dateLabel = new JLabel("Data", SwingConstants.CENTER);
		carNumLabel = new JLabel("Number of Cars", SwingConstants.CENTER);
		
		add(northPanel);
		add(southPanel);
		add(eastPanel);
		add(westPanel);
		add(flowTF);
		add(dateTF);
		add(flowLabel);
		add(dateLabel);
		add(carNumLabel);
		add(carNumTF);

		// Set the properties of JPanel, JLabel, JButton and JTextField
		flowTF.setBounds(10, 20, 120, 30);
		flowLabel.setBounds(130, 20, 90, 30);
		dateTF.setBounds(100, 100, 100, 30);
		dateLabel.setBounds(340, 575, 300, 20);
		carNumTF.setBounds(0, 140, 600, 330);
		carNumLabel.setBounds(200, 245, 200, 30);
		dateTF.setVisible(false);
		carNumTF.setEditable(false);
		carNumTF.setOpaque(false);
		carNumTF.setBorder(null);
		carNumLabel.setOpaque(false);
		flowTF.setFont(infoFont);
		carNumTF.setFont(infoFont);
		dateLabel.setFont(dataFont);
		flowLabel.setFont(infoFont);
		carNumLabel.setFont(infoFont);

		// Set different panel of arrows
		northPanel.setBounds(222, 0, 156, 50);
		northPanel.setLayout(new GridLayout(1, 4, 1, 0));
		northPanel.setBackground(Color.BLACK);
		southPanel.setBounds(222, 550, 156, 50);
		southPanel.setLayout(new GridLayout(1, 4, 1, 0));
		southPanel.setBackground(Color.BLACK);
		eastPanel.setBounds(550, 222, 50, 156);
		eastPanel.setLayout(new GridLayout(4, 1, 0, 1));
		eastPanel.setBackground(Color.BLACK);
		westPanel.setBounds(0, 222, 50, 156);
		westPanel.setLayout(new GridLayout(4, 1, 0, 1));
		westPanel.setBackground(Color.BLACK);

		// Size of font
		northLabel1.setFont(arrowFont);
		northLabel2.setFont(arrowFont);
		northLabel3.setFont(arrowFont);
		southLabel1.setFont(arrowFont);
		southLabel2.setFont(arrowFont);
		southLabel3.setFont(arrowFont);
		eastLabel1.setFont(arrowFont);
		eastLabel2.setFont(arrowFont);
		eastLabel3.setFont(arrowFont);
		westLabel1.setFont(arrowFont);
		westLabel2.setFont(arrowFont);
		westLabel3.setFont(arrowFont);
		northTF.setFont(lightFont);
		southTF.setFont(lightFont);
		eastTF.setFont(lightFont);
		westTF.setFont(lightFont);

		// Color of foreground and background
		northLabel1.setForeground(Color.BLACK);
		northLabel2.setForeground(Color.BLACK);
		northLabel3.setForeground(Color.BLACK);	
		southLabel1.setForeground(Color.BLACK);
		southLabel2.setForeground(Color.BLACK);
		southLabel3.setForeground(Color.BLACK);
		eastLabel1.setForeground(Color.BLACK);
		eastLabel2.setForeground(Color.BLACK);
		eastLabel3.setForeground(Color.BLACK);
		westLabel1.setForeground(Color.BLACK);
		westLabel2.setForeground(Color.BLACK);
		westLabel3.setForeground(Color.BLACK);
		northTF.setForeground(Color.RED);
		northTF.setBackground(Color.BLUE);
		southTF.setForeground(Color.RED);
		southTF.setBackground(Color.BLUE);
		eastTF.setForeground(Color.RED);
		eastTF.setBackground(Color.BLUE);
		westTF.setForeground(Color.RED);
		westTF.setBackground(Color.BLUE);

		// Whether is editable
		northTF.setEditable(false);
		southTF.setEditable(false);
		eastTF.setEditable(false);
		westTF.setEditable(false);

		// Put the font in center
		northTF.setHorizontalAlignment(JTextField.CENTER);
		southTF.setHorizontalAlignment(JTextField.CENTER);
		eastTF.setHorizontalAlignment(JTextField.CENTER);
		westTF.setHorizontalAlignment(JTextField.CENTER);
		flowTF.setHorizontalAlignment(JTextField.RIGHT);
		carNumTF.setHorizontalAlignment(JTextField.CENTER);

		// Add to JPanel
		northPanel.add(northLabel1);
		northPanel.add(northLabel2);
		northPanel.add(northLabel3);
		northPanel.add(northTF);
		southPanel.add(southLabel1);
		southPanel.add(southLabel2);
		southPanel.add(southLabel3);
		southPanel.add(southTF);
		eastPanel.add(eastLabel1);
		eastPanel.add(eastLabel2);
		eastPanel.add(eastLabel3);
		eastPanel.add(eastTF);
		westPanel.add(westLabel1);
		westPanel.add(westLabel2);
		westPanel.add(westLabel3);
		westPanel.add(westTF);

		northCar = new JButton(buttonIcon);
		southCar = new JButton(buttonIcon);
		westCar = new JButton(buttonIcon);
		eastCar = new JButton(buttonIcon);
		auto = new JButton("自动出特殊车辆");
		nonAuto = new JButton("手动出特殊车辆");

		northCar.setBounds(480, 10, 30, 30);
		southCar.setBounds(480, 70, 30, 30);
		westCar.setBounds(450, 40, 30, 30);
		eastCar.setBounds(510, 40, 30, 30);
		auto.setBounds(433, 100, 126, 20);
		nonAuto.setBounds(433, 120, 126, 20);
		
		this.add(northCar);
		this.add(southCar);
		this.add(westCar);
		this.add(eastCar);
		this.add(auto);
		this.add(nonAuto);
		
		// Set the button
		callPolice = new JButton("Call Police", new ImageIcon(".\\pic\\police.png"));
		callPolice.setBounds(10, 520, 100, 75);
		callPolice.setVerticalTextPosition(JButton.BOTTOM);
		callPolice.setHorizontalTextPosition(JButton.CENTER);
		add(callPolice);
		
		callPolice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carList.clear();
			}
		});
		
		// Add a key listener
		flowTF.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_ENTER) { // Check whether press Enter
					if (flowTF.getText().toString().equals("more cars")) { // A special mode
						lightTime = 5;
						Constants.sleepTime = 200;
						Constants.Coefficient = 0;
					}
					else { // Normal mode
						try {
							lightTime = Integer.parseInt(flowTF.getText()); // Transform String to int
							if (lightTime > 0) // Set the read time of light as the flow/60
								lightTime /= 30;
							else
								lightTime = 1;
							if (lightTime < 0) {
								JOptionPane.showMessageDialog(null, "Please make sure the number bigger than 0", "Input Error", JOptionPane.ERROR_MESSAGE);
								lightTime = 0;
								flowTF.setText("");							
							}
							else { // Check whether the number if legal
								if (lightTime > 50) // Make 50 as the longest time
								{
									lightTime = 50;
									Constants.sleepTime = 200;
									Constants.Coefficient = 1;
								}
								else if ((lightTime < 30) && (lightTime > 0)) {
									lightTime = 30;
									Constants.sleepTime = 700;
									Constants.Coefficient = 1;
								}
								if (lightTime == 0) {
									lightTime = 20;
									Constants.sleepTime = 1500;
									Constants.Coefficient = 1;
								}
								if (JOptionPane.showConfirmDialog(null, "Make the time of light as " + lightTime + " second", "Are you sure", JOptionPane.YES_NO_OPTION) != 0)
									lightTime = 0;
							}
						} catch (NumberFormatException e1) {
							JOptionPane.showMessageDialog(null, "Only allow number", "Input error", JOptionPane.ERROR_MESSAGE);
							lightTime = 0;
							flowTF.setText("");
						}
						dateTF.setText(String.valueOf(lightTime));
					}
				}
			}
		});
	}

	// Used to repaint the panel
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, this);
		synchronized(carList) {
			for (Car car:carList) { // Traverse the car list and paint them
				g.setColor(car.color);
				g.fillRect(car.x, car.y, Car.length, Car.width);
			}
		}
	}
}
