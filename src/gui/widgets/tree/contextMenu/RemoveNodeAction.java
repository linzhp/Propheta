package gui.widgets.tree.contextMenu;

import data.database.dataEntities.EstimateNode;
import estimation.entity.EstimationProjects;
import gui.GUI;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

public class RemoveNodeAction extends Action{

	public RemoveNodeAction(){
		super("删除");
	}
	
	public void run(){
		EstimateNode node=GUI.getTreeArea().getSelectedNode();
		if(node.isRoot()){
			boolean result=MessageDialog.openConfirm(Display.getCurrent().getActiveShell(), "确认", "确定要删除项目?");
		    if(result==true){
		    	EstimationProjects.removeEstimateProject(node);
		    }
		}else{
			boolean result=MessageDialog.openConfirm(Display.getCurrent().getActiveShell(), "确认", "确定要删除节点?");
		    if(result==true){
		    	node.getParent().removeChild(node);
		    	GUI.getTreeArea().getTreeViewer().refresh();
		    }
		}
	}
}
