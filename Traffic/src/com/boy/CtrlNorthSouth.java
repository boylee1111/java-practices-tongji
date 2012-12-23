package com.boy;
// CtrlNorthSouth.java -- Control the north-south traffic light, depends on east-west traffic light 
import java.util.*;
import java.awt.*;

public class CtrlNorthSouth implements Runnable {
	private int restTime, totalTime = 0;
	private String t = "", rest = "";
	private BackgroundPanel bgPanel;
	private CtrlEastWest eastWest;
	Date date;
	
	public CtrlNorthSouth(final BackgroundPanel bgPanel, CtrlEastWest eastWest) {
		this.bgPanel = bgPanel;
		this.eastWest = eastWest;
	}
	
	public void run() {
		while (true) {
			synchronized(eastWest) { // Synchronized with east-west traffic light
				if (eastWest.flag) {
					try {
						eastWest.wait();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				// Change the coefficient
				Constants.n1 = 1;
				Constants.s1 = 1;
				Constants.e1 = 0;
				Constants.w1 = 0;
				// Change the color of traffic light
				bgPanel.northLabel1.setForeground(Color.GREEN);
				bgPanel.northLabel2.setForeground(Color.GREEN);
				bgPanel.northLabel3.setForeground(Color.GREEN);
				bgPanel.northTF.setForeground(Color.GREEN);
				bgPanel.southLabel1.setForeground(Color.GREEN);
				bgPanel.southLabel2.setForeground(Color.GREEN);
				bgPanel.southLabel3.setForeground(Color.GREEN);
				bgPanel.southTF.setForeground(Color.GREEN);
				bgPanel.eastLabel1.setForeground(Color.RED);
				bgPanel.eastLabel2.setForeground(Color.RED);
				bgPanel.eastLabel3.setForeground(Color.GREEN);
				bgPanel.eastTF.setForeground(Color.RED);
				bgPanel.westLabel1.setForeground(Color.GREEN);
				bgPanel.westLabel2.setForeground(Color.RED);
				bgPanel.westLabel3.setForeground(Color.RED);
				bgPanel.westTF.setForeground(Color.RED);
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
						bgPanel.northLabel2.setForeground(Color.YELLOW);
						bgPanel.northLabel3.setForeground(Color.YELLOW);
						bgPanel.northTF.setForeground(Color.YELLOW);
						bgPanel.southLabel1.setForeground(Color.YELLOW);
						bgPanel.southLabel2.setForeground(Color.YELLOW);
						bgPanel.southTF.setForeground(Color.YELLOW);
						// If yellow, stop
						Constants.n1 = 0;
						Constants.s1 = 0;
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
					eastWest.flag = true;
					eastWest.notify();
				}
			}
		}
	}
}
