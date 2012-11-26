package Boy;

import java.awt.*;
import javax.swing.*;

public class MemBlock extends JLabel {
	private static final long serialVersionUID = 1L;

	private boolean isUsed;
	
	int beginY;
	
	int num, size;
	
	public MemBlock(int num, int size) {
		this.num = num;
		this.size = size;
		beginY = 2;
		isUsed = false;
		super.setBackground(Color.BLUE);
	}
	
	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}
	
	public boolean getUsed() {
		return isUsed;
	}
}