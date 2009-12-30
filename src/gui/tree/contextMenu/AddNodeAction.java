package gui.tree.contextMenu;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;

import entity.EstimateNode;
import gui.GUI;
import gui.tree.TreeArea;

public class AddNodeAction extends Action{

	public AddNodeAction(){
		super("新建节点");
	}
	
	public void run(){
		TreeArea treeArea = GUI.getTreeArea();
		EstimateNode node=treeArea.getSelectedNode();
		InputDialog input=new InputDialog(treeArea.getShell(),"请输入节点名称","节点名称","",null);
		if(input.open()==Window.OK){
			EstimateNode newNode=new EstimateNode(input.getValue());
			node.addChild(newNode);
			treeArea.getTreeViewer().refresh();
			if(treeArea.getTreeViewer().getExpandedState(node)==false){
				treeArea.getTreeViewer().setExpandedState(node, true);
			}
		}
	}
}
