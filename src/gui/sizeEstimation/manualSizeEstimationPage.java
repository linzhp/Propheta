package gui.sizeEstimation;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class ManualSizeEstimationPage extends WizardPage{

	protected ManualSizeEstimationPage(String pageName) {
		super(pageName);
		this.setTitle("用户输入");
		this.setMessage("请直接输入代码规模");
	}

	@Override
	public void createControl(Composite parent) {
        Composite composite=new Composite(parent,SWT.NONE);
		
		this.setControl(composite);
	}

}
