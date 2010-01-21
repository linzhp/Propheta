package gui.tabs;

import estimation.entity.EstimateNode;
import gui.GUI;

import org.eclipse.swt.custom.CTabFolder;

public abstract class NewResultTabAction extends NewTabAction {
	private EstimateNode node;
	public NewResultTabAction(EstimateNode node)
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
