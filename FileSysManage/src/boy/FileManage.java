package boy;

import java.util.*;
import java.util.regex.*;

public class FileManage {
	private List<FileFCB> FCBList = null;
	private List<FileFat> FatList = null;
	
	public static void main(String[] argc) {
		FileManage a = new FileManage();
		
		String[] file = new String[15];
		FileFCB[] fileFCB = new FileFCB[15];
		for (int i = 0; i < 15; i++) {
			file[i] = new String("file" + Integer.toString(i));
		}
		for (int i = 0; i < 15; i++) {
			fileFCB[i] = new FileFCB(file[i]);
		}
		String dir1 = new String("dir1");
		String dir2 = new String("dir2");
		String dir3 = new String("dir3");
		FileFCB dirFCB1 = new FileFCB(dir1);
		FileFCB dirFCB2 = new FileFCB(dir2);
		FileFCB dirFCB3 = new FileFCB(dir3);
		
		a.createDir(dir1, a.getRoot().getID(), dirFCB1);
		a.createDir(dir2, a.getRoot().getID(), dirFCB2);
		a.createDir(dir3, dirFCB1.getID(), dirFCB3);
		
		for (int i = 0; i < 4; i++) {
			a.createFile(file[i], dirFCB1.getID(), fileFCB[i]);
		}
		for (int i = 4; i < 10; i++) {
			a.createFile(file[i], dirFCB2.getID(), fileFCB[i]);
		}
		for (int i = 10; i < 15; i++) {
			a.createFile(file[i], dirFCB3.getID(), fileFCB[i]);
		}
		
		a.deleteDir(dirFCB1.getID());

		System.out.println();
		a.print();
	}
	
	public void print() {
		for (Iterator<FileFCB> it = FCBList.iterator(); it.hasNext();) {
			FileFCB tmpFCB = (FileFCB)it.next();
			System.out.println(tmpFCB.getFileName() + ' ' + tmpFCB.getID() + ' ' + tmpFCB.getParentID());
		}
	}
	
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
	
	public FileFCB getRoot() {
		return FCBList.get(0);
	}

	public void formatFileSystem() {
		FCBList.clear();
		FatList.clear();
		this.initRoot();
	}

	public Status_Type createDir(String dirName, int parentID, FileFCB dirFCB) {
		if (this.isDupilicationOfName(dirName, parentID, FCB_Type.directory))
			return Status_Type.dupilication_of_name;
		if (this.isNameLegal(dirName))
			return Status_Type.illegal_name;
		dirFCB.setParentID(parentID);
		dirFCB.setFCBType(FCB_Type.directory);
		FCBList.add(dirFCB);
		return Status_Type.all_right;
	}
	
	public Status_Type createFile(String fileName, int parentID, FileFCB fileFCB) {
		if (this.isDupilicationOfName(fileName, parentID, FCB_Type.file))
			return Status_Type.dupilication_of_name;
		if (this.isNameLegal(fileName))
			return Status_Type.illegal_name;
		fileFCB.setParentID(parentID);
		fileFCB.setFCBType(FCB_Type.file);
		FileFat fileFat = new FileFat();
		fileFat.setUsed(true);
		fileFat.setNextID(Constants.END_OF_FAT);
		fileFCB.setFileFat(fileFat);
		FatList.add(fileFat);
		FCBList.add(fileFCB);
		return Status_Type.all_right;
	}
	
	public void deleteDir(int dirID) {
		List<FileFCB> toDeleteList = new LinkedList<FileFCB>();
		this.searchFCBByParentID(dirID, toDeleteList);
		int deleteSize = toDeleteList.size();
		for (int i = 0; i < deleteSize; i++) {
			FileFCB tmpFCB = toDeleteList.get(i);
			if (tmpFCB.getFCBType() == FCB_Type.directory) {
				this.recursiveDeleteDir(tmpFCB.getID(), toDeleteList);
			}
		}
		// 首先删除所有需要删除的文件
		for (Iterator<FileFCB> it = toDeleteList.iterator(); it.hasNext();) {
			FileFCB tmpFCB = (FileFCB)it.next();
			if (tmpFCB.getFCBType() == FCB_Type.file) {
				this.deleteFile(tmpFCB.getID());
				//it.remove();
			}
		}
		FCBList.removeAll(toDeleteList); 
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
	
	public Status_Type rename(int fileID, String newName) {
		if (this.isNameLegal(newName)) {
			return Status_Type.illegal_name;
		}
		FileFCB changedFCB = this.searchFCBByID(fileID);
		int parentID = changedFCB.getParentID();
		List<FileFCB> siblingList = new LinkedList<FileFCB>();
		this.searchFCBByParentID(parentID, siblingList);
		for (Iterator<FileFCB> it = FCBList.iterator(); it.hasNext();) {
			FileFCB tmpFCB = it.next();
			if (tmpFCB.getFileName().equals(newName)) {
				return Status_Type.dupilication_of_name;
			}
		}
		changedFCB.setFileName(newName);
		return Status_Type.all_right;
	}
	
	public Status_Type move(int fileID, int parentID) {
		FileFCB movedFCB = this.searchFCBByID(fileID);
		List<FileFCB> targetSubFileList = new LinkedList<FileFCB>();
		this.searchFCBByParentID(parentID, targetSubFileList);
		for (Iterator<FileFCB> it = targetSubFileList.iterator(); it.hasNext();) {
			FileFCB tmpFCB = (FileFCB)it.next();
			if (tmpFCB.getFileName().equals(movedFCB.getFileName())) {
				return Status_Type.dupilication_of_name;
			}
		}
		movedFCB.setParentID(parentID);
		return Status_Type.all_right;
	}
	
	private void initRoot() {
		FileFCB root = new FileFCB("Root");
		root.setParentID(Constants.PARENT_OF_ROOT); // 根目录的父节点ID规定为0
		root.setFCBType(FCB_Type.directory);
		FCBList.add(root);
	}
	
	// 判断命名是否合法
	private boolean isNameLegal(String fileName) {
		// TODO 检查命名是否合法
//		if (fileName == null || fileName.length() <= 0 || fileName.length() >= 255)
//			return false;
//		String regex = "^[a-zA-Z_]+[a-zA-Z0-9_]*";
//		return Pattern.compile(regex).matcher(fileName).matches();
		return false;
	}
	
	// 检测是否重名
	private boolean isDupilicationOfName(String fileName, int parentID, FCB_Type fileType) {
		for (Iterator<FileFCB> it = FCBList.iterator(); it.hasNext();) {
			FileFCB tmpFCB = (FileFCB)it.next();
			if (tmpFCB.getFileName().endsWith(fileName) && 
					tmpFCB.getParentID() == parentID &&
					tmpFCB.getFCBType() == fileType)
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
			if (tmpFCB.getParentID() == parentID) {
				subFCBList.add(tmpFCB);
			}
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
	
	private void recursiveDeleteDir(int dirID, List<FileFCB> toDeleteList) {  
		int beforeSize = toDeleteList.size();
		this.searchFCBByParentID(dirID, toDeleteList);
		int afterSize = toDeleteList.size();
		if (beforeSize == afterSize) {
			return ;
		}
		for (int i = beforeSize; i < afterSize; i++) {
			FileFCB tmpFCB = toDeleteList.get(i);
			if (tmpFCB.getFCBType() == FCB_Type.directory) {
				this.recursiveDeleteDir(tmpFCB.getID(), toDeleteList);
			}
		}
	}
}