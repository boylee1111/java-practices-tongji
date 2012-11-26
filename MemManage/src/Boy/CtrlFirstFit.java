package Boy;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class CtrlFirstFit implements ActionListener, KeyListener {
	private List<MemBlock> firstList;
	private List<MemBlock> freeList;
	
	private MemFrame memFrame = null;
	
	private int memNum, freeNum, blockSize;

	public CtrlFirstFit(MemFrame memFrame) {
		this.memFrame = memFrame;
		memNum = freeNum = 0;
		blockSize = 0;
//		freeList.add(new MemBlock(freeNum++, 640));
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO 添加按钮委托 first-fit
		Object event = e.getSource();
		if (event == memFrame.firstAllocButton) {
			System.out.println("first alloc button");
			// TODO 判断内存是否足够
			memNum = 0;
			blockSize = valueOfText();
			JPanel memBlock = new MemBlock(memNum, blockSize);
			JPanel memBlock2 = new MemBlock(memNum, blockSize);
			memBlock.setBounds(50, 100, 50 ,320);
			memBlock2.setBounds(250, 100, 50 ,320);
			memFrame.add(memBlock);
			memFrame.add(memBlock2);
			System.out.println(valueOfText());
		}
		if (event == memFrame.firstFreeButton)
			System.out.println("first free button");
//		if (event == memFrame.firstDemoButton)
//			System.out.println("first demo button");
//		if (event == memFrame.firstPackButton)
//			System.out.println("first pack button");
//		if (event == memFrame.firstLogButton)
//			System.out.println("first log button");
	}

	public void keyPressed(KeyEvent e) {
		
	}

	public void keyReleased(KeyEvent e) {
		// TODO 添加回车事件 first-fit
		Object event = e.getSource();
		int keyCode = e.getKeyCode();
		if (event == memFrame.firstAllocText && keyCode == KeyEvent.VK_ENTER) {
			System.out.println("first alloc text");
		}
		if (event == memFrame.firstFreeText && keyCode == KeyEvent.VK_ENTER) {
			System.out.println("first free text");
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}
	
	public int valueOfText() {
		int value = 0;
		try {
			value = Integer.valueOf(memFrame.firstAllocText.getText().toString());
		} catch (NumberFormatException e) {
			// TODO 处理输入非数字的异常
			System.out.println("请输入数字");
		}
		return value;
	}
}