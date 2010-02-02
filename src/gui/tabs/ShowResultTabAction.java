package gui.tabs;

import estimation.entity.EstimateNode;
import gui.GUI;

import org.eclipse.swt.custom.CTabFolder;

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
