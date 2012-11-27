package Boy;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MemLogCat extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private Font font, titleFont;
	
	private JLabel title;
	
	JTextArea logArea;	
	
	public MemLogCat() {
		super("LogCat");
		this.setSize(Constants.logCatFrame);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		
		font = new Font("Times New Roman", Font.BOLD, 17);
		titleFont = new Font("Times New Roman", Font.BOLD, 25);
		
		title = new JLabel("LogCat", JLabel.CENTER);
		title.setFont(titleFont);
		title.setBounds(0, 0, Constants.logCatFrame.width, 50);
//		contentPane.add(title);

		logArea = new JTextArea("LogCat\n");
		logArea.setEditable(false);
		logArea.setForeground(Color.BLACK);
		logArea.setFont(font);
		JScrollPane scrollPane = new JScrollPane(logArea);

		contentPane.add(scrollPane, BorderLayout.CENTER);

		this.add(contentPane);
		this.setResizable(false);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}