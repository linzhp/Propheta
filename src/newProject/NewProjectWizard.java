package newProject;

import org.eclipse.jface.wizard.Wizard;

import quickEstimate.FactorPage;
import quickEstimate.FactorValuePage;
import quickEstimate.ResultPage;

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
