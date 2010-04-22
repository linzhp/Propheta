package estimation.sizeEstimation;

import org.eclipse.jface.wizard.Wizard;

public class SizeEstimationWizard extends Wizard {

	// wizardPage
	private SizeEstimationTypePage sizeEstimationTypePage;
	private HistoricalDataBaseSizeEstimationPage historicalDataBaseSizeEstimationPage;
	private CocomoNewDevelopedPage cocomoNewDevelopedPage;
	private CocomoReusedPage cocomoReusedPage;
	private CocomoMaintainedPage cocomoMaintainedPage;
	private FunctionPointPage functionPointPage;

	// 用户输入

	// 关联的Text
	private int size;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public HistoricalDataBaseSizeEstimationPage getHistoricalDataBaseSizeEstimationPage() {
		return historicalDataBaseSizeEstimationPage;
	}

	public CocomoNewDevelopedPage getCocomoNewDevelopedPage() {
		return cocomoNewDevelopedPage;
	}

	public CocomoReusedPage getCocomoReusedPage() {
		return cocomoReusedPage;
	}

	public CocomoMaintainedPage getCocomoMaintainedPage() {
		return cocomoMaintainedPage;
	}

	public SizeEstimationWizard() {
		sizeEstimationTypePage = new SizeEstimationTypePage();
		historicalDataBaseSizeEstimationPage = new HistoricalDataBaseSizeEstimationPage();
		cocomoNewDevelopedPage = new CocomoNewDevelopedPage();
		cocomoReusedPage = new CocomoReusedPage();
		cocomoMaintainedPage = new CocomoMaintainedPage();
		functionPointPage = new FunctionPointPage();

		this.setWindowTitle("代码规模估算");
	}

	@Override
	public void addPages() {
		this.addPage(sizeEstimationTypePage);
		this.addPage(historicalDataBaseSizeEstimationPage);
		this.addPage(cocomoNewDevelopedPage);
		this.addPage(cocomoReusedPage);
		this.addPage(cocomoMaintainedPage);
		this.addPage(functionPointPage);
	}

	@Override
	public boolean performFinish() {
		BaseWizardPage currentPage = (BaseWizardPage) this.getContainer()
				.getCurrentPage();
		size = currentPage.getSize();
		return true;
	}

	@Override
	public boolean canFinish() {
		BaseWizardPage currentPage = (BaseWizardPage) this.getContainer()
				.getCurrentPage();
		return currentPage.getNextPage() == null;
	}

}
