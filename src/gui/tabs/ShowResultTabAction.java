package gui.tabs;

import gui.GUI;

import org.eclipse.swt.custom.CTabFolder;

import data.database.dataEntities.EstimateNode;

public abstract class ShowResultTabAction extends ShowTabAction {
	private ParameterArea param;
	public ShowResultTabAction(ParameterArea param)
	{
		super("确定");
		this.param = param;
	}

	@Override
	protected CTabFolder getTabFolder() {
		return GUI.getButtomContentArea();
	}

	@Override 
	public EstimateNode getNode() {
		return param.getNode();
	}
	
	@Override
	public void run(){
		super.run();
		param.setIsInformationChanged(false);
	}
}
