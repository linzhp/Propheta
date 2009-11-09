package newProject.PIEstimate;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class FactorValuePage extends WizardPage {
	public static final String PAGE_NAME = "FactorValue";
	private Button button1, button2, button3;

	public FactorValuePage() {
		super(PAGE_NAME, "生产率快速估算: 因子取值", null);

	}

	public void createControl(Composite parent) {
		setDescription("您所选取的因子有以下几种取值，请选取符合您项目情况的选项: ");

		Composite topLevel = new Composite(parent, SWT.NONE);
		topLevel.setLayout(new GridLayout(1, false));

		button1 = new Button(topLevel, SWT.RADIO);
		button1.setText("新开发");
		button1.setSelection(true);

		button2 = new Button(topLevel, SWT.RADIO);
		button2.setText("二次开发");

		button3 = new Button(topLevel, SWT.RADIO);
		button3.setText("优化");

		setControl(topLevel);
		setPageComplete(true);
	}

}
