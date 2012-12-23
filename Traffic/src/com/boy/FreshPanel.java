package com.boy;
// FreshPanel.java -- Used to fresh the panel, prevent flicker
import java.awt.*;

public class FreshPanel implements Runnable {
	private BackgroundPanel bgPanel;
	private int i;
	
	public FreshPanel(final BackgroundPanel bgPanel) {
		this.bgPanel = bgPanel;
		i = 0;
	}
	
	public void run() {
		while (true) {
			bgPanel.repaint(); // Repaint the panel
			if (bgPanel.carList.size() == 500) { // A special mode
				bgPanel.carNumTF.setText("Made In China");
				bgPanel.carNumTF.setFont(new Font("ScansSerif", Font.BOLD, 72));
				bgPanel.carNumTF.setForeground(Color.RED);
			}
			else {
				bgPanel.carNumTF.setFont(bgPanel.infoFont);
				bgPanel.carNumTF.setForeground(bgPanel.carNumLabel.getForeground());
			}
			i++;
			if (i == 100) {
				bgPanel.northCar.setEnabled(true);
				bgPanel.southCar.setEnabled(true);
				bgPanel.westCar.setEnabled(true);
				bgPanel.eastCar.setEnabled(true);
				i = 0;
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
