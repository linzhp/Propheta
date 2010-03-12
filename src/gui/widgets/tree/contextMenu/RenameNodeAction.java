package gui.widgets.tree.contextMenu;

import data.database.dataEntities.EstimateNode;
import estimation.entity.EstimationProjects;
import gui.GUI;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;

public class RenameNodeAction extends Action{

	public RenameNodeAction(){
		super("重命名");
	}
	
	public void run(){
		EstimateNode node=GUI.getTreeArea().getSelectedNode();
		if(node.isRoot()){
			InputDialog input=new InputDialog(null,"请输入新的项目名称","新项目名称","",new ProjectNameValidator());
			if(input.open()==Window.OK){
				node.renameNode(input.getValue().trim());
				GUI.getTreeArea().getTreeViewer().refresh();
			}
		}else{
			InputDialog input=new InputDialog(null,"请输入新的节点名称","新节点名称","",new NodeNameValidator());
			if(input.open()==Window.OK){
				node.renameNode(input.getValue().trim());
				GUI.getTreeArea().getTreeViewer().refresh();
			}
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
				//判断是否存在同名项目
				if(EstimationProjects.isEstimateProjectExist(projectName)==true){
					return "已存在名称为 "+projectName+"  的项目!";
				}else{
					return null;
				}
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
				//判断是否存在同名兄弟节点
				if(GUI.getTreeArea().getSelectedNode().getParent().isChildExist(nodeName)==true){
					return "已存在名称为 "+nodeName+"  的节点!";
				}else{
					return null;
				}
			}
		}
	}
}
