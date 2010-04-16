package estimation.sizeEstimation;

import org.eclipse.jface.wizard.WizardPage;

public abstract class BaseWizardPage extends WizardPage{

	protected BaseWizardPage(String pageName) {
		super(pageName);
	}

	protected abstract int getSize();
	
}
