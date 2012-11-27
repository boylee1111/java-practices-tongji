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
			MemBlock firstBlock = new MemBlock("free", largestSize);
			firstBlock.setBounds(2, 2, Constants.blockWidth, Constants.memSize / Constants.factor);
			blockList.add(firstBlock);
			memFrame.firstMemPane.add(firstBlock);
		}
		Object event = e.getSource();
		if (event == memFrame.firstAllocButton) {
			int blockSize = Constants.valueOfText(memFrame.firstAllocText);
			String memName = "Job " + memNum;
			allocMem(memName,  blockSize);
		}
		if (event == memFrame.firstFreeButton) {
			int jobNum = Constants.valueOfText(memFrame.firstFreeText);
			String memName = "Job " + jobNum;
			freeMem(memName);
		}
		if (event == memFrame.firstPackButton) {
			System.out.println("first pack button");
			Constants.pack(memFrame, blockList);
		}
//		if (event == memFrame.firstDemoButton)
//			System.out.println("first demo button");
//		if (event == memFrame.firstLogButton)
//			System.out.println("first log button");
	}

	public void keyReleased(KeyEvent e) {
		// TODO 添加回车事件 first-fit
		if (blockList.isEmpty()) {
			MemBlock firstBlock = new MemBlock("free", largestSize);
			firstBlock.setBounds(2, 2, Constants.blockWidth, Constants.memSize / Constants.factor);
			blockList.add(firstBlock);
			memFrame.firstMemPane.add(firstBlock);
		}
		
		Object event = e.getSource();
		int keyCode = e.getKeyCode();
		if (event == memFrame.firstAllocText && keyCode == KeyEvent.VK_ENTER) {
			int blockSize = Constants.valueOfText(memFrame.firstAllocText);
			String memName = "Job " + memNum;
			allocMem(memName,  blockSize);
		}
		if (event == memFrame.firstFreeText && keyCode == KeyEvent.VK_ENTER) {
			int jobNum = Constants.valueOfText(memFrame.firstFreeText);
			String memName = "Job " + jobNum;
			freeMem(memName);
		}
	}
	
	public boolean allocMem(String name, int size) {
		// TODO 分配失败
		if (size > largestSize)
			return false;
		
		int i = 0;
		MemBlock newBlock = null;
		for (Iterator<MemBlock> it = blockList.iterator(); it.hasNext(); i++) {
			MemBlock tmpBlock = (MemBlock)it.next();
			if (tmpBlock.size >= size && !tmpBlock.getUsed()) {
				int restSize = tmpBlock.size - size;
				tmpBlock.name = name;
				tmpBlock.size = size;
				tmpBlock.setUsed(true);
				usedSize += size;

				if (restSize != 0){
					tmpBlock.setBounds(2, tmpBlock.beginY, Constants.blockWidth, tmpBlock.size / Constants.factor);
					newBlock = new MemBlock("free", restSize);
					newBlock.beginY = tmpBlock.beginY + tmpBlock.size / Constants.factor;
					newBlock.setBounds(2, newBlock.beginY, Constants.blockWidth, newBlock.size / Constants.factor);
					newBlock.setUsed(false);
					blockList.add(i + 1, newBlock);
					memFrame.firstMemPane.add(newBlock);
				}
				break;
			}
		}
		
		// Get the largest size that hasn't used
		largestSize = Constants.getLargestSize(blockList);
		
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
					freeBlock.setBounds(2, freeBlock.beginY, Constants.blockWidth, freeBlock.size / Constants.factor);
					blockList.remove(nextBlock);
					memFrame.firstMemPane.remove(nextBlock);
				}
			}
			
			i = blockList.indexOf(freeBlock);
			// i > 0, has previous
			if (i > 0) {
				preBlock = blockList.get(i - 1);
				// TODO 前驱为空闲内存
				if (!preBlock.getUsed()) {
					preBlock.size += freeBlock.size;
					preBlock.setBounds(2, preBlock.beginY, Constants.blockWidth, preBlock.size / Constants.factor );
					blockList.remove(freeBlock);
					memFrame.firstMemPane.remove(freeBlock);
				}
			}
		} else {
			// TODO 检车输入正确的内存块号
			System.out.println("free block == null");
		}
		
		// Get the largest size that hasn't used
		largestSize = Constants.getLargestSize(blockList);
		
		return true;
	}
	
	public void keyPressed(KeyEvent e) {
		
	}
	
	public void keyTyped(KeyEvent e) {
		
	}
}