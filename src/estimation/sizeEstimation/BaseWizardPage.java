package estimation.sizeEstimation;

import org.eclipse.jface.wizard.WizardPage;

public abstract class BaseWizardPage extends WizardPage{

	protected BaseWizardPage(String pageName) {
		super(pageName);
	}

	protected abstract boolean isEndPage();
	protected abstract int getSize();
	
}
