package estimation.sizeEstimation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class FunctionPointPage extends BaseWizardPage {
	public static final String NAME="功能点估算";
	public FunctionPointPage(){
		super(NAME);
		setTitle(NAME);
	}

	@Override
	protected int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void createControl(Composite parent) {
		parent.setLayout(new GridLayout(4, false));
		Label label = new Label(parent, SWT.NONE);
		label.setText("External Files");
		
	}

}
