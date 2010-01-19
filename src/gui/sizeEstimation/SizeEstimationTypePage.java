package gui.sizeEstimation;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class SizeEstimationTypePage extends BaseWizardPage{

	private Button manualSLOCButton,historicalSLOCButton,cocomoSLOCButton;
	private int estimationType=1;
	
	public void setEstimationType(int estimationType){
		this.estimationType=estimationType;
	}
	
	protected SizeEstimationTypePage(String pageName) {
		super(pageName);
		this.setTitle("估算方式");
		this.setMessage("请选择代码规模估算方式");
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite=new Composite(parent,SWT.NONE);
		GridLayout gd=new GridLayout(1,false);
		gd.marginLeft=20;
		composite.setLayout(gd);
		
		manualSLOCButton=new Button(composite,SWT.RADIO);
		manualSLOCButton.setText("用户输入");
		manualSLOCButton.setSelection(true);
		manualSLOCButton.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				setEstimationType(1);
			}});
		
		historicalSLOCButton=new Button(composite,SWT.RADIO);
		historicalSLOCButton.setText("参考历史数据");
		historicalSLOCButton.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				setEstimationType(2);
			}});
		
		cocomoSLOCButton=new Button(composite,SWT.RADIO);
		cocomoSLOCButton.setText("cocomo规模估算");
		cocomoSLOCButton.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				setEstimationType(3);
			}});
		
		this.setControl(composite);
	}

	
	public IWizardPage getNextPage(){
		SizeEstimationWizard seWizard=(SizeEstimationWizard)this.getWizard();
		if(this.estimationType==1){
			return seWizard.getManualSizeEstimationPage();
		}else if(this.estimationType==2){
			return seWizard.getHistoricalDataBaseSizeEstimationPage();
		}else if(this.estimationType==3){
			return seWizard.getCocomoSizeEstimation_newDeveloped_Page();
		}else{
			return null;
		}
	}

	@Override
	protected int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected boolean isEndPage() {
		// TODO Auto-generated method stub
		return false;
	}

}
