package estimation.sizeEstimation;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

import estimation.COCOMO;

public class CocomoMaintainedPage extends BaseSizePage{

	private Spinner textModifySize,textAddSize,textMaintSU,textMaintUNFM;
	public static final String NAME="cocomo规模估算:维护的代码";
		
	public Spinner getTextModifySize() {
		return textModifySize;
	}

	public void setTextModifySize(Spinner textModifySize) {
		this.textModifySize = textModifySize;
	}

	public Spinner getTextAddSize() {
		return textAddSize;
	}

	public void setTextAddSize(Spinner textAddSize) {
		this.textAddSize = textAddSize;
	}

	public Spinner getTextMaintSU() {
		return textMaintSU;
	}

	public void setTextMaintSU(Spinner textMaintSU) {
		this.textMaintSU = textMaintSU;
	}

	public Spinner getTextMaintUNFM() {
		return textMaintUNFM;
	}

	public void setTextMaintUNFM(Spinner textMaintUNFM) {
		this.textMaintUNFM = textMaintUNFM;
	}

	protected CocomoMaintainedPage() {
		super(NAME);
		this.setTitle("cocomo规模估算");
		this.setMessage("维护的代码");
	}

	@Override
	public void createControl(Composite parent) {
        Composite composite=new Composite(parent,SWT.NONE);
        GridLayout layout=new GridLayout(2,false);
		layout.marginLeft=20;
		composite.setLayout(layout);
		
		textAddSize = createDataField(composite, "新增代码量(LOC)：", 0, 0, Spinner.LIMIT, 0);
		textModifySize = createDataField(composite, "修改代码量(LOC)：", 0, 0, Spinner.LIMIT, 0);
		textMaintSU = createDataField(composite, "代码可理解度(范围10-50，10表示代码很好理解)：", 10, 10, 50, 0);
		textMaintUNFM = createDataField(composite, "开发人员不熟悉度(范围0-1，0表示完全熟悉)：", 0, 0, 10, 1);
	
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
	protected int getSize() {

		int estimatedSize = 0;
		SLOCWizard seWizard=(SLOCWizard)this.getWizard();
		
		// new code
		CocomoNewDevelopedPage new_Page=seWizard.getCocomoNewDevelopedPage();
		estimatedSize += new_Page.getSpnSLOC().getSelection();
		// reuse code
		CocomoReusedPage resused_Page=seWizard.getCocomoReusedPage();
		Double SLOC = Double.parseDouble(resused_Page.getTextReuseSize().getText());
		Double AT = Double.parseDouble(resused_Page.getTextReuseAT().getText());
		Double DM = Double.parseDouble(resused_Page.getTextReuseDM().getText());
		Double CM = Double.parseDouble(resused_Page.getTextReuseCM().getText());
		Double IM = Double.parseDouble(resused_Page.getTextReuseIM().getText());
		Double AA = Double.parseDouble(resused_Page.getTextReuseAA().getText());
		Double SU = Double.parseDouble(resused_Page.getTextReuseSU().getText());
		Double UNFM = Double.parseDouble(resused_Page.getTextReuseUNFM().getText());
		estimatedSize += COCOMO.getReuseSize(SLOC, AT, DM, CM, IM, AA, SU, UNFM);
		// maintain code
		Double addSLOC = Double.parseDouble(textAddSize.getText());
		Double modifySLOC = Double.parseDouble(textModifySize.getText());
		SU = Double.parseDouble(textMaintSU.getText());
		UNFM = Double.parseDouble(textMaintUNFM.getText());
		estimatedSize += COCOMO.getMaintainSize(addSLOC, modifySLOC, SU, UNFM);
		
		return estimatedSize;
	}

	@Override
	public IWizardPage getNextPage() {
		return null;
	}

	@Override
	public boolean canFlipToNextPage() {
		// TODO Auto-generated method stub
		return false;
	}

}