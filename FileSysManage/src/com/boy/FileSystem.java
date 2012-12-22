package com.boy;

public class FileSystem {
	public static void main(String[] args) {
		FileSysUIView fileSysPane = new FileSysUIView();
		FileManage fileManage = new FileManage();
		FileSysUIController fileSysUIController = new FileSysUIController(fileSysPane, fileManage);
		FileSysFrame fileSysFrame = new FileSysFrame();
		fileSysFrame.add(fileSysUIController.getFileSysUIView());
	}
}
