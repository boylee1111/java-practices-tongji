package Boy;

import java.util.*;
import java.awt.event.*;

public class CtrlFirstFit implements ActionListener, KeyListener {
	private List<MemBlock> firtList;
	
	private MemFrame memFrame = null;

	public CtrlFirstFit(MemFrame memFrame) {
		this.memFrame = memFrame;
	}
	
	public void actionPerformed(ActionEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		
	}

	public void keyReleased(KeyEvent e) {
//		System.out.println(e.getSource());
		if (e.getSource() == memFrame.firstAllocText)
			System.out.println("first alloc text");
	}

	public void keyTyped(KeyEvent e) {
		
	}
}