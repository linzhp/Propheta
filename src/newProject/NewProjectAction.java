package newProject;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;


public class NewProjectAction extends Action {
	public NewProjectAction()
	{
		super("新建项目");
	}
	
	@Override
	public void run(){
		WizardDialog wizardDlg = new WizardDialog(null, new NewProjectWizard());
		wizardDlg.open();
	}
}
