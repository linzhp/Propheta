package newProject;

import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class TaskSelectionPage extends WizardSelectionPage {
	public static final String NAME="选择任务";

	protected TaskSelectionPage() {
		super(NAME);
		setMessage("选择一项任务");
		setTitle("选择任务");
	}

	@Override
	public void createControl(Composite parent) {
		Button btn = new Button(parent, SWT.RADIO);
		btn.setText("快速估算");
		setControl(btn);
	}

}
