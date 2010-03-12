package gui.widgets.tree.contextMenu;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;

import data.database.dataEntities.EstimateNode;

import gui.GUI;
import gui.widgets.tree.TreeArea;

public class AddNodeAction extends Action{

	public AddNodeAction(){
		super("新建节点");
	}
	
	public void run(){
		TreeArea treeArea = GUI.getTreeArea();
		EstimateNode node=treeArea.getSelectedNode();
		InputDialog input=new InputDialog(treeArea.getShell(),"请输入节点名称","节点名称","",new NodeNameValidator());
		if(input.open()==Window.OK){
			EstimateNode newNode=new EstimateNode(input.getValue().trim());
			node.addChild(newNode);
			treeArea.getTreeViewer().refresh();
			if(treeArea.getTreeViewer().getExpandedState(node)==false){
				treeArea.getTreeViewer().setExpandedState(node, true);
			}
		}
	}
	
	//节点名称验证
	class NodeNameValidator implements IInputValidator {
		@Override
		public String isValid(String text) {
			String nodeName=text.trim();
			if(nodeName.length()==0){
				return "请输入节点名称";
			}else{
				if(GUI.getTreeArea().getSelectedNode().isChildExist(nodeName)==true){
					return "已存在名称为 "+nodeName+"  的节点!";
				}else{
					return null;
				}
			}
		}
	}
}
