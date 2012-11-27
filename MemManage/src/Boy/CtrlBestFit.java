package Boy;

import java.util.*;
import java.awt.event.*;

public class CtrlBestFit implements ActionListener, KeyListener {
	private List<MemBlock> blockList;

	private MemFrame memFrame = null;
	
	// blockSize块需要的内存   UsedSize已使用的内存  largestSize最大容量内存
	private int memNum, usedSize, largestSize;
	
	public CtrlBestFit(MemFrame memFrame) {
		this.memFrame = memFrame;
		memNum = 0;
		usedSize = 0;
		largestSize = Constants.memSize;
		blockList = new LinkedList<MemBlock>();
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO 添加按钮委托 best-fit
		Object event = e.getSource();
		if (event == memFrame.bestAllocButton)
			System.out.println("best alloc button");
		if (event == memFrame.bestFreeButton)
			System.out.println("best free button");
		if (event == memFrame.bestPackButton)
			System.out.println("best pack button");
//		if (event == memFrame.bestDemoButton)
//			System.out.println("best demo button");
//		if (event == memFrame.bestLogButton)
//			System.out.println("best log button");
	}

	public void keyPressed(KeyEvent e) {
		
	}

	public void keyReleased(KeyEvent e) {
		// TODO 添加回车事件 best-fit
		if (e.getSource() == memFrame.bestAllocText)
			System.out.println("best alloc text");
		if (e.getSource() == memFrame.bestFreeText)
			System.out.println("best free text");
	}

	public void keyTyped(KeyEvent e) {
		
	}
}