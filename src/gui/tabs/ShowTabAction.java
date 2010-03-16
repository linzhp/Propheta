package gui.tabs;

import gui.GUI;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;

public abstract class ShowTabAction extends Action {

	public ShowTabAction(String text)
	{
		super(text);
	}
	
	@Override
	public void run()
	{
		CTabFolder parent = getTabFolder();
		int id = getTabID();
		Composite newContents = createContents(parent);
		boolean opened = false;
		for(CTabItem tab:parent.getItems())
		{
			TabContentArea tabContent = (TabContentArea)tab.getControl();
			if(tabContent.getTabID() == id && tabContent.getClass() == pageClass())
			{
				parent.setSelection(tab);
				tab.setControl(newContents);
				opened = true;
				break;
			}
		}
		if(opened == false)
		{
			GUI.createNewTab(getTabTitle(), newContents);			
		}
		parent.setFocus();
		
	}

	protected abstract CTabFolder getTabFolder();
	protected abstract Composite createContents(Composite parent);
	protected abstract Class<? extends TabContentArea> pageClass();
	protected abstract String getTabTitle();
	protected abstract int getTabID();
}
