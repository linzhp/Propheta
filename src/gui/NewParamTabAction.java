package gui;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;

import estimation.entity.EstimateNode;
import gui.widgets.ParameterArea;

public abstract class NewParamTabAction extends Action {
	protected EstimateNode node;
	
	protected NewParamTabAction(String text)
	{
		super(text);
	}
	
	@Override
	public void run()
	{
		CTabFolder parent = GUI.getTopContentArea();
		node = GUI.getTreeArea().getSelectedNode();
		boolean opened = false;
		for(CTabItem tab:parent.getItems())
		{
			ParameterArea tabContent = (ParameterArea)tab.getControl();
			if(tabContent.getnodeID() == node.getId() && tabContent.getClass() == pageClass())
			{
				parent.setSelection(tab);
				opened = true;
				break;
			}
		}
		if(opened == false)
		{
			GUI.createNewTab(getTabTitle(), createContents(parent));			
		}
		parent.setFocus();
	}
	
	protected abstract Composite createContents(Composite parent);
	protected abstract Class<? extends ParameterArea> pageClass();
	protected abstract String getTabTitle();
}
