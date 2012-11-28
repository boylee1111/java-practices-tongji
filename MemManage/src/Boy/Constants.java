package Boy;

import java.awt.*;
import java.util.*;
// Constants.java -- Definition of constants

import javax.swing.JTextField;

public class Constants {
	static int memSize = 640;
	static int factor = 2;
	
	//static int firstBlockX = 100;
	//static int bestBlockX = 500;
	
	static int blockWidth = 200;
	
	static Dimension mainFrame = new Dimension(800, 600);
	static Dimension logCatFrame = new Dimension(300, 500);
	static Rectangle firstPaneRec = new Rectangle(98, 58, 204, 324);
	static Rectangle bestPaneRec = new Rectangle(498, 58, 204, 324);
	
	enum Type {FIRST_FIT, BEST_FIT};
	
	// Get the integer value of text field
	public static int valueOfText(JTextField TF) {
		int value = 0;
		try {
			if (TF.getText().toString().equals("")) {
				System.out.println("It's black");
				value = -1;
			}
			value = Integer.valueOf(TF.getText().toString());
		} catch (NumberFormatException e) {
			// TODO 处理输入非数字的异常
			System.out.println("请输入数字");
		}
		return value;
	}
	
	// Get the largest size in list
	public static int getLargestSize(LinkedList<MemBlock> list) {
		int max = 0;
		for (Iterator<MemBlock> it = list.iterator(); it.hasNext();) {
			MemBlock tmpBlock = (MemBlock)it.next();
			if (!tmpBlock.getUsed() && tmpBlock.size > max)
				max = tmpBlock.size;
		}
		return max;
	}
	
	// Pack algorithm
	public static boolean pack(MemFrame memFrame, LinkedList<MemBlock> list, Type type) {
		MemBlock firstBlock = list.getFirst();
		// If the first block is free, make the second block be the first
		if (!firstBlock.getUsed()) {
			list.remove(firstBlock);
			switch (type) {
			case FIRST_FIT:
				memFrame.firstMemPane.remove(firstBlock);
				break;
			case BEST_FIT:
				memFrame.bestMemPane.remove(firstBlock);
				break;
			}
			firstBlock = list.getFirst();
			firstBlock.setBounds(2, 2, blockWidth, firstBlock.size / factor);
		}

		int currentPos = 2;
		LinkedList<MemBlock> toDelete = new LinkedList<MemBlock>();
		for (Iterator<MemBlock> it = list.iterator(); it.hasNext();) {
			MemBlock tmpBlock = (MemBlock)it.next();
			if (tmpBlock == list.getLast())
				break;
			if (tmpBlock.getUsed()) {
				if (tmpBlock.beginY != currentPos) {
					tmpBlock.beginY = currentPos;
					tmpBlock.setBounds(2, tmpBlock.beginY, blockWidth, tmpBlock.size / factor);
				}
				currentPos += tmpBlock.size / factor;
				continue;
			} else {
				switch (type) {
				case FIRST_FIT:
					memFrame.firstMemPane.remove(tmpBlock);
					break;
				case BEST_FIT:
					memFrame.bestMemPane.remove(tmpBlock);
					break;
				}
				toDelete.add(tmpBlock);
			}
		}
		list.removeAll(toDelete);
		
		// Deal with the last free block
		MemBlock lastBlock= list.getLast();
		if (lastBlock.getUsed()) {
			lastBlock.setBounds(2, currentPos, blockWidth, lastBlock.size / factor);
			currentPos += lastBlock.size / factor;
			
			MemBlock freeBlock = new MemBlock("free", 2 * (322 - currentPos));
			freeBlock.beginY = currentPos;
			freeBlock.setBounds(2, freeBlock.beginY, blockWidth, freeBlock.size / factor);
			freeBlock.setUsed(false);
			list.add(freeBlock);
			switch (type) {
			case FIRST_FIT:
				memFrame.firstMemPane.add(freeBlock);
				break;
			case BEST_FIT:
				memFrame.bestMemPane.add(freeBlock);
				break;
			}
		} else {
			lastBlock.size = 2 * (322 - currentPos);
			lastBlock.beginY = currentPos;
			lastBlock.setBounds(2, lastBlock.beginY, blockWidth, lastBlock.size / factor);
		}
		
		switch (type) {
		case FIRST_FIT:
			memFrame.firstFit.logCat.appendLog("Memory pack successfully!");
			break;
		case BEST_FIT:
			memFrame.bestFit.logCat.appendLog("Memory pack successfully!");
		}

		return true;
	}
}