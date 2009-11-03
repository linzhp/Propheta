package newProject.quickEstimate;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

//下步工作，将scale的值与两个text绑定
public class SizePage extends WizardPage {
	public static final String PAGE_NAME = "Size";
	private Button userInput, history;
	private Scale scaleHistory;
	private Text textInput, textLevel,textHistory;
	private int sizeValue;
	public SizePage() {
		super(PAGE_NAME, "快速估算: 系统规模", null);

	}

	public void createControl(Composite parent) {
		setPageComplete(true);

		Composite topLevel = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		topLevel.setLayout(layout);

		Label label = new Label(topLevel, SWT.CENTER);
		label.setText("请选择一种规模估算方式：");
		final Label blank1 = new Label(topLevel,SWT.CENTER);
		
		//用户输入规模
		userInput = new Button(topLevel, SWT.RADIO);
		userInput.setText("用户自己输入代码行数：                      ");
		textInput = new Text(topLevel, SWT.BORDER);	
		textInput.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));//此处设置了文本框的align
		userInput.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				textInput.setEditable(true);
				scaleHistory.setEnabled(false);
			}
		});
		
		//根据历史数据估算规模
		history = new Button(topLevel, SWT.RADIO);
		history.setText("根据数据库的历史数据得出                       ");
		history.setSelection(true);
		textHistory = new Text(topLevel, SWT.BORDER);	
		textHistory.setEditable(false);
		textHistory.setVisible(false);
		textHistory.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		history.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				textInput.setEditable(false);
				scaleHistory.setEnabled(true);
			}
		});
		
		scaleHistory = new Scale(topLevel, SWT.NULL);
		scaleHistory.setMinimum(0);
		scaleHistory.setMaximum(1500000);
		scaleHistory.setIncrement(1000);
		scaleHistory.setPageIncrement(300000);
		scaleHistory.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				sizeValue = scaleHistory.getSelection() + scaleHistory.getMinimum();
				textLevel.setText(getLevel(sizeValue) + ":" + sizeValue);
			}
		});
		textLevel = new Text(topLevel, SWT.BORDER);
		//textLevel.setTabs(6);
		textLevel.setEditable(false);
		textLevel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		scaleHistory.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		
		setControl(topLevel);
		setPageComplete(true);
	}
	
	//由规模大小定义其等级，根据数据库的实际情况来,需修改数据
	public String getLevel(int value){
		String level;
		if(value<10000)
			level = "很小";
		else if(value>=10000 && value < 100000)
			level = "较小";
		else if(value>=10000 && value < 250000)
			level = "中等";
		else if(value>=250000 && value < 1000000)
			level = "较大";
		else
			level = "很大";
		return level;
	}
	
	public int getSize() {
		if (userInput.getSelection())
			return Integer.parseInt(textInput.getText());
		else
			return sizeValue;
	}

}
