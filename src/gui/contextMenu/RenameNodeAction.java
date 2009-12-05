package gui.contextMenu;

import entity.EstimateNode;
import gui.GUI;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;

public class RenameNodeAction extends Action{

	public RenameNodeAction(){
		super("重命名");
	}
	
	public void run(){
		EstimateNode node=GUI.getTreeArea().getSelectedNode();
		InputDialog input=new InputDialog(null,"请输入新名称","新名称","",null);
		if(input.open()==Window.OK){
			node.setName(input.getValue());
			GUI.getTreeArea().getTreeViewer().refresh();
		}
	}
}
