package estimation;

import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import estimation.detailedEstimate.DetailedEstimateNode;
import estimation.quickEstimate.QuickEstimateNode;
import estimation.sizeEstimate.SizeEstimateNode;


public class TaskSelectionPage extends WizardSelectionPage {
	public static final String NAME = "选择任务";

	protected TaskSelectionPage() {
		super(NAME);
		setMessage("选择一项任务");
		setTitle("选择任务");
	}

	@Override
	public void createControl(Composite parent) {
		setPageComplete(false);
		
		Composite top = new Composite(parent, NONE);
		top.setLayout(new RowLayout(SWT.VERTICAL));

		
		Button quick = new Button(top, SWT.RADIO);
		quick.setText("快速估算");
		quick.addSelectionListener(new SelectionListener() {

			// 以下内容为new SelectionListener()实现
			// 默认选择该项与选择该项（widgetSelected）的操作是一样的
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				setSelectedNode(new QuickEstimateNode());
			}
		});

		Button detailed = new Button(top, SWT.RADIO);
		detailed.setText("详细估算");
		detailed.addSelectionListener(new SelectionListener() {
			// 默认选择该项与选择该项（widgetSelected）的操作是一样的
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				setSelectedNode(new DetailedEstimateNode());
			}
		});
		
		Button size = new Button(top, SWT.RADIO);
		size.setText("规模估算");
		size.addSelectionListener(new SelectionListener() {

			// 以下内容为new SelectionListener()实现
			// 默认选择该项与选择该项（widgetSelected）的操作是一样的
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				setSelectedNode(new SizeEstimateNode());
			}
		});
		
		setControl(top);
		setPageComplete(true);
	}

}