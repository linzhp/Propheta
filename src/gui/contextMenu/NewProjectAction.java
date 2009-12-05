package gui.contextMenu;

import entity.EstimateNode;
import gui.GUI;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;

public class NewProjectAction extends Action {
	public NewProjectAction()
	{
		super("新建项目");
	}
	
	@Override
	public void run(){
		InputDialog input=new InputDialog(null,"请输入项目名称","项目名称","",new ProjectNameValidator());
		if(input.open()==Window.OK){
			EstimateNode node=new EstimateNode(input.getValue().trim());
			GUI.getTreeArea().addEstimateProjet(node);
			GUI.getTreeArea().getTreeViewer().setSelection(new StructuredSelection(node));
		}
		
	}
	
	
	class ProjectNameValidator implements IInputValidator {
		@Override
		public String isValid(String text) {
			if(text.trim().length()==0){
				return "!!!";
			}
			return null;
		}
	}
	
}


