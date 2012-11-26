package Boy;
// Constants.java -- Definition of constants

public class Constants {
	// Coefficient of velocity in different direction
	static int w1 = 1;
	static int s1 = 1;
	static int e1 = 1;
	static int n1 = 1;
	
	static double sleepTime = 2000; // Control the internal time between two car
	static int Coefficient = 1; // Use to have fun
	static int ifSpecial = 1; // Whether automatic car
	
	// initial coordinates of special cars
	static int westEastSpecialX = 0;
	static int westEastSpecialY = 303;
	static int southNorthSpecialX = 303;
	static int southNorthSpecialY = 600;
	static int eastWestSpecialX = 600;
	static int eastWestSpecialY = 278;
	static int northSouthSpecialX = 278;
	static int northSouthSpecialY = 0;
	
	// initial coordinates of normal cars that don't turn right
	static int westEastX = 0;
	static int westEastY = 328;
	static int southNorthX = 328;
	static int southNorthY = 600;
	static int eastWestX = 600;
	static int eastWestY = 253;
	static int northSouthX = 253;
	static int northSouthY = 0;
	
	// initial coordinates of normal cars that turn right
	static int westEastTurnX = 0;
	static int westEastTurnY = 353;
	static int southNorthTurnX = 353;
	static int southNorthturnY = 600;
	static int eastWestTurnX = 600;
	static int eastWestTurnY = 228;
	static int northSouthTurnX = 228;
	static int northSouthTurnY = 0;
}