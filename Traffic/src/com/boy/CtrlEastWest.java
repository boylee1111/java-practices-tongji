package com.boy;
// CtrlNorthSouth.java -- Control the east-west traffic light independently
import java.util.*;
import java.awt.*;

public class CtrlEastWest implements Runnable {
	private int restTime, totalTime = 0; // re
	private String t = "", rest = "";
	private BackgroundPanel bgPanel;
	Date date;
	
	boolean flag = true; // Used to control north-south light and east-west light
	
	public CtrlEastWest(final BackgroundPanel bgPanel) {
		this.bgPanel = bgPanel;
	}
	
	public void run() {
		while (true) {
			synchronized(this) {
				if (!flag) { // If flag = false, wait
					try {
						this.wait();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				// Change the coefficient
				Constants.n1 = 0;
				Constants.s1 = 0;
				Constants.e1 = 1;
				Constants.w1 = 1;
				// Change the color of traffic light
				bgPanel.northLabel1.setForeground(Color.GREEN);
				bgPanel.northLabel2.setForeground(Color.RED);
				bgPanel.northLabel3.setForeground(Color.RED);
				bgPanel.northTF.setForeground(Color.RED);
				bgPanel.southLabel1.setForeground(Color.RED);
				bgPanel.southLabel2.setForeground(Color.RED);
				bgPanel.southLabel3.setForeground(Color.GREEN);
				bgPanel.southTF.setForeground(Color.RED);
				bgPanel.eastLabel1.setForeground(Color.GREEN);
				bgPanel.eastLabel2.setForeground(Color.GREEN);
				bgPanel.eastLabel3.setForeground(Color.GREEN);
				bgPanel.eastTF.setForeground(Color.GREEN);
				bgPanel.westLabel1.setForeground(Color.GREEN);
				bgPanel.westLabel2.setForeground(Color.GREEN);
				bgPanel.westLabel3.setForeground(Color.GREEN);
				bgPanel.westTF.setForeground(Color.GREEN);
				// Reset the rest time of traffic light
				totalTime = Integer.parseInt(bgPanel.dateTF.getText());
				if (totalTime != 0)
					restTime = totalTime + 1;
				else
					restTime = 21;
				t.indexOf(restTime);
				bgPanel.northTF.setText(t);
				bgPanel.southTF.setText(t);
				bgPanel.eastTF.setText(t);
				bgPanel.westTF.setText(t);
				
				// Run the traffic light
				while (true) {
					date = new Date();
					bgPanel.dateLabel.setText(date.toString());
					try {
						Thread.currentThread();
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (restTime == 8) { // Change to yellow
						bgPanel.eastLabel2.setForeground(Color.YELLOW);
						bgPanel.eastLabel3.setForeground(Color.YELLOW);
						bgPanel.eastTF.setForeground(Color.YELLOW);
						bgPanel.westLabel1.setForeground(Color.YELLOW);
						bgPanel.westLabel2.setForeground(Color.YELLOW);
						bgPanel.westTF.setForeground(Color.YELLOW);
						// If yellow, stop
						Constants.e1 = 0;
						Constants.w1 = 0;
					}
					else 
						if (restTime == 1) // Time is over
							break;
					restTime--;
					// Make sure the format of display
					if (restTime < 10)
						rest = "0" + String.valueOf(restTime);
					else
						rest = String.valueOf(restTime);
					bgPanel.northTF.setText(rest);
					bgPanel.southTF.setText(rest);
					bgPanel.eastTF.setText(rest);
					bgPanel.westTF.setText(rest);
				}
				flag = false;
				this.notify();
			}
		}
	}
}
