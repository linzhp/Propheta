package gui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;


public class ContentArea extends Composite{

	public ContentArea(Composite parent, int style) {
		super(parent, style);
		
	}
	
	
	public void disposeCurrentPage(){
		Control[] controls=this.getChildren();
	    for(int i=0;i<controls.length;i++){
	    	controls[i].dispose();
	    }
	}

}
