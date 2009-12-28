package gui.tree.contextMenu;

import org.eclipse.jface.action.Action;

import entity.EstimationProjects;

public class SaveEstimationProjectsAction extends Action{

	public SaveEstimationProjectsAction(){
		super("保存");
	}
	
	@Override
	public void run(){
		EstimationProjects.saveEstimationProjects();
	}
}
