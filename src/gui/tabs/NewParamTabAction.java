package gui.tabs;

import org.eclipse.swt.custom.CTabFolder;

import gui.GUI;

public abstract class NewParamTabAction extends NewTabAction {
	
	protected NewParamTabAction(String text)
	{
		super(text);
	}
	
	@Override
	protected CTabFolder getTabFolder(){
		return GUI.getTopContentArea();
	}
	
}
