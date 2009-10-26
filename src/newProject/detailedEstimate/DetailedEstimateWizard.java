package newProject.detailedEstimate;

import newProject.quickEstimate.FactorValuePage;
import newProject.quickEstimate.ResultPage;

import org.eclipse.jface.wizard.Wizard;

public class DetailedEstimateWizard extends Wizard {

	public void addPages() {
		// addPage(new FactorPage());
		addPage(new FactorValuePage());
		addPage(new ResultPage());
	}

	public boolean performFinish() {
		if (this.canFinish()) {
			this.dispose();
			return true;
		} else
			return false;
	}

	public boolean performCancel() {
		this.dispose();
		return true;
	}
}
