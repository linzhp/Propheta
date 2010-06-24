package estimation.sizeEstimation;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

public class CocomoReusedPage extends BaseSizePage{

	private Spinner textReuseSize,textReuseAT,textReuseDM,textReuseCM,textReuseIM,textReuseAA,textReuseSU,textReuseUNFM;
	public static final String NAME="cocomo规模估算:重用的代码";
	
	
	public Spinner getTextReuseSize() {
		return textReuseSize;
	}

	public void setTextReuseSize(Spinner textReuseSize) {
		this.textReuseSize = textReuseSize;
	}

	public Spinner getTextReuseAT() {
		return textReuseAT;
	}

	public void setTextReuseAT(Spinner textReuseAT) {
		this.textReuseAT = textReuseAT;
	}

	public Spinner getTextReuseDM() {
		return textReuseDM;
	}

	public void setTextReuseDM(Spinner textReuseDM) {
		this.textReuseDM = textReuseDM;
	}

	public Spinner getTextReuseCM() {
		return textReuseCM;
	}

	public void setTextReuseCM(Spinner textReuseCM) {
		this.textReuseCM = textReuseCM;
	}

	public Spinner getTextReuseIM() {
		return textReuseIM;
	}

	public void setTextReuseIM(Spinner textReuseIM) {
		this.textReuseIM = textReuseIM;
	}

	public Spinner getTextReuseAA() {
		return textReuseAA;
	}

	public void setTextReuseAA(Spinner textReuseAA) {
		this.textReuseAA = textReuseAA;
	}

	public Spinner getTextReuseSU() {
		return textReuseSU;
	}

	public void setTextReuseSU(Spinner textReuseSU) {
		this.textReuseSU = textReuseSU;
	}

	public Spinner getTextReuseUNFM() {
		return textReuseUNFM;
	}

	public void setTextReuseUNFM(Spinner textReuseUNFM) {
		this.textReuseUNFM = textReuseUNFM;
	}

	
	protected CocomoReusedPage() {
		super(NAME);
		this.setTitle("cocomo规模估算");
		this.setMessage("重用的代码");
	}

	@Override
	public void createControl(Composite parent) {
        Composite composite=new Composite(parent,SWT.NONE);
		GridLayout layout=new GridLayout(2,false);
		layout.marginLeft=20;
		composite.setLayout(layout);
		
        textReuseSize = createDataField(composite, "新增和修改的代码量(LOC)：", 0, 0, Spinner.LIMIT, 0);
		textReuseAT = createDataField(composite, "自动转换的代码比例(百分值，范围1-100)：", 0, 0, 100, 0);
		textReuseDM = createDataField(composite, "设计修改比例(百分值，范围1-100)：", 0, 0, 100, 0);
		textReuseCM = createDataField(composite, "代码修改比例(百分值，范围1-100)：", 0, 0, 100, 0);
		textReuseIM = createDataField(composite, "集成工作量比例(百分值，范围1-100)：", 0, 0, 100, 0);
		textReuseAA = createDataField(composite, "代码适用程度(范围0-8，0表示完全适用):", 0, 0, 8, 0);
		textReuseSU = createDataField(composite, "代码可理解度(范围10-50，10表示代码很好理解)：", 10, 10, 50, 0);
		textReuseUNFM = createDataField(composite, "开发人员不熟悉度(范围0-1，0表示完全熟悉)：", 0, 0, 10, 1);
	
		this.setControl(composite);
	}

	private Spinner createDataField(Composite parent, String desc, int selection, int minimum, int maximum, int digits ){
		new Label(parent,SWT.NONE).setText(desc);
		
		Spinner inputSpinner = new Spinner(parent, SWT.BORDER);
		inputSpinner.setMinimum(minimum);
		inputSpinner.setMaximum(maximum);
		inputSpinner.setSelection(selection);
		inputSpinner.setDigits(digits);
		inputSpinner.setSize(30, 20);
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		inputSpinner.setLayoutData(gd);
		return inputSpinner;
	}
	
	
	@Override
	public IWizardPage getNextPage() {
		return this.getWizard().getPage(CocomoMaintainedPage.NAME);
	}

	@Override
	protected int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

}