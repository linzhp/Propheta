package gui;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;

import entity.EstimateNode;

public class AddNodeAction extends Action{

	public AddNodeAction(){
		super("新建节点");
	}
	
	public void run(){
		EstimateNode node=GUI.getTreeArea().getSelectedNode();
		InputDialog input=new InputDialog(null,"请输入节点名称","节点名称","",null);
		if(input.open()==Window.OK){
			EstimateNode newNode=new EstimateNode(input.getValue());
			newNode.setParent(node);
			node.add(newNode);
			GUI.getTreeArea().getTreeViewer().refresh();
			if(GUI.getTreeArea().getTreeViewer().getExpandedState(node)==false){
				GUI.getTreeArea().getTreeViewer().setExpandedState(node, true);
			}
		}
	}
}
