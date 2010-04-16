package estimation.sizeEstimation;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

public class ManualSizeEstimationPage extends BaseWizardPage{

	private Label labelSLOC;
	private Spinner spnSLOC;
	public static final String NAME="请直接输入代码规模";
	
	protected ManualSizeEstimationPage() {
		super(NAME);
		this.setTitle("用户输入");
		this.setMessage("请直接输入代码规模");
	}

	@Override
	public void createControl(Composite parent) {
        Composite composite=new Composite(parent,SWT.NONE);
        GridLayout gd=new GridLayout(2,false);
		gd.marginLeft=20;
		composite.setLayout(gd);
		
		labelSLOC=new Label(composite,SWT.NONE);
		labelSLOC.setText("代码规模（行）:");
		spnSLOC=new Spinner(composite,SWT.BORDER);
		spnSLOC.setMaximum(Spinner.LIMIT);
		spnSLOC.setSelection(1000);
		
		this.setControl(composite);
	}

	@Override
	public IWizardPage getNextPage() {
		return null;
	}

	@Override
	protected int getSize() {
		// TODO Auto-generated method stub
		return this.spnSLOC.getSelection();
	}

	@Override
	public boolean canFlipToNextPage() {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
