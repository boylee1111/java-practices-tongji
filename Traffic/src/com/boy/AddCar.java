package com.boy;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

// AddCar.java -- Class that used to add new car to screen

public class AddCar implements Runnable {
	private BackgroundPanel bgPanel;
	private String szFlow = "500";
	int flow; // Max flow of cars
	String carNum = ""; // Show current number of cars in screen
	

	
	public AddCar(final BackgroundPanel bgPanel) {
		this.bgPanel = bgPanel;
		
		

		bgPanel.northCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Car newCar = new Car(bgPanel);
				newCar.type = 0;
				newCar.fromTo = 4;
				newCar.color = Color.RED;
				bgPanel.northCar.setEnabled(false);
				AddToList(newCar);
			}
		});
		
		bgPanel.southCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Car newCar = new Car(bgPanel);
				newCar.type = 0;
				newCar.fromTo = 2;
				newCar.color = Color.RED;
				bgPanel.southCar.setEnabled(false);
				AddToList(newCar);
			}
		});
		
		bgPanel.westCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Car newCar = new Car(bgPanel);
				newCar.type = 0;
				newCar.fromTo = 1;
				newCar.color = Color.RED;
				bgPanel.westCar.setEnabled(false);
				AddToList(newCar);
			}
		});
		
		bgPanel.eastCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Car newCar = new Car(bgPanel);
				newCar.type = 0;
				newCar.fromTo = 3;
				newCar.color = Color.RED;
				bgPanel.eastCar.setEnabled(false);
				AddToList(newCar);
			}
		});
		
		bgPanel.auto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Constants.ifSpecial = 0;
			}
		});
		
		bgPanel.nonAuto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Constants.ifSpecial = 1;
				synchronized(bgPanel.carList) {
					for (Iterator<Car> it = bgPanel.carList.iterator(); it.hasNext();) {
						Car tmpCar = (Car)it.next();
						if (tmpCar.type == 0 && (tmpCar.x == 0 || tmpCar.x == 600 || tmpCar.y == 0 || tmpCar.y == 600))
							it.remove();
					}
				}
			}
		});
	}
	
	// Use for many class
	public  void AddToList(Car newCar) {
		if (newCar.type == 0) {
			if (newCar.fromTo == 1) { // west to east
				newCar.x = Constants.westEastSpecialX;
				newCar.y = Constants.westEastSpecialY;
			}
			else if (newCar.fromTo == 2) { // south to north
				newCar.x = Constants.southNorthSpecialX;
				newCar.y = Constants.southNorthSpecialY;
			}
			else if (newCar.fromTo == 3) { // east to west
				newCar.x = Constants.eastWestSpecialX;
				newCar.y = Constants.eastWestSpecialY;
			}
			else if (newCar.fromTo == 4) { // north to south
				newCar.x = Constants.northSouthSpecialX;
				newCar.y = Constants.northSouthSpecialY;
			}
		}
		else { // normal cars
			if (newCar.fromTo == 1) { // west to east
				if (newCar.turnOff == 1) {
					newCar.x = Constants.westEastTurnX;
					newCar.y = Constants.westEastTurnY;
				}
				else {
					newCar.x = Constants.westEastX;
					newCar.y = Constants.westEastY;
				}
			}
			else if (newCar.fromTo == 2) { // south to north
				if (newCar.turnOff == 1) {
					newCar.x = Constants.southNorthTurnX;
					newCar.y = Constants.southNorthturnY;
				}
				else {
					newCar.x = Constants.southNorthX;
					newCar.y = Constants.southNorthY;
				}
			}
			else if (newCar.fromTo == 3) { // east to west
				if (newCar.turnOff == 1) {
					newCar.x = Constants.eastWestTurnX;
					newCar.y = Constants.eastWestTurnY;
				}
				else {
					newCar.x = Constants.eastWestX;
					newCar.y = Constants.eastWestY;
				}
			}
			else if (newCar.fromTo == 4) { // north to south
				if (newCar.turnOff == 1) {
					newCar.x = Constants.northSouthTurnX;
					newCar.y = Constants.northSouthTurnY;
				}
				else {
					newCar.x = Constants.northSouthX;
					newCar.y = Constants.northSouthY;
				}
			}
		}
		synchronized(bgPanel.carList) {
			bgPanel.carList.add(newCar);
		}
		carNum = String.valueOf(bgPanel.carList.size());
		if (bgPanel.carList.size() < 10)
			carNum = "0" + carNum;
		try {
			bgPanel.carNumTF.setText(carNum); // Show the current number of cars in screen
		} catch (Exception e) {
			carNum = "500";
		}					
		newCar.start();
	}
	
	public void run() {
		while (true) {
			synchronized(this) {
				while (true) {
					try {
						Thread.currentThread();
						Thread.sleep((int)(1000 * Math.random() * Constants.Coefficient + Constants.sleepTime));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					flow = Integer.parseInt(szFlow);
					if (bgPanel.carList.size() < flow) {
						Car newCar = new Car(bgPanel);
						AddToList(newCar);
					}
				}
			}
		}
	}
}
