package com.boy;

enum FCB_Type { directory, file };
enum Status_Type { all_right, dupilication_of_name, illegal_name, memory_lack };

public class Constants {
	// UI常量定义
	static int FRAME_LENGTH = 800, FRAME_WIDTH = 600;
	static int BUTTON_PANE_LENGTH = FRAME_LENGTH, BUTTON_PANE_WIDTH = 100;
	static int TREE_AND_EDIT_PANE_LENGTH = FRAME_LENGTH, TREE_AND_EDIT_PANE_WIDTH = FRAME_WIDTH - BUTTON_PANE_WIDTH;
	static int NUMBER_OF_BUTTONS = 8;

	// 文件系统管理常量定义
	static int BLOCK_SIZE = 256; // 每一个块的大小
	static int CLUSTER_SIZE = BLOCK_SIZE * 4; // 每一个簇的大小
	static int CLUSTER_NUM = 2048;
	static int MEMORY_SIZE = CLUSTER_SIZE * CLUSTER_NUM; // 内存大小
	
	static int END_OF_FAT = -1; // Fat表结束标志
	static int PARENT_OF_ROOT = -1; // 根结点的父节点
}