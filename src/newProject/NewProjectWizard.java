package newProject;

import org.eclipse.jface.wizard.Wizard;

public class NewProjectWizard extends Wizard {

	@Override
	public void addPages()
	{
		addPage(new TaskSelectionPage());
	}
	
	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
	}

}
