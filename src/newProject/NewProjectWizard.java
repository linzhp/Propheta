package newProject;

import newProject.quickEstimate.FactorPage;
import newProject.quickEstimate.FactorValuePage;
import newProject.quickEstimate.ResultPage;

import org.eclipse.jface.wizard.Wizard;


public class NewProjectWizard extends Wizard {

	@Override
	public void addPages()
	{
		addPage(new TaskSelectionPage());
		addPage(new FactorPage());
		addPage(new FactorValuePage());
		addPage(new ResultPage());
	}
	
	@Override
	public boolean performFinish() {
		return true;
	}

}
