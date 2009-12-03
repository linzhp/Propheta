package newProject;

import entity.EstimateNode;
import gui.GUI;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;

public class NewProjectAction extends Action {
	public NewProjectAction()
	{
		super("新建项目");
	}
	
	@Override
	public void run(){
		InputDialog input=new InputDialog(null,"请输入项目名称","项目名称","",null);
		if(input.open()==Window.OK){
			GUI.getTreeArea().addEstimateProjet(new EstimateNode(input.getValue()));
		}
		
	}
}
