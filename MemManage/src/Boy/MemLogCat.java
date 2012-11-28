package Boy;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MemLogCat extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane, editPane;
	
	private Font font, editFont;
	
	private JLabel rateLabel;
	private JButton copy, clear;
	
	private JTextArea logArea;
	
	public MemLogCat() {
		this.setSize(Constants.logCatFrame);
		contentPane = new JPanel();
		editPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		editPane.setLayout(new GridLayout(1, 3));
		
		font = new Font("Calibri", Font.BOLD, 20);
		editFont = new Font("Times New Roman", Font.BOLD, 25);

		logArea = new JTextArea("===========LogCat===========\n");
		logArea.setEditable(false);
		logArea.setForeground(Color.BLACK);
		logArea.setFont(font);
		JScrollPane scrollPane = new JScrollPane(logArea);
		
		rateLabel = new JLabel("0%", JLabel.CENTER);
		rateLabel.setFont(editFont);
		
		copy = new JButton("Copy");
		clear = new JButton("Clear");
		copy.setFont(editFont);
		clear.setFont(editFont);
		copy.addActionListener(this);
		clear.addActionListener(this);

		editPane.add(rateLabel);
		editPane.add(copy);
		editPane.add(clear);

		contentPane.add(scrollPane, BorderLayout.CENTER);
		contentPane.add(editPane, BorderLayout.SOUTH);

		// TODO Ä¿Ç°¾ÓÖÐ
		this.setLocationRelativeTo(null);
		this.add(contentPane);
		this.setResizable(false);
	}

	public void appendLog(String log) {
		logArea.append(log + '\n');
	}

	public void setRate(float value) {
		String rate = String.format("%.2f%%", value * 100);
		rateLabel.setText(rate);
	}

	public void actionPerformed(ActionEvent e) {
		Object id = e.getSource();
		if (id == copy) {
			logArea.copy();
		}
		if (id == clear) {
			logArea.setText("===========LogCat===========\n");
		}
	}
	
	public void windowCloseing(WindowEvent e) {
		System.exit(0);
	}
}