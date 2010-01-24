package gui.tabs;

import estimation.entity.EstimateNode;
import gui.GUI;

import org.eclipse.swt.custom.CTabFolder;

public abstract class ShowResultTabAction extends ShowTabAction {
	private EstimateNode node;
	public ShowResultTabAction(EstimateNode node)
	{
		super("确定");
		this.node = node;
	}

	@Override
	protected CTabFolder getTabFolder() {
		return GUI.getButtomContentArea();
	}

	@Override 
	public EstimateNode getNode() {
		return node;
	}
}
