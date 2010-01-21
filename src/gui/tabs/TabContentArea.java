package gui.tabs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class TabContentArea extends Composite {

	protected int nodeID;
	public TabContentArea(Composite parent, int nodeID) {
		super(parent, SWT.NONE);
		this.nodeID = nodeID;
	}

	public int getnodeID(){
		return nodeID;
	}
	
}
