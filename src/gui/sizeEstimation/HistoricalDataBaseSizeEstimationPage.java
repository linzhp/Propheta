package gui.sizeEstimation;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class HistoricalDataBaseSizeEstimationPage extends WizardPage{

	protected HistoricalDataBaseSizeEstimationPage(String pageName) {
		super(pageName);
		this.setTitle("参考历史数据");
		this.setMessage("根据历史数据选择代码规模");
	}

	@Override
	public void createControl(Composite parent) {
        Composite composite=new Composite(parent,SWT.NONE);
		
		this.setControl(composite);
	}

}
