package newProject.sizeEstimate;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

public class SizeEstimateWizard extends Wizard {

	public void addPages() {
		addPage(new COCOMOSizePage());
		addPage(new ResultPage());
	}

	private COCOMOSizePage getCOCOMOSizePage() {
		return (COCOMOSizePage) getPage(COCOMOSizePage.PAGE_NAME);
	}
	
	private double getCOCOMOSize() {
		return (double) getCOCOMOSizePage().getSize();
	}
	
	public IWizardPage getNextPage(IWizardPage page)
	{
	    IWizardPage nextPage = super.getNextPage(page);
	    if (nextPage instanceof ResultPage)
	    {
	    	ResultPage result = (ResultPage) nextPage;
	    	result.updateText("规模估算值为：" + getCOCOMOSize());
	    }
	    return nextPage;
	}
	
	public boolean canFinish() {
		if (getContainer().getCurrentPage() instanceof ResultPage)
			return true;
		else
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
