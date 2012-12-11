package boy;
// Car.java -- Class car
import java.awt.*;
import java.util.*;

public class Car extends Thread {
	private BackgroundPanel bgPanel;
	int fromTo; // 1.west to east 2.south to north 3.east to west 4.north to south
	int x, y; // Position
	// collision = 1, collision; type = 0, special car
	int collision, type;
	int turnOff; // turnOff = 0, turn left; turnOff = 1, turn right; else go straight
	boolean hasTurn, hasWait;
	Color color; // Color of the car
	
	static int v = 1; // Velocity of the car
	
	final static int length = 20; // length of the car
	final static int width = 20; // width of the car
	
	public Car() {
	}
	
	public Car(final BackgroundPanel bgPanel) {
		this.bgPanel = bgPanel;
		fromTo = (int)(4 * Math.random() + 1.01); // Choose the direction randomly
		type = (int)(8 * Math.random() + Constants.ifSpecial); // Choose the type randomly, 0 stands for special car
		if (fromTo == 1 || fromTo == 2)
			turnOff = (int)(5 * Math.random());
		else
			turnOff = (int)(15 * Math.random());
		hasTurn = false;

		if (fromTo == 1)
			color = Color.BLACK;
		else if (fromTo == 2)
			color = Color.YELLOW;
		else if (fromTo == 3)
			color = Color.CYAN;
		else if (fromTo == 4)
			color = Color.BLUE;
		if (type == 0)
			color = Color.RED; // Color of special car
	}

	// Check whether the car is collision
	public boolean isCollision() {
		int tmpX, tmpY;
		if (type == 0) { // is special vehicles
			// Used algorithm to check
			for (Iterator<Car> it = bgPanel.carList.iterator(); it.hasNext();) {
				Car specialCar = (Car)it.next();
				if (this.equals(specialCar))
					continue;
				if (fromTo == 1) {
					tmpX = specialCar.x - x;
					tmpY = Math.abs(specialCar.y - y);
					if (specialCar.type == 0) {
						if (tmpX < 300 && tmpX > 0 && tmpY <= 20)
							return true;
					}
					else {
						if ((tmpX > 0) && (tmpX <= 23) && (tmpY <= 21))
							return true;
					}
				}
				else if (fromTo == 2) {
					tmpX = Math.abs(specialCar.x - x);
					tmpY = y - specialCar.y;
					if (specialCar.type == 0) {
						if (tmpY < 300 && tmpY > 0 && tmpX <= 20)
							return true;
					}
					else {
						if ((tmpX <= 21) && (tmpY >= 0) && (tmpY <= 23))
							return true;
					}
				}
				else if (fromTo == 3) {
					tmpX = x - specialCar.x;
					tmpY = Math.abs(specialCar.y - y);
					if (specialCar.type == 0) {
						if (tmpX < 300 && tmpX > 0 && tmpY <= 20)
							return true;
					}
					else {
						if ((tmpX >= 0) && (tmpX <= 23) && (tmpY <= 21))
							return true;
					}
				}
				else if (fromTo == 4) {
					tmpX = Math.abs(specialCar.x - x);
					tmpY = specialCar.y - y;
					if (specialCar.type == 0) {
						if (tmpY < 300 && tmpY > 0 && tmpX <= 20)
							return true;
					}
					else {
						if ((tmpX <= 21) && (tmpY >= 0) && (tmpY <= 23))
							return true;
					}
				}
			}
			return false;
		}
		// Beside the value, the method is the same as the special car
		for (Iterator<Car> it = bgPanel.carList.iterator(); it.hasNext();) {
			Car car = (Car)it.next();
			if (this.equals(car))
				continue;
			if (fromTo == 1) {
				tmpX = car.x - x;
				tmpY = Math.abs(car.y - y);
				if ((tmpX > 0) && (tmpX <= 45) && (tmpY <= 20)) {
					if (turnOff == 1) {
						if (fromTo == car.fromTo)
							return true;
						return false;
					}
					return true;
				}
			}
			else if (fromTo == 2) {
				tmpX = Math.abs(car.x - x);
				tmpY = y - car.y;
				if ((tmpX <= 20) && (tmpY > 0) && (tmpY <= 45)) {
					if (turnOff == 1) {
						if (fromTo == car.fromTo)
							return true;
						return false;
					}
					return true;
				}
			}
			else if (fromTo == 3) {
				tmpX = x - car.x;
				tmpY = Math.abs(car.y - y);
				if ((tmpX > 0) && (tmpX <= 45) && (tmpY <= 20)) {
					if (turnOff == 1) {
						if (fromTo == car.fromTo)
							return true;
						return false;
					}
					return true;
				}
			}
			else if (fromTo == 4) {
				tmpX = Math.abs(car.x - x);
				tmpY = car.y - y;
				if ((tmpX <= 20) && (tmpY > 0) && (tmpY <= 45)) {
					if (turnOff == 1) {
						if (fromTo == car.fromTo)
							return true;
						return false;
					}
					return true;
				}
			}
		}
		return false;
	}
	
	public void run() {
		while (true) {
			synchronized (bgPanel.carList) {
				if (x >= 620 || y >= 620 || x <= -20 || y <= -20) {
					x = 800;
					y = 800;
					break;
				}
				if (isCollision()) // If collision, the coefficient is 0
					collision = 0;
				else
					collision = 1;
				if (type == 0) { // Special car runs
					if (fromTo == 1)
						x += 2 * collision * v;
					else if (fromTo == 2)
						y -= 2 * collision * v;
					else if (fromTo == 3)
						x -= 2 * collision * v;
					else if (fromTo == 4)
						y += 2 * collision * v;
				}
				else { // Normal car runs
					if (fromTo == 1) {
						if (x <= 162 && x >= 159 && turnOff != 1)
							x += v * Constants.w1 * collision;
						else
							x += v * collision;
						if (!hasTurn && turnOff == 1 && x == Constants.northSouthTurnX) {
							fromTo = 4;
							hasTurn = true;
						}
						if (!hasTurn && turnOff == 0 && x == Constants.southNorthX) {
							fromTo = 2;
							hasTurn = true;
						}
					}
					if (fromTo == 2) {
						if (y >= 418 && y <= 421 && turnOff != 1)
							y -= v * Constants.s1 * collision;
						else
							y -= v * collision;
						if (!hasTurn && turnOff == 1 && y == Constants.westEastTurnY) {
							fromTo = 1;
							hasTurn = true;
						}
						if (!hasTurn && turnOff == 0 && y == Constants.eastWestY) {
							fromTo = 3;
							hasTurn = true;
						}
					}
					if (fromTo == 3) {
						if (x >= 418 && x <= 421 && turnOff != 1)
							x -= v * Constants.e1 * collision;
						else
							x -= v * collision;
						if (!hasTurn && turnOff == 1 && x == Constants.southNorthTurnX) {
							fromTo = 2;
							hasTurn = true;
						}
						if (!hasTurn && turnOff == 0 && x == Constants.northSouthX) {
							fromTo = 4;
							hasTurn = true;
						}
					}
					if (fromTo == 4) {
						if (y <= 162 && y >= 159 && turnOff != 1)
							y += v * Constants.n1 * collision;
						else
							y += v * collision;
						if (!hasTurn && turnOff == 1 && y == Constants.eastWestTurnY) {
							fromTo = 3;
							hasTurn = true;
						}
						if (!hasTurn && turnOff == 0 && y == Constants.westEastY) {
							fromTo = 1;
							hasTurn = true;
						}
					}
				}
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
