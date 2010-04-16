package estimation.sizeEstimation;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Text;

public class SizeEstimationWizard extends Wizard {

	// wizardPage
	private SizeEstimationTypePage sizeEstimationTypePage;
	private ManualSizeEstimationPage manualSizeEstimationPage;
	private HistoricalDataBaseSizeEstimationPage historicalDataBaseSizeEstimationPage;
	private CocomoNewDevelopedPage cocomoNewDevelopedPage;
	private CocomoReusedPage cocomoReusedPage;
	private CocomoMaintainedPage cocomoMaintainedPage;
	private FunctionPointPage functionPointPage;

	// 用户输入

	// 关联的Text
	private Text textSLOC;

	public ManualSizeEstimationPage getManualSizeEstimationPage() {
		return manualSizeEstimationPage;
	}

	public void setManualSizeEstimationPage(
			ManualSizeEstimationPage manualSizeEstimationPage) {
		this.manualSizeEstimationPage = manualSizeEstimationPage;
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

	public Text getTextSLOC() {
		return textSLOC;
	}

	public void setTextSLOC(Text textSLOC) {
		this.textSLOC = textSLOC;
	}

	public SizeEstimationWizard(Text text) {
		setTextSLOC(text);

		sizeEstimationTypePage = new SizeEstimationTypePage();
		manualSizeEstimationPage = new ManualSizeEstimationPage();
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
		this.addPage(manualSizeEstimationPage);
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
		int size = currentPage.getSize();
		this.textSLOC.setText(String.valueOf(size));
		return true;
	}

	@Override
	public boolean canFinish() {
		BaseWizardPage currentPage = (BaseWizardPage) this.getContainer()
				.getCurrentPage();
		return currentPage.getNextPage() == null;
	}

}
