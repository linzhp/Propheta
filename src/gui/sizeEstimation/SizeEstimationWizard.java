package gui.sizeEstimation;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Text;

public class SizeEstimationWizard extends Wizard{

	//wizardPage
	private SizeEstimationTypePage sizeEstimationTypePage;
	private ManualSizeEstimationPage manualSizeEstimationPage;
	private HistoricalDataBaseSizeEstimationPage historicalDataBaseSizeEstimationPage;
	private CocomoSizeEstimation_newDeveloped_Page cocomoSizeEstimation_newDeveloped_Page;
	private CocomoSizeEstimation_reused_Page cocomoSizeEstimation_reused_Page;
	private CocomoSizeEstimation_maintained_Page cocomoSizeEstimation_maintained_Page;
	
	//用户输入
	
	//关联的Text
	private Text textSLOC;	
	
	public SizeEstimationTypePage getSizeEstimationTypePage() {
		return sizeEstimationTypePage;
	}

	public void setSizeEstimationTypePage(
			SizeEstimationTypePage sizeEstimationTypePage) {
		this.sizeEstimationTypePage = sizeEstimationTypePage;
	}

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

	public void setHistoricalDataBaseSizeEstimationPage(
			HistoricalDataBaseSizeEstimationPage historicalDataBaseSizeEstimationPage) {
		this.historicalDataBaseSizeEstimationPage = historicalDataBaseSizeEstimationPage;
	}

	public CocomoSizeEstimation_newDeveloped_Page getCocomoSizeEstimation_newDeveloped_Page() {
		return cocomoSizeEstimation_newDeveloped_Page;
	}

	public void setCocomoSizeEstimation_newDeveloped_Page(
			CocomoSizeEstimation_newDeveloped_Page cocomoSizeEstimationNewDevelopedPage) {
		cocomoSizeEstimation_newDeveloped_Page = cocomoSizeEstimationNewDevelopedPage;
	}

	public CocomoSizeEstimation_reused_Page getCocomoSizeEstimation_reused_Page() {
		return cocomoSizeEstimation_reused_Page;
	}

	public void setCocomoSizeEstimation_reused_Page(
			CocomoSizeEstimation_reused_Page cocomoSizeEstimationReusedPage) {
		cocomoSizeEstimation_reused_Page = cocomoSizeEstimationReusedPage;
	}

	public CocomoSizeEstimation_maintained_Page getCocomoSizeEstimation_maintained_Page() {
		return cocomoSizeEstimation_maintained_Page;
	}

	public void setCocomoSizeEstimation_maintained_Page(
			CocomoSizeEstimation_maintained_Page cocomoSizeEstimationMaintainedPage) {
		cocomoSizeEstimation_maintained_Page = cocomoSizeEstimationMaintainedPage;
	}

	public Text getTextSLOC() {
		return textSLOC;
	}

	public void setTextSLOC(Text textSLOC) {
		this.textSLOC = textSLOC;
	}

	public SizeEstimationWizard(Text text){
		setTextSLOC(text);
		
		sizeEstimationTypePage=new SizeEstimationTypePage("请选择估算方式");
		manualSizeEstimationPage=new ManualSizeEstimationPage("人工输入");
		historicalDataBaseSizeEstimationPage=new HistoricalDataBaseSizeEstimationPage("参考历史数据");
		cocomoSizeEstimation_newDeveloped_Page=new CocomoSizeEstimation_newDeveloped_Page("新开发的代码");
		cocomoSizeEstimation_reused_Page=new CocomoSizeEstimation_reused_Page("重用的代码");
		cocomoSizeEstimation_maintained_Page=new CocomoSizeEstimation_maintained_Page("维护的代码");
	
		this.setWindowTitle("代码规模估算");
		this.addPage(sizeEstimationTypePage);
		this.addPage(manualSizeEstimationPage);
		this.addPage(historicalDataBaseSizeEstimationPage);
		this.addPage(cocomoSizeEstimation_newDeveloped_Page);
		this.addPage(cocomoSizeEstimation_reused_Page);
		this.addPage(cocomoSizeEstimation_maintained_Page);
	}
	
	
	@Override
	public boolean performFinish() {
		BaseWizardPage currentPage=(BaseWizardPage)this.getContainer().getCurrentPage();
		if(currentPage.isEndPage()==true){
			int size=currentPage.getSize();
			this.textSLOC.setText(String.valueOf(size));
		}
		return true;
	}
	
	public boolean canFinish(){
		BaseWizardPage currentPage=(BaseWizardPage)this.getContainer().getCurrentPage();
		return currentPage.isEndPage();
	}

}
