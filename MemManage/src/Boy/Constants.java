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
	
	static Rectangle firstPaneRec = new Rectangle(98, 58, 204, 324);
	static Rectangle bestPaneRec = new Rectangle(498, 58, 204, 324);
	
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
	public static boolean pack(MemFrame memFrame, LinkedList<MemBlock> list) {
		MemBlock firstBlock = list.getFirst();
		// If the first block is free, make the second block be the first
		if (!firstBlock.getUsed()) {
			list.remove(firstBlock);
			memFrame.firstMemPane.remove(firstBlock);
			firstBlock = list.getFirst();
			firstBlock.setBounds(2, 2, blockWidth, firstBlock.size / factor);
		}

		int index = 0, currentPos = 2;
		LinkedList<MemBlock> toDelete = new LinkedList<MemBlock>();
		for (Iterator<MemBlock> it = list.iterator(); it.hasNext(); index++) {
			MemBlock tmpBlock = (MemBlock)it.next();
			if (tmpBlock == list.getLast())
				break;
			if (tmpBlock.getUsed()) {
				if (tmpBlock.beginY != currentPos) {
					tmpBlock.setBounds(2, currentPos, blockWidth, tmpBlock.size / factor);
				}
				currentPos += tmpBlock.size / factor;
				continue;
			} else {
				memFrame.firstMemPane.remove(tmpBlock);
				toDelete.add(tmpBlock);
			}
		}
		list.removeAll(toDelete);
		
		MemBlock freeBlock = list.getLast();
		freeBlock.size = 2 * (322 - currentPos);
		freeBlock.setBounds(2, currentPos, blockWidth, freeBlock.size / factor);
		System.out.println(freeBlock.size);

//		for (int i = 0; i < list.size() - 1; i++) {
//			MemBlock tmpBlock = list.get(i);
//			System.out.println(tmpBlock.name);
//		}
		return true;
	}
}