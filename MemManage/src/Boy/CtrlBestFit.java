package Boy;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import Boy.Constants.Type;

public class CtrlBestFit implements ActionListener, KeyListener {
	private LinkedList<MemBlock> blockList;

	private MemFrame memFrame = null;
	
	MemLogCat logCat = null;
	
	Constants.Type type = Type.BEST_FIT;
	
	// blockSize块需要的内存   UsedSize已使用的内存  largestSize最大容量内存
	private int memNum, largestSize;
	
	private float usedSize;
	
	public CtrlBestFit(MemFrame memFrame) {
		this.memFrame = memFrame;
		memNum = 0;
		usedSize = 0;
		largestSize = Constants.memSize;
		blockList = new LinkedList<MemBlock>();
		logCat = new MemLogCat();
		logCat.setTitle("Best-Fit LogCat");
	}
	
	public void actionPerformed(ActionEvent e) {
		initList();	
		Object event = e.getSource();
		if (event == memFrame.bestAllocButton) {
			int blockSize = Constants.valueOfText(memFrame.bestAllocText);
			if (blockSize != -1) {
				String memName = "Job " + memNum;
				if (!allocMem(memName,  blockSize)) {
					logCat.appendLog("Allocate unsuccessfully!");
				}
			}
		}
		if (event == memFrame.bestFreeButton) {
			int jobNum = Constants.valueOfText(memFrame.bestFreeText);
			if (jobNum != -1){
				String memName = "Job " + jobNum;
				freeMem(memName);
			}
		}
		if (event == memFrame.bestPackButton) {
			if (!Constants.pack(memFrame, blockList, type)) {
				JOptionPane.showMessageDialog(null, 
						"Please allocte memory first",
						"Error",
						JOptionPane.WARNING_MESSAGE);
			} else {
				largestSize = Constants.getLargestSize(blockList);
			}
		}
//		if (event == memFrame.firstDemoButton) {
//			System.out.println("first demo button");
//		}
		if (event == memFrame.bestClearButton) {
			Constants.clear(memFrame, blockList, type);
		}
		if (event == memFrame.bestLogButton) {
			logCat.setVisible(true);
		}
	}

	public void keyReleased(KeyEvent e) {
		initList();
		Object event = e.getSource();
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_ENTER && event == memFrame.bestAllocText) {
			int blockSize = Constants.valueOfText(memFrame.bestAllocText);
			if (blockSize != -1) {
				String memName = "Job " + memNum;
				if (!allocMem(memName,  blockSize)) {
					logCat.appendLog("Allocation unsuccessfully!");
				}
			}
		}
		if (keyCode == KeyEvent.VK_ENTER && event == memFrame.bestFreeText) {
			int jobNum = Constants.valueOfText(memFrame.bestFreeText);
			String memName = "Job " + jobNum;
			freeMem(memName);
		}
	}
	
	private boolean allocMem(String name, int size) {
		if (size > largestSize) {
			JOptionPane.showMessageDialog(null, 
					"Memory shortage, please try packing then allocate.",
					"Error",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (size < Constants.memLeast) {
			JOptionPane.showMessageDialog(null, 
					"Please allocate 10K at least.",
					"Error",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
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
		logCat.appendLog("Allocate " + size + "K successfully!");
		logCat.setRate(usedSize / Constants.memSize);

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
				if (!preBlock.getUsed()) {
					preBlock.size += freeBlock.size;
					preBlock.setBounds(2, preBlock.beginY, Constants.blockWidth, preBlock.size / Constants.factor );
					blockList.remove(freeBlock);
					memFrame.bestMemPane.remove(freeBlock);
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, 
					"NO " + name + "!",
					"Error",
					JOptionPane.WARNING_MESSAGE);
			logCat.appendLog("Free unsuccessfully!");
			return;
		}

		// Get the largest size that hasn't used
		largestSize = Constants.getLargestSize(blockList);
		logCat.appendLog("Free " + name + "# successfully!");
		logCat.setRate(usedSize / Constants.memSize);
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