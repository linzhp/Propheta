package newProject;

import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
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
		Composite top = new Composite(parent, NONE);
		top.setLayout(new RowLayout(SWT.VERTICAL));
		
		Button quick = new Button(top, SWT.RADIO);
		quick.setText("快速估算");
		
		Button detailed = new Button(top, SWT.RADIO);
		detailed.setText("详细估算");
		
		setControl(top);
	}

}
