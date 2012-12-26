package com.boy;

import java.util.*;
import java.io.*;
import java.awt.event.*;
import java.text.*;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

public class FileSysUIController implements ActionListener, TreeSelectionListener, MouseListener, Serializable {
	private static final long serialVersionUID = 1L;
	
	private FileSysUIView fileSysUIView = null;
	private FileManage fileManage = null;
	private Map<DefaultMutableTreeNode, FileFCB> connectionMap = null;
	
	public FileSysUIController(FileSysUIView fileSysUIView, FileManage fileManage) {
		this.fileSysUIView = fileSysUIView;
		this.fileManage = fileManage;
		
		connectionMap = new HashMap<DefaultMutableTreeNode, FileFCB>();
		DefaultMutableTreeNode rootNode = fileSysUIView.rootNode;
		FileFCB rootFCB = fileManage.getRoot();
		connectionMap.put(rootNode, rootFCB);
		
		this.addListenerFromView();
	}
	
	public void addListenerFromView() {
		fileSysUIView.tree.addMouseListener(this);
		fileSysUIView.tree.addTreeSelectionListener(this);
		fileSysUIView.formatButton.addActionListener(this);
		fileSysUIView.newDirButton.addActionListener(this);
		fileSysUIView.newFileButton.addActionListener(this);
		fileSysUIView.deleteButton.addActionListener(this);
		fileSysUIView.renameButton.addActionListener(this);
		fileSysUIView.openButton.addActionListener(this);
		fileSysUIView.saveAndCloseButton.addActionListener(this);
	}
	
	public FileSysUIView getFileSysUIView() {
		return fileSysUIView;
	}

	public void setFileSysUIView(FileSysUIView fileSysUIView) {
		this.fileSysUIView = fileSysUIView;
	}
	public FileManage getFileManage() {
		return fileManage;
	}

	public void setFileManage(FileManage fileManage) {
		this.fileManage = fileManage;
	}

	public Map<DefaultMutableTreeNode, FileFCB> getConnectionMap() {
		return connectionMap;
	}

	public void setConnectionMap(Map<DefaultMutableTreeNode, FileFCB> connectionMap) {
		this.connectionMap = connectionMap;
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == fileSysUIView.formatButton) {
			int result  = JOptionPane.showConfirmDialog(
					fileSysUIView.buttonPane,
					"Are you sure to format disk?",
					"Format Warning",
					JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.NO_OPTION)
				return ;
			fileSysUIView.rootNode.removeAllChildren();
			fileSysUIView.tree.updateUI();
			fileManage.formatFileSystem();
			fileSysUIView.textArea.setText(this.getRootInfo());
			return;
		}
		
		DefaultMutableTreeNode selectedNode =
				(DefaultMutableTreeNode)fileSysUIView.tree.getLastSelectedPathComponent();
		if (selectedNode == null) {
			JOptionPane.showMessageDialog(null,
					"Please selected a file or a dirctory to operate!",
					"Error",
					JOptionPane.ERROR_MESSAGE);
			return ;
		}
		
		FileFCB selectedFCB = connectionMap.get(selectedNode);
		FCB_Type FCBType = selectedFCB.getFCBType();
		int ID = selectedFCB.getID();
		if (obj == fileSysUIView.newDirButton) {
			if (FCBType == FCB_Type.file) {
				JOptionPane.showMessageDialog(null,
						"Please selected a dirctory to new!",
						"Error",
						JOptionPane.ERROR_MESSAGE);
			} else if (FCBType == FCB_Type.directory) {
				String dirName = this.getName(FCB_Type.directory);
				if (dirName.equals("N/A")) {
					return ;
				}
				FileFCB dirFCB = new FileFCB(dirName);
				Status_Type result = fileManage.createDir(dirName, ID, dirFCB);
				if (result == Status_Type.all_right) {
					DefaultMutableTreeNode newDir = new DefaultMutableTreeNode(dirName, true);
					selectedNode.add(newDir);
					TreeNode[] nodes = fileSysUIView.treeModel.getPathToRoot(newDir);
					TreePath path = new TreePath(nodes);
					if (selectedFCB.getParentID() == -1)
						fileSysUIView.textArea.setText(this.getRootInfo());
					else
						fileSysUIView.textArea.setText(this.getDirInfo(selectedFCB));
					fileSysUIView.textArea.setEditable(false);
					fileSysUIView.tree.scrollPathToVisible(path);
					fileSysUIView.tree.updateUI();
					
					connectionMap.put(newDir, dirFCB);
				} else  {
					this.showStatus(result);
				}
			}
		} else if (obj == fileSysUIView.newFileButton) {
			if (FCBType == FCB_Type.file) {
				JOptionPane.showMessageDialog(null,
						"Please selected a dirctory to new!",
						"Error",
						JOptionPane.ERROR_MESSAGE);
			} else if (FCBType == FCB_Type.directory) {
				String fileName = this.getName(FCB_Type.file);
				if (fileName.equals("N/A")) {
					return ;
				}
				FileFCB fileFCB = new FileFCB(fileName);
				Status_Type result = fileManage.createFile(fileName, ID, fileFCB);
				if (result == Status_Type.all_right) {
					DefaultMutableTreeNode newFile = new DefaultMutableTreeNode(fileName, false);
					selectedNode.add(newFile);
					TreeNode[] nodes = fileSysUIView.treeModel.getPathToRoot(newFile);
					TreePath path = new TreePath(nodes);
					if (selectedFCB.getParentID() == -1)
						fileSysUIView.textArea.setText(this.getRootInfo());
					else
						fileSysUIView.textArea.setText(this.getDirInfo(selectedFCB));
					fileSysUIView.textArea.setEditable(false);
					fileSysUIView.tree.scrollPathToVisible(path);
					fileSysUIView.tree.updateUI();
					
					connectionMap.put(newFile, fileFCB);
				} else  {
					this.showStatus(result);
				}
			}
		} else if (obj == fileSysUIView.deleteButton) {
			if (selectedNode.getParent() == null) {
				JOptionPane.showMessageDialog(null,
						"Can't delete the root node!",
						"Delete Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				FileFCB toDeleteFCB = connectionMap.get(selectedNode);
				if (toDeleteFCB.getFCBType() == FCB_Type.directory) {
					fileManage.deleteDir(ID);
					recursiveDeleteNode(selectedNode);
					connectionMap.remove(selectedNode);
				} else if (toDeleteFCB.getFCBType() == FCB_Type.file) {
					fileManage.deleteFile(ID);
					connectionMap.remove(selectedNode);
				}
				fileSysUIView.treeModel.removeNodeFromParent(selectedNode);
				fileSysUIView.textArea.setText(this.getRootInfo());
				fileSysUIView.textArea.setEditable(false);
			}
		} else if (obj == fileSysUIView.renameButton) {
			if (selectedNode.getParent() == null) {
				JOptionPane.showMessageDialog(null,
						"Can't rename the root node!",
						"Rename Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				FileFCB toRenameFCB = connectionMap.get(selectedNode);
				JOptionPane optionPane = new JOptionPane(
						"Please input the new name",
						JOptionPane.QUESTION_MESSAGE,
						JOptionPane.CANCEL_OPTION);
				optionPane.setInitialSelectionValue(toRenameFCB.getFileName());
				JDialog dialog = optionPane.createDialog("Rename");
				optionPane.setWantsInput(true);
				optionPane.setInputValue("N/A");
				dialog.setVisible(true);
				String newName = (String) optionPane.getInputValue();
				if (newName.equals("N/A"))
					return ;
				Status_Type result = fileManage.rename(toRenameFCB.getID(), newName);

				if (result == Status_Type.all_right) {
					selectedNode.setUserObject(newName);
					if (toRenameFCB.getFCBType() == FCB_Type.directory)
						fileSysUIView.textArea.setText(this.getDirInfo(toRenameFCB));
					else 
						fileSysUIView.textArea.setText(this.getFileInfo(toRenameFCB));
					if (selectedNode.isRoot())
						fileSysUIView.textArea.setText(this.getRootInfo());
					fileSysUIView.tree.updateUI();
				} else {
					this.showStatus(result);
				}
			}
		} else if (obj == fileSysUIView.openButton) {
			if (selectedNode.getAllowsChildren()) {
				JOptionPane.showMessageDialog(null,
						"Please choose a file to open!",
						"Open Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				fileSysUIView.deleteButton.setEnabled(false);
				fileSysUIView.formatButton.setEnabled(false);
				fileSysUIView.renameButton.setEnabled(false);
				fileSysUIView.openButton.setEnabled(false);
				fileSysUIView.saveAndCloseButton.setEnabled(true);
				FileFCB toOpenFCB = connectionMap.get(selectedNode);
				fileSysUIView.textArea.setEditable(true);
				fileSysUIView.textArea.setText(fileManage.readFile(toOpenFCB.getID()));
			}
		} else if (obj == fileSysUIView.saveAndCloseButton) {
			if (selectedNode.getAllowsChildren()) {
				JOptionPane.showMessageDialog(null,
						"Please choose a file to open!",
						"Open Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				FileFCB toSaveFile = connectionMap.get(selectedNode);
				Status_Type result = fileManage.saveFile(toSaveFile.getID(), fileSysUIView.textArea.getText());
				if (result != Status_Type.all_right)
				{
					this.showStatus(result);
					return ;
				}
				fileSysUIView.deleteButton.setEnabled(true);
				fileSysUIView.formatButton.setEnabled(true);
				fileSysUIView.renameButton.setEnabled(true);
				fileSysUIView.saveAndCloseButton.setEnabled(false);
				fileSysUIView.openButton.setEnabled(true);
				fileSysUIView.textArea.setText(this.getFileInfo(selectedFCB));
				fileSysUIView.textArea.setEditable(false);
			}
		}
	}

	public void valueChanged(TreeSelectionEvent e) {
		JTree tree = (JTree)e.getSource();
		// 获得目前选中结点
		DefaultMutableTreeNode selectedNode =
				(DefaultMutableTreeNode)tree.getLastSelectedPathComponent();

		if (selectedNode == null)
			return ;
		FileFCB toShowFCB = connectionMap.get(selectedNode); // 获得对应FCB

		if (selectedNode.getAllowsChildren()) {
			fileSysUIView.newDirButton.setEnabled(true);
			fileSysUIView.newFileButton.setEnabled(true);
			fileSysUIView.openButton.setEnabled(false);
			fileSysUIView.saveAndCloseButton.setEnabled(false);
			fileSysUIView.textArea.setText(this.getDirInfo(toShowFCB));
			fileSysUIView.textArea.setEditable(false);
		} else {
			fileSysUIView.openButton.setEnabled(true);
			fileSysUIView.saveAndCloseButton.setEnabled(false);
			fileSysUIView.newDirButton.setEnabled(false);
			fileSysUIView.newFileButton.setEnabled(false);
			fileSysUIView.textArea.setText(this.getFileInfo(toShowFCB));
			fileSysUIView.textArea.setEditable(false);
		}
		
		// 根结点的处理
		if (selectedNode.isRoot()) {
			fileSysUIView.textArea.setText(this.getRootInfo());
			fileSysUIView.textArea.setEditable(false);
		}
		fileSysUIView.deleteButton.setEnabled(true);
		fileSysUIView.formatButton.setEnabled(true);
		fileSysUIView.renameButton.setEnabled(true);
	}

	public void mousePressed(MouseEvent e) {
		// 通过TreePath来确定移动节点
		TreePath treePath = fileSysUIView.tree.getPathForLocation(e.getX(), e.getY());
		if (treePath != null) {
			fileSysUIView.movePath = treePath;
		}
	}

	public void mouseReleased(MouseEvent e) { // 获得需要拖到的父节点
		TreePath treePath = fileSysUIView.tree.getPathForLocation(e.getX(), e.getY());
		if (treePath != null && fileSysUIView.movePath != null)
		{
			if (fileSysUIView.movePath.isDescendant(treePath) &&
				fileSysUIView.movePath != treePath) {
				// 错误的移动
				JOptionPane.showMessageDialog(null,
						"Can't move a node to its child node!",
						"Move Error",
						JOptionPane.ERROR_MESSAGE);
				return ;
			} else if (fileSysUIView.movePath != treePath) { // 不是向子节点移动，且鼠标按下、松开时不是同一个节点  
				DefaultMutableTreeNode parentNode =
						(DefaultMutableTreeNode) treePath.getLastPathComponent();
				FileFCB parentFCB = connectionMap.get(parentNode);
				DefaultMutableTreeNode childNode = 
						(DefaultMutableTreeNode) fileSysUIView.movePath.getLastPathComponent();
				if (!parentNode.getAllowsChildren()) {
					JOptionPane.showMessageDialog(null,
							"Please choose a directory as the destination!",
							"Move Error",
							JOptionPane.ERROR_MESSAGE);
					return ;
				}
				FileFCB childFCB = connectionMap.get(childNode);
				Status_Type result = fileManage.move(childFCB.getID(), parentFCB.getID());
				if (result == Status_Type.all_right) {
					parentNode.add(childNode);
					fileSysUIView.movePath = null;
					fileSysUIView.tree.updateUI();
				} else {
					this.showStatus(result);
				}
			}
		}
	}
	
	private void showStatus(Status_Type status) {
		if (status == Status_Type.dupilication_of_name) {
			JOptionPane.showMessageDialog(null,
					"Dupilication of name!",
					"Error",
					JOptionPane.ERROR_MESSAGE);
		} else if (status == Status_Type.illegal_name) {
			JOptionPane.showMessageDialog(null,
					"Illegal_name!",
					"Error",
					JOptionPane.ERROR_MESSAGE);
		} else if (status == Status_Type.memory_lack) {
			JOptionPane.showMessageDialog(null,
					"Memory lack!",
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void recursiveDeleteNode(DefaultMutableTreeNode toDeleteNode) {
		Enumeration<DefaultMutableTreeNode> children = toDeleteNode.children();
		for (Enumeration<DefaultMutableTreeNode> e = children; children.hasMoreElements();) {
			DefaultMutableTreeNode tmpNode = e.nextElement();
			if (tmpNode.getChildCount() != 0) {
				recursiveDeleteNode(tmpNode);
			}
			connectionMap.remove(tmpNode);
		}
	}
	
	private String getName(FCB_Type FCBType) {
		JOptionPane optionPane = null;
		JDialog dialog = null;
		if (FCBType == FCB_Type.directory) {
			optionPane = new JOptionPane(
					"Please input the name of new directory",
					JOptionPane.QUESTION_MESSAGE,
					JOptionPane.CANCEL_OPTION);
			optionPane.setInitialSelectionValue("new_directory");
			dialog = optionPane.createDialog("New Directory");
		} else if (FCBType == FCB_Type.file) {
			optionPane = new JOptionPane(
					"Please input the name of new file",
					JOptionPane.QUESTION_MESSAGE,
					JOptionPane.CANCEL_OPTION);
			optionPane.setInitialSelectionValue("new_file");
			dialog = optionPane.createDialog("New File");
		}
		optionPane.setWantsInput(true);
		optionPane.setInputValue("N/A");
		dialog.setVisible(true);
		return (String)optionPane.getInputValue();
	}
	
	private Date getData(long timeStamp) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String d = format.format(timeStamp);
		return format.parse(d);
	}
	
	private String getDirInfo(FileFCB dirFCB) {
		String name = dirFCB.getFileName();
		Date createDate = null, modifyDate = null;
		try {
			createDate = this.getData(dirFCB.getCreateDate());
			modifyDate = this.getData(dirFCB.getModifyDate());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		String type = null;
		if (dirFCB.getFCBType() == FCB_Type.directory)
			type = "directory";
		else
			type = "file";
		String size = String.format(dirFCB.getFileSize() + "B");
		int subNumber = fileManage.getSubNumber(dirFCB.getID());
		return String.format(
				"         name:  " + name +
				"\n             type:  " + type +
				"\ncreate date:  " + createDate +
				"\nmodify date:  " + modifyDate +
				"\n             size:  " + size +
				"\n      children:" + subNumber);
	}
	
	private String getFileInfo(FileFCB fileFCB) {
		String name = fileFCB.getFileName();
		Date createDate = null, modifyDate = null;
		try {
			createDate = this.getData(fileFCB.getCreateDate());
			modifyDate = this.getData(fileFCB.getModifyDate());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		String type = null;
		if (fileFCB.getFCBType() == FCB_Type.directory)
			type = "directory";
		else
			type = "file";
		String size = String.format(fileFCB.getFileSize() + "B");
		String text = fileManage.readFile(fileFCB.getID());
		if (text.length() > Constants.MAX_TEXT) {
			text = String.format(text.substring(0, Constants.MAX_TEXT) + "......\n\nPlease open to look details.");
		}
		return String.format(
				"         name:  " + name +
				"\n             type:  " + type +
				"\ncreate date:  " + createDate +
				"\nmodify date:  " + modifyDate +
				"\n             size:  " + size + 
				"\n---------------------------------------------------------------------------------" + 
				"\n" + text);
	}
	
	private String getRootInfo() {
		FileFCB rootFCB = fileManage.getRoot();
		String name = rootFCB.getFileName();
		Date createDate = null, modifyDate = null;
		try {
			createDate = this.getData(rootFCB.getCreateDate());
			modifyDate = this.getData(rootFCB.getModifyDate());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		String size = String.format(fileManage.getRootSize() + "B");
		int subNumber = fileManage.getSubNumber(rootFCB.getID());
		String remainSize = String.format(Constants.MEMORY_SIZE - rootFCB.getFileSize() + "B");
		return String.format(
				"         name:  " + name +
				"\ncreate date:  " + createDate +
				"\nmodify date:  " + modifyDate +
				"\n             size:  " + size +
				"\nremain size:  " + remainSize +
				"\n      children:  " + subNumber);
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}