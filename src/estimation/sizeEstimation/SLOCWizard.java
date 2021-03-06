package estimation.sizeEstimation;


public class SLOCWizard extends SizeEstimationWizard {

	// wizardPage
	private SLOCTypePage sizeEstimationTypePage;
	private HistoricalDataBaseSizeEstimationPage historicalDataBaseSizeEstimationPage;
	private CocomoNewDevelopedPage cocomoNewDevelopedPage;
	private CocomoReusedPage cocomoReusedPage;
	private CocomoMaintainedPage cocomoMaintainedPage;
	private FunctionPointPage functionPointPage;

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

	public SLOCWizard() {
		sizeEstimationTypePage = new SLOCTypePage();
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
		BaseSizePage currentPage = (BaseSizePage) this.getContainer()
				.getCurrentPage();
		size = currentPage.getSize();
		return true;
	}

	@Override
	public boolean canFinish() {
		BaseSizePage currentPage = (BaseSizePage) this.getContainer()
				.getCurrentPage();
		return currentPage.getNextPage() == null;
	}

}
