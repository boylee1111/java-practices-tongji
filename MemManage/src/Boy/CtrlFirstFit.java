package Boy;


import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class CtrlFirstFit implements ActionListener, KeyListener {
	private LinkedList<MemBlock> blockList;
	
	private MemFrame memFrame = null;
	
	// blockSize块需要的内存   UsedSize已使用的内存  largestSize最大容量内存
	private int memNum, usedSize, largestSize;

	public CtrlFirstFit(MemFrame memFrame) {
		this.memFrame = memFrame;
		memNum = 0;
		usedSize = 0;
		largestSize = Constants.memSize;
		blockList = new LinkedList<MemBlock>();
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO 添加按钮委托 first-fit
		if (blockList.isEmpty()) {
			System.out.println("I'm empty");
			MemBlock firstBlock = new MemBlock("free", largestSize);
			firstBlock.setBounds(2, 2, Constants.blockWidth, Constants.memSize / 2);
			blockList.add(firstBlock);
			memFrame.firstMemPane.add(firstBlock);
		}
		Object event = e.getSource();
		if (event == memFrame.firstAllocButton) {
//			System.out.println("first alloc button");
			int blockSize = valueOfText(memFrame.firstAllocText);
			String memName = "Job " + memNum;
			System.out.println(memName);
			allocMem(memName,  blockSize);
//			System.out.println(valueOfText(memFrame.firstAllocText));
		}
		if (event == memFrame.firstFreeButton) {
//			System.out.println("first free button");
			int jobNum = valueOfText(memFrame.firstFreeText);
			String memName = "Job " + jobNum;
//			System.out.println(memName);
			freeMem(memName);
		}
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
	
	public int valueOfText(JTextField TF) {
		int value = 0;
		try {
			value = Integer.valueOf(TF.getText().toString());
		} catch (NumberFormatException e) {
			// TODO 处理输入非数字的异常
			System.out.println("请输入数字");
		}
		return value;
	}
	
	public int getLargestSize() {
		int max = 0;
		for (Iterator<MemBlock> it = blockList.iterator(); it.hasNext();) {
			MemBlock tmpBlock = (MemBlock)it.next();
			if (!tmpBlock.getUsed() && tmpBlock.size > max)
				max = tmpBlock.size;
		}
		return max;
	}
	
	public boolean allocMem(String name, int size) {
		// TODO 分配失败
		if (size > largestSize)
			return false;
		
		int i = 0;
		char c = 0;
		MemBlock newBlock = null;
		for (Iterator<MemBlock> it = blockList.iterator(); it.hasNext(); i++) {
			MemBlock tmpBlock = (MemBlock)it.next();
			if (tmpBlock.size >= size && !tmpBlock.getUsed()) {
				int restSize = tmpBlock.size - size;
				tmpBlock.name = name;
				tmpBlock.size = size;
				tmpBlock.setUsed(true);
				usedSize += size;
//				System.out.println(tmpBlock.beginY);
//				System.out.println(name + size);
				if (restSize == 0) {
//					System.out.println("Replace");
					c = 'R';
					break;
				} else {
//					System.out.println("Add block");
					c = 'A';
					tmpBlock.setBounds(2, tmpBlock.beginY, Constants.blockWidth, tmpBlock.size / 2);
					newBlock = new MemBlock("free", restSize);
					newBlock.setBounds(2, tmpBlock.beginY + size / 2, Constants.blockWidth, restSize / 2);
					newBlock.beginY = tmpBlock.beginY + size / 2;
					break;
				}
			}
		}
		if (c == 'A') {
			newBlock.setUsed(false);
			blockList.add(i + 1, newBlock);
			memFrame.firstMemPane.add(newBlock);
	//		System.out.println(usedSize);
			System.out.println(blockList.size());
		}

		// Get the largest size that hasn't used
		largestSize = getLargestSize();
		
		memNum++;
		return true;
	}
	
	public boolean freeMem(String name) {
		MemBlock freeBlock = null;
		for (Iterator<MemBlock> it = blockList.iterator(); it.hasNext();) {
			MemBlock tmpBlock = (MemBlock)it.next();
			if (tmpBlock.name.equals(name) && tmpBlock.getUsed())
				freeBlock = tmpBlock;
		}
		
		if (freeBlock != null) {
			int i = 0;
			freeBlock.setUsed(false);
			usedSize -= freeBlock.size;
			MemBlock preBlock = null, nextBlock = null;
			
			i = blockList.indexOf(freeBlock);
			// i < blockList.size(), has next
			if (i < blockList.size() - 1) {
				nextBlock = blockList.get(i + 1);
				// TODO 后继为空闲内存
				if (!nextBlock.getUsed() || blockList.indexOf(nextBlock) == blockList.size() - 1){
					freeBlock.size += nextBlock.size;
					freeBlock.setBounds(2, freeBlock.beginY, Constants.blockWidth, freeBlock.size / 2);
					blockList.remove(nextBlock);
				}
			}
			
			i = blockList.indexOf(freeBlock);
			// i > 0, has previous
			if (i > 0) {
				preBlock = blockList.get(i - 1);
//				System.out.println(preBlock.name);
				// TODO 前驱为空闲内存
				if (!preBlock.getUsed()) {
					preBlock.size += freeBlock.size;
					preBlock.setBounds(2, preBlock.beginY, Constants.blockWidth, preBlock.size / 2 );
					blockList.remove(freeBlock);
					System.out.println(blockList.size());
				}
			}
			for (MemBlock aaa : blockList) {
				System.out.println(aaa.name);
			}
		} else {
			System.out.println("free block == null");
		}
		
		// Get the largest size that hasn't used
		largestSize = getLargestSize();
		
		return true;
	}
}