package boy;

// Constants.java -- Definition of constants
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Constants {
	// The total memory, factor of the size, the least memory can be allcated
	static int MEM_SIZE = 640;
	static int FACTOR = 1;
	static int MEM_LEAST = 10;
	
	// Size of panel
	static int BLOCK_WIDTH = 270;
	
	// Some definitions
	static Dimension LOGCAT_FRAME_NAME = new Dimension(300, 500);
	static Rectangle FIRST_PANE_REC = new Rectangle(18, 8, BLOCK_WIDTH + 4, MEM_SIZE / FACTOR + 4);
	static Rectangle BEST_PANE_REC = new Rectangle(518, 8, BLOCK_WIDTH + 4, MEM_SIZE / FACTOR + 4);
	static Rectangle FIRST_CTRL_PANE_REC = new Rectangle(295, 450, 200, 80);
	static Rectangle BEST_CTRL_PANE_REC = new Rectangle(795, 450, 200, 80);
	
	static Dimension MAIN_FRAME = new Dimension(1010, 720);
	
	// Enum used to judge which algorithm
	enum AlgoType {FIRST_FIT, BEST_FIT};

	// Get the integer value of text field
	public static int valueOfText(JTextField TF) {
		int value = 0;
		try {
			value = Integer.valueOf(TF.getText().toString());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, 
					"Please input a number",
					"Error",
					JOptionPane.WARNING_MESSAGE);
			value = -1;
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
	
	// p algorithm
	public static boolean compaction(MemFrame memFrame, LinkedList<MemBlock> list, AlgoType type) {
		if (list.size() == 1 && !list.getFirst().getUsed()) {
			return false;
		}
		
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
			firstBlock.setBounds(2, 2, BLOCK_WIDTH, firstBlock.size / FACTOR);
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
					tmpBlock.setBounds(2, tmpBlock.beginY, BLOCK_WIDTH, tmpBlock.size / FACTOR);
				}
				currentPos += tmpBlock.size / FACTOR;
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
			lastBlock.setBounds(2, currentPos, BLOCK_WIDTH, lastBlock.size / FACTOR);
			currentPos += lastBlock.size / FACTOR;
			
			MemBlock freeBlock = new MemBlock("free", FACTOR * (MEM_SIZE + 2 - currentPos));
			freeBlock.beginY = currentPos;
			freeBlock.setBounds(2, freeBlock.beginY, BLOCK_WIDTH, freeBlock.size / FACTOR);
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
			lastBlock.size = FACTOR * (MEM_SIZE + 2 - currentPos);
			lastBlock.beginY = currentPos;
			lastBlock.setBounds(2, lastBlock.beginY, BLOCK_WIDTH, lastBlock.size / FACTOR);
		}
		
		switch (type) {
		case FIRST_FIT:
			memFrame.firstFit.logCat.appendLog("Memory compaction successfully!");
			break;
		case BEST_FIT:
			memFrame.bestFit.logCat.appendLog("Memory compaction successfully!");
		}

		return true;
	}
	
	public static void clear(MemFrame memFrame, LinkedList<MemBlock> list, AlgoType type) {
		switch (type) {
		case FIRST_FIT: {
			for (Iterator<MemBlock> it = list.iterator(); it.hasNext();) {
				MemBlock tmpBlock = (MemBlock)it.next();
				memFrame.firstMemPane.remove(tmpBlock);
			}
			list.clear();
			memFrame.firstFit.logCat.appendLog("Clear!");
			break;
		}
		case BEST_FIT:
			list.clear();
			memFrame.bestMemPane.removeAll();
			memFrame.bestFit.logCat.appendLog("Clear!");
		}
	}
}