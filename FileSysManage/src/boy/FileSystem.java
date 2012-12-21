package boy;

public class FileSystem {
	public static void main(String[] args) {
		FileSysUIView fileSysPane = new FileSysUIView();
		FileManage fileManage = new FileManage();
		IOManage IOManage = new IOManage();
		FileSysUIController fileSysUIController = new FileSysUIController(fileSysPane, fileManage, IOManage);
		FileSysFrame fileSysFrame = new FileSysFrame();
		fileSysFrame.add(fileSysUIController.getFileSysUIView());
	}
}
