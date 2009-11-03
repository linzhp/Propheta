package newProject.quickEstimate;


import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

public class QuickEstimateWizard extends Wizard {

	public void addPages() {
		addPage(new SizePage());
		addPage(new PIPage());
		addPage(new SummaryPage());
	}

	private SizePage getSizePage() {
		return (SizePage) getPage(SizePage.PAGE_NAME);
	}

	private PIPage getPIPage() {
		return (PIPage) getPage(PIPage.PAGE_NAME);
	}

	private double getSize() {
		return (double) getSizePage().getSize();
	}

	private double getPIE() {
		return getPIPage().getPIE();
	}

	private double getPID() {
		return getPIPage().getPID();
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

	public IWizardPage getNextPage(IWizardPage page) {
		IWizardPage nextPage = super.getNextPage(page);
		if (nextPage instanceof SummaryPage) {
			SummaryPage summaryPage = (SummaryPage) nextPage;
			summaryPage.updateData(getSize(), getPIE(), getPID());
			return summaryPage;
		} else
			return nextPage;
	}
}
