package newProject.quickEstimate;

import org.eclipse.jface.wizard.Wizard;

public class QuickEstimateWizard extends Wizard {

	public void addPages() {
		addPage(new FactorPage());
		addPage(new FactorValuePage());
		addPage(new ResultPage());
	}

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	public boolean performCancel() {
		return true;
	}
}
