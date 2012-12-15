package boy;

public class FileFat {
	private int ID; // 标示符，hashCode表示
	private int nextID; // 下一个标示符，hashCode表示
//	private int clusterNum;
	private String data = null;
	private boolean isUsed;
	
	public FileFat() {
		ID = this.hashCode();
		isUsed = false;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getNextID() {
		return nextID;
	}

	public void setNextID(int nextID) {
		this.nextID = nextID;
	}

//	public int getClusterNum() {
//		return clusterNum;
//	}
//
//	public void setClusterNum(int clusterNum) {
//		this.clusterNum = clusterNum;
//	}

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
	
}
