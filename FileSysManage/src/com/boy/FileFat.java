package com.boy;

public class FileFat {
	private int ID; // 标示符，hashCode表示
	private int nextID; // 下一个标示符，hashCode表示
	private String data = null;
	private int usedSize;
	private boolean isUsed;
	
	public FileFat() {
		ID = this.hashCode();
		nextID = -1;
		isUsed = false;
		usedSize = 0;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public int getNextID() {
		return nextID;
	}

	public void setNextID(int nextID) {
		this.nextID = nextID;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public int getUsedSize() {
		return usedSize;
	}

	public void setUsedSize(int usedSize) {
		this.usedSize = usedSize;
	}
}