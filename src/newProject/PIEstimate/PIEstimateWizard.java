package newProject.PIEstimate;

import newProject.PIEstimate.FactorPage;
import newProject.PIEstimate.FactorValuePage;
import newProject.PIEstimate.ResultPage;

import org.eclipse.jface.wizard.Wizard;

public class PIEstimateWizard extends Wizard {

	public void addPages() {
		addPage(new FactorPage());
		addPage(new FactorValuePage());
		addPage(new ResultPage());
	}

	public boolean performFinish() {
		if(this.canFinish())
		{
			this.dispose();
			return true;
		}
		else
			return false;
	}
	
	public boolean performCancel(){
		this.dispose();
		return true;
	}
}
