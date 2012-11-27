package Boy;

import java.util.*;
import java.awt.event.*;

import Boy.Constants.Type;

public class CtrlBestFit implements ActionListener, KeyListener {
	private LinkedList<MemBlock> blockList;

	private MemFrame memFrame = null;
	
	Constants.Type type = Type.BEST_FIT;
	
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
		initList();	
		Object event = e.getSource();
		if (event == memFrame.bestAllocButton) {
			int blockSize = Constants.valueOfText(memFrame.bestAllocText);
			String memName = "Job " + memNum;
			allocMem(memName, blockSize);
		}
		if (event == memFrame.bestFreeButton) {
			int jobNum = Constants.valueOfText(memFrame.bestFreeText);
			String memName = "Job " + jobNum;
			freeMem(memName);
		}
		if (event == memFrame.bestPackButton) {
			Constants.pack(memFrame, blockList, type);
			largestSize = Constants.getLargestSize(blockList);
		}
//		if (event == memFrame.bestDemoButton)
//			System.out.println("best demo button");
//		if (event == memFrame.bestLogButton)
//			System.out.println("best log button");
	}

	public void keyReleased(KeyEvent e) {
		// TODO 添加回车事件 best-fit
		initList();
		Object event = e.getSource();
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_ENTER && event == memFrame.bestAllocText) {
			int blockSize = Constants.valueOfText(memFrame.bestAllocText);
			String memName = "Job " + memNum;
			allocMem(memName,  blockSize);
		}
		if (keyCode == KeyEvent.VK_ENTER && event == memFrame.bestFreeText) {
			int jobNum = Constants.valueOfText(memFrame.bestFreeText);
			String memName = "Job " + jobNum;
			freeMem(memName);
		}
	}
	
	private boolean allocMem(String name, int size) {
		if (size > largestSize)
			return false;
		
		int i = 0, min = 640;
		for (Iterator<MemBlock> it = blockList.iterator(); it.hasNext();) {
			MemBlock tmpBlock = (MemBlock)it.next();
			if (!tmpBlock.getUsed() && tmpBlock.size >= size) {
				if (min > tmpBlock.size - size) {
					min = tmpBlock.size - size;
					i = blockList.indexOf(tmpBlock);
				}
			}
		}

		MemBlock tmpBlock = blockList.get(i);
		int restSize = tmpBlock.size - size;
		tmpBlock.name = name;
		tmpBlock.size = size;
		tmpBlock.setUsed(true);
		usedSize += size;

		if (restSize != 0){
			tmpBlock.setBounds(2, tmpBlock.beginY, Constants.blockWidth, tmpBlock.size / Constants.factor);
			MemBlock newBlock = new MemBlock("free", restSize);
			newBlock.beginY = tmpBlock.beginY + tmpBlock.size / Constants.factor;
			newBlock.setBounds(2, newBlock.beginY, Constants.blockWidth, newBlock.size / Constants.factor);
			newBlock.setUsed(false);
			blockList.add(i + 1, newBlock);
			memFrame.bestMemPane.add(newBlock);
		}
		
		// Get the largest size that hasn't used
		largestSize = Constants.getLargestSize(blockList);

		memNum++;
		return true;
	}

	private void freeMem(String name) {
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
				if (!nextBlock.getUsed()){
					freeBlock.size += nextBlock.size;
					freeBlock.setBounds(2, freeBlock.beginY, Constants.blockWidth, freeBlock.size / Constants.factor);
					blockList.remove(nextBlock);
					memFrame.bestMemPane.remove(nextBlock);
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
					memFrame.bestMemPane.remove(freeBlock);
				}
			}
		} else {
			// TODO 检车输入正确的内存块号
			System.out.println("free block == null");
		}

		// Get the largest size that hasn't used
		largestSize = Constants.getLargestSize(blockList);
	}

	// Initialize list
	public void initList() {
		if (blockList.isEmpty()) {
			MemBlock firstBlock = new MemBlock("free", largestSize);
			firstBlock.setBounds(2, 2, Constants.blockWidth, Constants.memSize / Constants.factor);
			blockList.add(firstBlock);
			memFrame.bestMemPane.add(firstBlock);
		}
	}

	public void keyPressed(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {

	}
}