package estimation.sizeEstimation;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class SizeEstimationTypePage extends BaseWizardPage {

	private Button historicalSLOCButton, cocomoSLOCButton;
	public final static int HISTORY = 2;
	public final static int COCOMO = 3;
	public static final String NAME="请选择代码规模估算方式";

	private int estimationType = COCOMO;

	public void setEstimationType(int estimationType) {
		this.estimationType = estimationType;
	}

	protected SizeEstimationTypePage() {
		super(NAME);
		this.setTitle("估算方式");
		this.setMessage("请选择代码规模估算方式");
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout gd = new GridLayout(1, false);
		gd.marginLeft = 20;
		composite.setLayout(gd);

		historicalSLOCButton = new Button(composite, SWT.RADIO);
		historicalSLOCButton.setText("参考历史数据");
		historicalSLOCButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				setEstimationType(HISTORY);
			}
		});

		cocomoSLOCButton = new Button(composite, SWT.RADIO);
		cocomoSLOCButton.setText("cocomo规模估算");
		cocomoSLOCButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				setEstimationType(COCOMO);
			}
		});

		this.setControl(composite);
	}

	public IWizardPage getNextPage() {
		SizeEstimationWizard seWizard = (SizeEstimationWizard) this.getWizard();
		switch (this.estimationType) {
		case HISTORY:
			return seWizard.getHistoricalDataBaseSizeEstimationPage();
		case COCOMO:
			return seWizard.getCocomoNewDevelopedPage();
		default:
			return null;
		}
	}

	@Override
	protected int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

}
