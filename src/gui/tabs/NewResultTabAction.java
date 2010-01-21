package gui.tabs;

import gui.GUI;

import org.eclipse.swt.custom.CTabFolder;

public abstract class NewResultTabAction extends NewTabAction {
	public NewResultTabAction(String text)
	{
		super(text);
	}

	@Override
	protected CTabFolder getTabFolder() {
		return GUI.getButtomContentArea();
	}

}
