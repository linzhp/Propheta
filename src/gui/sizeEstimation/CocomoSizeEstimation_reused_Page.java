package gui.sizeEstimation;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class CocomoSizeEstimation_reused_Page extends WizardPage{

	protected CocomoSizeEstimation_reused_Page(String pageName) {
		super(pageName);
		this.setTitle("cocomo规模估算");
		this.setMessage("重用的代码");
	}

	@Override
	public void createControl(Composite parent) {
        Composite composite=new Composite(parent,SWT.NONE);
		
		this.setControl(composite);
	}
}