package boy;

import java.util.*;
import java.util.regex.*;

public class FileManage {
	private List<FileFCB> FCBList = null;
	private List<FileFat> FatList = null;
	
	public FileManage() {
		FCBList = new LinkedList<FileFCB>();
		FatList = new LinkedList<FileFat>();
		initRoot();
	}

	public List<FileFCB> getFCBList() {
		return FCBList;
	}

	public void setFCBList(List<FileFCB> FCBList) {
		this.FCBList = FCBList;
	}

	public List<FileFat> getFatList() {
		return FatList;
	}

	public void setFatList(List<FileFat> FatList) {
		this.FatList = FatList;
	}

	public void initRoot() {
		FileFCB root = new FileFCB("Root");
		root.setParentID(Constants.PARENT_OF_ROOT); // 根目录的父节点ID规定为0
		root.setFileTyle(File_Type.directory);
		FCBList.add(root);
	}

	public void formatFileSystem() {
		FCBList.clear();
		FatList.clear();
	}

	public Status_Type createDir(String dirName, int parentID) {
		if (this.isRename(dirName, parentID, File_Type.directory))
			return Status_Type.rename;
		if (this.isNameLegal(dirName))
			return Status_Type.illegal_name;
		FileFCB dirFCB = new FileFCB(dirName);
		dirFCB.setParentID(parentID);
		dirFCB.setFileTyle(File_Type.directory);
		FCBList.add(dirFCB);
		return Status_Type.all_right;
	}
	
	public Status_Type createFile(String fileName, int parentID) {
		if (this.isRename(fileName, parentID, File_Type.file))
			return Status_Type.rename;
		if (this.isNameLegal(fileName))
			return Status_Type.illegal_name;
		FileFCB fileFCB = new FileFCB(fileName);
		fileFCB.setParentID(parentID);
		fileFCB.setFileTyle(File_Type.file);
		FileFat fileFat = new FileFat();
		fileFat.setUsed(true);
		fileFat.setNextID(Constants.END_OF_FAT);
		fileFCB.setFileFat(fileFat);
		FatList.add(fileFat);
		FCBList.add(fileFCB);
		return Status_Type.all_right;
	}
	
	public void deleteDir(int dirID) {
		FileFCB dirFCB = this.searchFCBByID(dirID);
		List<FileFCB> subFCBList = new LinkedList<FileFCB>();
		this.searchFCBByParentID(dirID, subFCBList);
		for (Iterator<FileFCB> it = FCBList.iterator(); it.hasNext();) {
			FileFCB tmpFCB = (FileFCB)it.next();
			if (tmpFCB.getFileTyle() == File_Type.directory)
				deleteDir(tmpFCB.getID());
			if (tmpFCB.getFileTyle() == File_Type.file)
				deleteFile(tmpFCB.getID());
		}
		FCBList.remove(dirFCB);
	}

	public void deleteFile(int fileID) {
		// 获得文件FCB和Fat表首项
		FileFCB fileFCB = this.searchFCBByID(fileID);
		FileFat fileFat = fileFCB.getFileFat();
		int nextID;
		FCBList.remove(fileFCB);
		do {
			fileFat.setUsed(false);
			nextID = fileFat.getNextID();
			fileFat = this.searchNextFatByID(nextID);
		} while (nextID != Constants.END_OF_FAT);
		fileFat.setUsed(false);
	}
	
	// 判断命名是否合法
	private boolean isNameLegal(String fileName) {
		if (fileName == null || fileName.length() <= 0 || fileName.length() >= 255)
			return false;
		String regex = "^[a-zA-Z_]+[a-zA-Z0-9_]";
		return Pattern.compile(regex).matcher(fileName).matches();
	}
	
	// 检测是否重名
	private boolean isRename(String fileName, int parentID, File_Type fileType) {
		for (Iterator<FileFCB> it = FCBList.iterator(); it.hasNext();) {
			FileFCB tmpFCB = (FileFCB)it.next();
			if (tmpFCB.getFileName().endsWith(fileName) && 
					tmpFCB.getParentID() == parentID &&
					tmpFCB.getFileTyle() == fileType)
				return true;
		}
		return false;
	}
	
	// 一些与FCB和Fat表相关的搜索方法
	private FileFCB searchFCBByID(int ID) {
		FileFCB tmpFCB = null;
		for (Iterator<FileFCB> it = FCBList.iterator(); it.hasNext();) {
			tmpFCB = (FileFCB)it.next();
			if (tmpFCB.getID() == ID)
				break;
		}
		return tmpFCB;
	}
	
	private void searchFCBByParentID(int parentID, List<FileFCB> subFCBList) {		
		FileFCB tmpFCB = null;
		for (Iterator<FileFCB> it = FCBList.iterator(); it.hasNext();) {
			tmpFCB = (FileFCB)it.next();
			if (tmpFCB.getParentID() == parentID)
				subFCBList.add(tmpFCB);
		}
	}
	
	private FileFat searchNextFatByID(int ID) {
		FileFat tmpFat = null;
		for (Iterator<FileFat> it = FatList.iterator(); it.hasNext();) {
			tmpFat = (FileFat)it.next();
			if (tmpFat.getID() == ID)
				break;
		}
		return tmpFat;
	}
	

	private FileFat searchFatByID(int ID) {
		FileFat tmpFat = null;
		for (Iterator<FileFat> it = FatList.iterator(); it.hasNext();) {
			tmpFat = (FileFat)it.next();
			if (tmpFat.getID() == ID)
				break;
		}
		return tmpFat;
	}
}