package boy;

enum File_Type { directory, file };

public class Constants {
	static int BLOCK_SIZE = 256; // 每一个块的大小
	static int CLUSTER_SIZE = BLOCK_SIZE * 4; // 每一个簇的大小
	static int CLUSTER_NUM = 2048;
	static int MEMORY_SIZE = CLUSTER_SIZE * CLUSTER_NUM; // 内存大小
	
	static int END_OF_FAT = -1; // Fat表结束标志
	static int PARENT_OF_ROOT = -1; // 根结点的父节点
	
//	static char END_OF_FILE = '#'; // 文件结束标志
}
