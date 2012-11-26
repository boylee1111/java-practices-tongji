package Boy;

import java.awt.*;
import javax.swing.*;

public class MemBlock extends JLabel {
	private final int width = 320;
	
	int num, size;
	
	public MemBlock(int num, int size) {
		this.num = num;
		this.size = size;
		super.setBackground(Color.BLUE);
	}
}