package com.boy;

import java.awt.*;

import javax.swing.*;
import javax.swing.tree.*;

public class FileSysUIView extends JPanel {
	private static final long serialVersionUID = 1L;

	// Background Image
	ImageIcon backgroundImage = null;
	
	JPanel buttonPane = null;
	JSplitPane treeAndEditPane = null;
			
	DefaultMutableTreeNode rootNode = null;
	DefaultTreeModel treeModel = null;
	JTree tree = null;
	TreePath movePath = null;
	JTextArea textArea = null;
	JScrollPane treeScrollPane = null, editScrollPane = null;
	
	Font font = null;
	
	JButton formatButton = null;
	JButton newDirButton = null;
	JButton newFileButton = null;
	JButton deleteButton = null;
	JButton renameButton = null;
	JButton openButton = null;
	JButton saveAndCloseButton = null;
	
	public FileSysUIView() {
		backgroundImage = new ImageIcon(".\\Resource\\background.png");
		this.setLayout(null);

		buttonPane = new JPanel(new GridLayout(1, Constants.NUMBER_OF_BUTTONS));
		buttonPane.setBounds(0, 0, Constants.BUTTON_PANE_LENGTH, Constants.BUTTON_PANE_WIDTH);
//		buttonPane.setOpaque(false);
		this.add(buttonPane);

		rootNode = new DefaultMutableTreeNode("Root", true);
		treeModel = new DefaultTreeModel(rootNode);
		
		tree = new JTree(treeModel);
		tree.setCellRenderer(new TreeCellRenderer());
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);

		treeScrollPane = new JScrollPane(tree);
		editScrollPane = new JScrollPane(textArea);
		
		treeAndEditPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				true, treeScrollPane, editScrollPane);
		treeAndEditPane.setBounds(0, Constants.BUTTON_PANE_WIDTH,
				Constants.TREE_AND_EDIT_PANE_LENGTH - 5, Constants.TREE_AND_EDIT_PANE_WIDTH - 50);
		treeAndEditPane.setDividerLocation(200);
		this.add(treeAndEditPane);
		
		font = new Font("Times New Roman", Font.BOLD, 23);
		
		formatButton = new JButton("format");
		newDirButton = new JButton("new dir");
		newFileButton = new JButton("new file");
		deleteButton = new JButton("delete");
		renameButton = new JButton("rename");
		openButton = new JButton("open");
		saveAndCloseButton = new JButton("close");
		
		formatButton.setFont(font);
		formatButton.setOpaque(false);
		newDirButton.setFont(font);
		newDirButton.setOpaque(false);
		newFileButton.setFont(font);
		newFileButton.setOpaque(false);
		deleteButton.setFont(font);
		deleteButton.setOpaque(false);
		renameButton.setFont(font);
		renameButton.setOpaque(false);
		openButton.setFont(font);
		openButton.setOpaque(false);
		saveAndCloseButton.setFont(font);
		saveAndCloseButton.setOpaque(false);
		
		buttonPane.add(formatButton);
		buttonPane.add(newDirButton);
		buttonPane.add(newFileButton);
		buttonPane.add(deleteButton);
		buttonPane.add(renameButton);
		buttonPane.add(openButton);
		buttonPane.add(saveAndCloseButton);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintChildren(g);
		g.drawImage(backgroundImage.getImage(), 0, 0, null);
	}
}

class TreeCellRenderer extends DefaultTreeCellRenderer {
	private static final long serialVersionUID = 1L;
	
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
		
		if (node.getAllowsChildren()) {
			if (expanded) {
				this.setIcon(new ImageIcon(".\\Resource\\icons\\folder_open.png"));
			} else {
				this.setIcon(new ImageIcon(".\\Resource\\icons\\folder_close.png"));
			}
		} else {
			this.setIcon(new ImageIcon(".\\Resource\\icons\\file.png"));
		}
		if (node.isRoot()) {
			this.setIcon(new ImageIcon(".\\Resource\\icons\\root.png"));
		}
		return this;
	}
}