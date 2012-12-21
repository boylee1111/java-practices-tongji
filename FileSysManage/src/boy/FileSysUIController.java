package boy;

import java.util.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

public class FileSysUIController implements ActionListener, TreeModelListener, TreeExpansionListener, TreeWillExpandListener, TreeSelectionListener, MouseListener {
	private FileSysUIView fileSysUIView = null;
	private FileManage fileManage = null;
	private IOManage IOManage = null;
	private Map<DefaultMutableTreeNode, FileFCB> connectionMap = null;
	
	public FileSysUIController(FileSysUIView fileSysUIView, FileManage fileManage, IOManage IOManage) {
		this.fileSysUIView = fileSysUIView;
		this.fileManage = fileManage;
		this.IOManage = IOManage;
		
		connectionMap = new HashMap<DefaultMutableTreeNode, FileFCB>();
		DefaultMutableTreeNode rootNode = fileSysUIView.rootNode;
		FileFCB rootFCB = fileManage.getRoot();
		connectionMap.put(rootNode, rootFCB);
		
		this.addListenerFromView();
	}
	
	public void print() {
		Set<Map.Entry<DefaultMutableTreeNode, FileFCB>> set = connectionMap.entrySet();
		for (Iterator<Map.Entry<DefaultMutableTreeNode, FileFCB>> it = set.iterator(); it.hasNext();) {
			Map.Entry<DefaultMutableTreeNode, FileFCB> entry = it.next();
			System.out.println(entry.getKey() + "->" + entry.getValue().getFileName());
		}
	}
	
	public void addListenerFromView() {
		fileSysUIView.tree.addMouseListener(this);
		fileSysUIView.treeModel.addTreeModelListener(this);
		fileSysUIView.formatButton.addActionListener(this);
		fileSysUIView.newDirButton.addActionListener(this);
		fileSysUIView.newFileButton.addActionListener(this);
		fileSysUIView.deleteButton.addActionListener(this);
		fileSysUIView.renameButton.addActionListener(this);
	}
	
	public FileSysUIView getFileSysUIView() {
		return fileSysUIView;
	}

	public void setFileSysUIView(FileSysUIView fileSysUIView) {
		this.fileSysUIView = fileSysUIView;
	}

	// TODO ActionListener的监听
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == fileSysUIView.formatButton) {
			// TODO 有问题
			fileSysUIView.rootNode.removeAllChildren();
			fileSysUIView.tree.updateUI();
			fileManage.formatFileSystem();
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
				this.print();
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
				if (newName.equals("N/A"));
				Status_Type result = fileManage.rename(toRenameFCB.getID(), newName);

				if (result == Status_Type.all_right) {
					selectedNode.setUserObject(newName);
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

	// TODO TreeModelListener的监听
	@Override
	public void treeNodesChanged(TreeModelEvent e) {
		
	}

	@Override
	public void treeNodesInserted(TreeModelEvent e) {

	}

	@Override
	public void treeNodesRemoved(TreeModelEvent e) {
		
	}

	@Override
	public void treeStructureChanged(TreeModelEvent e) {
		
	}
	// TODO TreeExpansionListener的监听
	@Override
	public void treeExpanded(TreeExpansionEvent event) {
		
	}

	@Override
	public void treeCollapsed(TreeExpansionEvent event) {
		
	}

	// TODO TreeWillExpandListener的监听
	@Override
	public void treeWillExpand(TreeExpansionEvent event)
			throws ExpandVetoException {
		
	}

	@Override
	public void treeWillCollapse(TreeExpansionEvent event)
			throws ExpandVetoException {
		
	}

	// TODO TreeSelectionListener的监听
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		
	}

	// TODO MouseLitener的监听
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// 通过TreePath来确定移动节点
		TreePath treePath = fileSysUIView.tree.getPathForLocation(e.getX(), e.getY());
		if (treePath != null) {
			fileSysUIView.movePath = treePath;
		}
	}

	@Override
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

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
}