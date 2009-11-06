package newProject.quickEstimate;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

//下步工作，将scale的值与两个text绑定
public class PIPage extends WizardPage {
	public static final String PAGE_NAME = "PI";
	private double piE=16.967, piD=229.3141;
	public PIPage() {
		super(PAGE_NAME, "快速估算: 生产率", null);

	}

	public void createControl(Composite parent) {
		setPageComplete(false);

		Composite topLevel = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		topLevel.setLayout(layout);

		Label label = new Label(topLevel, SWT.CENTER);
		label.setText("请选择一种生产率估算方式：");
		
		//根据CSBSG历史数据估算规模
		Button CSBSG = new Button(topLevel, SWT.RADIO);
		CSBSG.setText("根据CSBSG数据库的历史数据得出                ");
		CSBSG.setSelection(true);
		CSBSG.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				piE = 16.2179;
				piD = 229.3141;
				
			}
		});
		
		//根据ISBSG历史数据估算规模
		Button ISBSG = new Button(topLevel, SWT.RADIO);
		ISBSG.setText("根据ISBSG数据库的历史数据得出                 ");
		ISBSG.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				piE = 16.967;
				piD = 547.653;
			}
		});
		
		setControl(topLevel);
		setPageComplete(true);
	}
	
	public double getPIE()
	{
		return piE;
	}
	
	public double getPID()
	{
		return piD;
	}
}
