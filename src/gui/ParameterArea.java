package gui;

import org.eclipse.swt.*;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.widgets.*;


public abstract class ParameterArea extends Composite{
	protected FormToolkit toolkit;
	protected ScrolledForm form;
	protected int nodeID;
	
	public ParameterArea(Composite parent, int nodeID){
		super(parent, SWT.NONE);
		this.nodeID = nodeID;
		toolkit = new FormToolkit(Display.getCurrent());
		setLayout(new FillLayout());
		form = toolkit.createScrolledForm(this);
		form.setText("估算参数设置");
		Composite body = form.getBody();
		body.setLayout(new ColumnLayout());
	}
	
	public int getnodeID(){
		return nodeID;
	}
	
	@Override
	public void dispose(){
		toolkit.dispose();
		super.dispose();
	}
}
