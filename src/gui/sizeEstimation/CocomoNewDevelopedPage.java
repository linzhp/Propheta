package gui.sizeEstimation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

public class CocomoNewDevelopedPage extends BaseWizardPage{

	private Label labelSLOC;
	private Spinner spnSLOC;
	
		
	public Spinner getSpnSLOC() {
		return spnSLOC;
	}

	protected CocomoNewDevelopedPage(String pageName) {
		super(pageName);
		this.setTitle("cocomo规模估算");
		this.setMessage("新开发的代码");
	}

	@Override
	public void createControl(Composite parent) {
        Composite composite=new Composite(parent,SWT.NONE);
        GridLayout layout=new GridLayout(2,false);
		layout.marginLeft=20;
		composite.setLayout(layout);
		
		labelSLOC=new Label(composite,SWT.NONE);
		labelSLOC.setText("新开发代码量（行）:");
		spnSLOC=new Spinner(composite,SWT.BORDER);
		spnSLOC.setMaximum(Spinner.LIMIT);
		spnSLOC.setSelection(0);
		
		this.setControl(composite);
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