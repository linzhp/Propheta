package estimation;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;

public class NewEstimationAction extends Action {
	public NewEstimationAction()
	{
		super("估算");
	}
	
	@Override
	public void run(){
		NewEstimationWizard newEstimationWizard = new NewEstimationWizard();
		WizardDialog wizardDlg = new WizardDialog(null, newEstimationWizard);
		wizardDlg.open();
		newEstimationWizard.dispose();
	}
}
