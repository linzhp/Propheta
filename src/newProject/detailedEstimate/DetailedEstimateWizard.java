package newProject.detailedEstimate;

import newProject.quickEstimate.PIFactorPage;

import org.eclipse.jface.wizard.Wizard;

public class DetailedEstimateWizard extends Wizard {

	public void addPages() {
		addPage(new PIFactorPage());
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
