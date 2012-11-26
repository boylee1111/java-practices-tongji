package Boy;
// CheckCar.java -- Remove the car out of screen
import java.util.*;

public class CheckCar implements Runnable {
	private BackgroundPanel bgPanel;
	
	public CheckCar(final BackgroundPanel bgPanel) {
		this.bgPanel = bgPanel;
	}
	
	public void run() {
		while (true) {
			try {
				Thread.currentThread();
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			synchronized(bgPanel.carList) {
				for (Iterator<Car> it = bgPanel.carList.iterator(); it.hasNext();) {
					Car tmpCar = (Car)it.next();
					// Thread of car has dead
					if (!(tmpCar.equals(null) || tmpCar.isAlive()) || (tmpCar.x < 225 && tmpCar.y < 225))
						it.remove();
					// Wait for a long time, clear
					if (tmpCar.hasWait && (tmpCar.x == 0 || tmpCar.x == 600 || tmpCar.y == 0 || tmpCar.y == 600))
						it.remove();
					if (!tmpCar.hasWait && (tmpCar.x == 0 || tmpCar.x == 600 || tmpCar.y == 0 || tmpCar.y == 600))
						if ((int)(20 * Math.random()) == 1)
							tmpCar.hasWait = true;
				}
			}
		}
	}
}
