package newProject;


import org.eclipse.jface.wizard.Wizard;

public class NewEstimationWizard extends Wizard {
	public NewEstimationWizard() {
		// 强制设置存在back与next按钮，否则仅有的一个页面（TaskSelectionPage）中不会出现这两个按钮
		setForcePreviousAndNextButtons(true);
	}

	@Override
	public void addPages() {
		//addPage(new ProjectNamePage());
		addPage(new TaskSelectionPage());

	}

	public boolean canFinish() {
		return false;
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
