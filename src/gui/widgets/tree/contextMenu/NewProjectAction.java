package gui.widgets.tree.contextMenu;

import estimation.entity.EstimateNode;
import estimation.entity.EstimationProjects;
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
		InputDialog input=new InputDialog(null,"新建项目","项目名称","",new ProjectNameValidator());
		if(input.open()==Window.OK){
			EstimateNode node=new EstimateNode(input.getValue().trim());
			EstimationProjects.addEstimateProjet(node);
			GUI.getTreeArea().getTreeViewer().setSelection(new StructuredSelection(node));
		}
		
	}
	
	//项目名称验证
	class ProjectNameValidator implements IInputValidator {
		@Override
		public String isValid(String text) {
			String projectName=text.trim();
			if(projectName.length()==0){
				return "请输入项目名称";
			}else{
				if(EstimationProjects.isEstimateProjectExist(projectName)==true){
					return "已存在名称为 "+projectName+"  的项目!";
				}else{
					return null;
				}
			}
		}
	}
	
}


