package boy;

import java.util.List;

public class FileSystem {
	public static void main(String[] args) {
		FileManage fileManage = new FileManage();
		List<FileFCB> FCBList = fileManage.getFCBList();
		int code = FCBList.get(0).getID();
		fileManage.createDir("abc", code);
		System.out.println(Constants.MEMORY_SIZE / 1024);
	}
}
