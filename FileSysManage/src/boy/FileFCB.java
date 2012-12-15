package boy;

import java.util.*;

public class FileFCB {
	private String fileName = null;
	private int ID; // 使用hashCode作为ID
	private int parentID;
	private long createDate; // 创建文件时间，时间戳
	private long modifyDate; // 最后修改文件时间，时间戳
	// TODO ACL设置不妥
	private Map<String, Set<String>> accessList = null; // ACL
	private File_Type fileType;
	private	int fileSize;
	private FileFat fileFat = null;
	
	public FileFCB(String fileName) {
		this.fileName = fileName;
		ID = this.hashCode();
		createDate = modifyDate = System.currentTimeMillis();
		// TODO 初始化ACL
//		accessList = new HashMap<String, Set<String>>();
//		accessList.put("read", new HashSet<String>());
//		accessList.put("write", new HashSet<String>());
		fileSize = 0;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getParentID() {
		return parentID;
	}

	public void setParentID(int parentID) {
		this.parentID = parentID;
	}

	public long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}

	public long getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(long modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Map<String, Set<String>> getAccessList() {
		return accessList;
	}

	public void setAccessList(Map<String, Set<String>> accessList) {
		this.accessList = accessList;
	}

	public File_Type getFileTyle() {
		return fileType;
	}

	public void setFileTyle(File_Type fileType) {
		this.fileType = fileType;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public FileFat getFileFat() {
		return fileFat;
	}

	public void setFileFat(FileFat fileFat) {
		this.fileFat = fileFat;
	}
}
