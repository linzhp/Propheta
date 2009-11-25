package newProject.sizeEstimate;

import newProject.quickEstimate.PIFactorPage;

import org.eclipse.jface.wizard.Wizard;

public class SizeEstimateWizard extends Wizard {

	public void addPages() {
		//addPage(new PIFactorPage());
		addPage(new COCOMOSizePage());
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
