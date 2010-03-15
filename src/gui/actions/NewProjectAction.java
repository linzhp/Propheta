package gui.actions;

import data.database.dataEntities.EstimateNode;
import data.database.dataEntities.SuperRoot;
import gui.GUI;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;

public class NewProjectAction extends Action {
	public NewProjectAction()
	{
		super("新建项目");
	}
	
	@Override
	public void run(){
		InputDialog input=new InputDialog(null,"新建根节点","节点名称","",new NodeNameValidator());
		if(input.open()==Window.OK){
			EstimateNode node=new EstimateNode(input.getValue().trim());
			TreeViewer treeViewer = GUI.getTreeArea().getTreeViewer();
			((SuperRoot)treeViewer.getInput()).addChild(node);
			treeViewer.refresh();
			treeViewer.setSelection(new StructuredSelection(node));
		}
		
	}
}


