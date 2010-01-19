package gui.sizeEstimation;

import org.eclipse.jface.wizard.WizardPage;

public abstract class BaseWizardPage extends WizardPage{

	protected BaseWizardPage(String pageName) {
		super(pageName);
		// TODO Auto-generated constructor stub
	}

	protected abstract boolean isEndPage();
	protected abstract int getSize();
	
}
