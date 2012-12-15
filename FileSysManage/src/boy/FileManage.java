package boy;

import java.util.*;

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
		FileFat rootFat = root.getFileFat();
		rootFat.setNextID(Constants.END_OF_FAT);
		FatList.add(rootFat);
		FCBList.add(root);
	}

	public boolean createDir(String fileName, int parentID) {
		FileFCB fileFCB = new FileFCB(fileName);
		fileFCB.setParentID(parentID);
		fileFCB.setFileTyle(File_Type.directory);
		FileFat fileFat = fileFCB.getFileFat();
		fileFat.setUsed(true);
		fileFat.setNextID(Constants.END_OF_FAT);
		FatList.add(fileFat);
		FCBList.add(fileFCB);
		return true;
	}
	
	public boolean createFile(String fileName, int parentID) {
		FileFCB fileFCB = new FileFCB(fileName);
		fileFCB.setParentID(parentID);
		fileFCB.setFileTyle(File_Type.file);
		FileFat fileFat = fileFCB.getFileFat();
		fileFat.setUsed(true);
		fileFat.setNextID(Constants.END_OF_FAT);
		FatList.add(fileFat);
		FCBList.add(fileFCB);
		return true;
	}
	
	public boolean deleteDir(int fileID) {
		
		return true;
	}

	public boolean deleteFile(int fileID) {
		
		return true;
	}

	private FileFCB serchFCBByID(int ID) {
		FileFCB tmpFCB = null;
		for (Iterator<FileFCB> it = FCBList.iterator(); it.hasNext();) {
			tmpFCB = (FileFCB)it.next();
			if (tmpFCB.getID() == ID)
				break;
		}
		return tmpFCB;
	}
	
	private FileFCB searchParentFCBByID(int ID) {		
		FileFCB tmpFCB = null;
		for (Iterator<FileFCB> it = FCBList.iterator(); it.hasNext();) {
			tmpFCB = (FileFCB)it.next();
			if (tmpFCB.getID() == ID)
				break;
		}
		return tmpFCB;
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