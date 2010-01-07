package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * 页面基类
 * @author Administrator
 *
 */
public class BasePage extends Composite{

	protected int pageType;
	protected int nodeID;
	
	public BasePage(Composite parent){
		super(parent, SWT.NONE);
	}

	public int getPageType() {
		return pageType;
	}

	public void setPageType(int pageType) {
		this.pageType = pageType;
	}

	public int getNodeID() {
		return nodeID;
	}

	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}
	
	
}
