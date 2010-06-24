package estimation.sizeEstimation;

import org.eclipse.jface.wizard.WizardPage;

public abstract class BaseSizePage extends WizardPage{

	protected BaseSizePage(String pageName) {
		super(pageName);
	}

	protected abstract int getSize();
	
}
