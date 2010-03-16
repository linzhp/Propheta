package gui.tabs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import data.database.dataEntities.EstimateNode;


public class TabContentArea extends Composite {

	protected EstimateNode node;
	public TabContentArea(Composite parent, EstimateNode node) {
		super(parent, SWT.NONE);
		this.node = node;
	}

	public int getTabID(){
		return node.getId();
	}
	
}
