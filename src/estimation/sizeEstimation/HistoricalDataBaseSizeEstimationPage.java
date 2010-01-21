package estimation.sizeEstimation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import gui.widgets.ParameterScale;

public class HistoricalDataBaseSizeEstimationPage extends BaseWizardPage{

	private Label labelSLOC;
	private Text textSLOC;
	private ParameterScale SLOCScale;
	
	protected HistoricalDataBaseSizeEstimationPage(String pageName) {
		super(pageName);
		this.setTitle("参考历史数据");
		this.setMessage("根据历史数据选择代码规模");
	}

	@Override
	public void createControl(Composite parent) {
        Composite composite=new Composite(parent,SWT.NONE);
		GridLayout layout=new GridLayout(2,false);
		layout.marginLeft=20;
		composite.setLayout(layout);
		
		labelSLOC=new Label(composite,SWT.NONE);
		labelSLOC.setText("代码规模（行）:");
		textSLOC=new Text(composite,SWT.BORDER);
		textSLOC.setEditable(false);
		textSLOC.setText("1000");
		
		String[] levels = {"XL","VL","L","N","H","VH","XH"};
		SLOCScale= new ParameterScale(composite, levels, 0);
		GridData gd=new GridData();
		gd.horizontalSpan=2;
		gd.horizontalAlignment=SWT.FILL;
		SLOCScale.setLayoutData(gd);
		
		SLOCScale.addListener(new Listener() {
			public void handleEvent(Event event) {
				int sizeValue = SLOCScale.getIndex();
				textSLOC.setText(String.valueOf((int)Math.pow(4, sizeValue)*1000));
			}
		});
		
		this.setControl(composite);
	}

	@Override
	protected int getSize() {
		return Integer.parseInt(this.textSLOC.getText());
	}

	@Override
	protected boolean isEndPage() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean canFlipToNextPage() {
		// TODO Auto-generated method stub
		return false;
	}

}
