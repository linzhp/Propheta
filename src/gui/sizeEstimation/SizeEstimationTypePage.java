package gui.sizeEstimation;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class SizeEstimationTypePage extends WizardPage{

	protected SizeEstimationTypePage(String pageName) {
		super(pageName);
		this.setTitle("估算方式");
		this.setMessage("请选择代码规模估算方式");
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite=new Composite(parent,SWT.NONE);
		
		this.setControl(composite);
	}

	/*
	public IWizardPage getNextPage(){
		
		return null;
	}
	*/
}
