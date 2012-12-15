package boy;

import java.util.*;

public class FileManage {
	private List<String> dataArea = null;
	
	public FileManage() {
		dataArea = new ArrayList<String>(Constants.CLUSTER_NUM);
		initRoot();
	}

	public void initRoot() {
		FileFCB root = new FileFCB("Root");
		root.setParentID(0); // 根目录的父节点ID规定为0
		root.setFileTyle(File_Type.directory);
	}
	
	public List<String> getDataArea() {
		return dataArea;
	}

	public void setDataArea(List<String> dataArea) {
		this.dataArea = dataArea;
	}

	public boolean createDir(String fileName, String parentName) {
		return true;
	}
	
	public boolean createFile(String fileName, String parentName) {
		return true;
	}
}
