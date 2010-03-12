package gui.tabs;

import org.eclipse.swt.custom.CTabFolder;

import data.database.dataEntities.EstimateNode;


import gui.GUI;

public abstract class ShowParamTabAction extends ShowTabAction {
	
	protected ShowParamTabAction(String text)
	{
		super(text);
	}
	
	@Override
	protected CTabFolder getTabFolder(){
		return GUI.getTopContentArea();
	}
	
	@Override
	protected EstimateNode getNode(){
		return GUI.getTreeArea().getSelectedNode();
	}
}
