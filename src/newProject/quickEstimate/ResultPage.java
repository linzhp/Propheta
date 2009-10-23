package newProject.quickEstimate;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class ResultPage extends WizardPage {
	public static final String PAGE_NAME = "Result";
	private Label textLabel;

	public ResultPage() {
		super(PAGE_NAME, "生产率快速估算: 估算结果", null);
	}

	public void createControl(Composite parent) {
		Composite topLevel = new Composite(parent, SWT.NONE);
		topLevel.setLayout(new FillLayout());
		textLabel = new Label(topLevel, SWT.CENTER);
		textLabel.setText("您项目的生产率为: 5.23 Loc/Hour");
		setControl(topLevel);
		setPageComplete(true);
	}

	public void updateText(String newText) {
		textLabel.setText(newText);
	}

}
