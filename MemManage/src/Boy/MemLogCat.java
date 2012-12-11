package boy;

// LogCat.java -- Record the every step
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import boy.Constants.AlgoType;

public class MemLogCat extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane, editPane;
	private JScrollPane scrollPane;
	
	private Font font, editFont;
	
	private JLabel editLabel1, editLabel2, rateLabel;
	private JButton select, copy, clear;
	
	private JTextArea logArea;
	
	AlgoType type;
	
	public MemLogCat() {
		this.setSize(Constants.logCatFrame);
		contentPane = new JPanel();
		editPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		editPane.setLayout(new GridLayout(2, 3));
		
		font = new Font("Calibri", Font.BOLD, 20);
		editFont = new Font("Times New Roman", Font.BOLD, 25);

		logArea = new JTextArea("===========LogCat===========\n");
		logArea.setEditable(false);
		logArea.setForeground(Color.BLACK);
		logArea.setFont(font);
		scrollPane = new JScrollPane(logArea);
		
		editLabel1 = new JLabel("Memory", JLabel.CENTER);
		editLabel1.setFont(editFont);
		editLabel2 = new JLabel(" Used:", JLabel.CENTER);
		editLabel2.setFont(editFont);
		rateLabel = new JLabel("0%", JLabel.CENTER);
		rateLabel.setForeground(Color.GREEN);
		rateLabel.setFont(editFont);
		
		select = new JButton("Select");
		copy = new JButton("Copy");
		clear = new JButton("Clear");
		select.setFont(editFont);
		copy.setFont(editFont);
		clear.setFont(editFont);
		select.addActionListener(this);
		copy.addActionListener(this);
		clear.addActionListener(this);

		editPane.add(editLabel1);
		editPane.add(editLabel2);
		editPane.add(rateLabel);
		editPane.add(select);
		editPane.add(copy);
		editPane.add(clear);

		contentPane.add(scrollPane, BorderLayout.CENTER);
		contentPane.add(editPane, BorderLayout.SOUTH);

		this.add(contentPane);
		this.setResizable(false);
	}

	public void appendLog(String log) {
		logArea.append(log + '\n');
		logArea.setCaretPosition(logArea.getText().length());
	}

	public void setRate(float value) {
		if (value < 0.5) {
			rateLabel.setForeground(Color.GREEN);
		} else if (value > 0.5 && value < 0.9) {
			rateLabel.setForeground(Color.YELLOW);
		} else {
			rateLabel.setForeground(Color.RED);
		}
		String rate = String.format("%.2f%%", value * 100);
		rateLabel.setText(rate);
	}

	public void actionPerformed(ActionEvent e) {
		Object id = e.getSource();
		if (id == select) {
			logArea.selectAll();
		}
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