package newProject.quickEstimate;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.*;

//下步工作，将scale的值与两个text绑定
public class SizePage extends WizardPage {
	public static final String PAGE_NAME = "Size";
	private Button userInput, history;
	private Scale scaleHistory;
	private Text sizeText;
	private Label textLevel;
	private int sizeValue;
	private StackLayout inputStack;
	private Composite predefinedInput;
	private Composite dataArea;
	public SizePage() {
		super(PAGE_NAME, "快速估算: 系统规模", null);
		setDescription("请选择一种规模估算方式：");

	}

	public void createControl(Composite parent) {
		//此方法能启动canFlipToNextPage()方法,判断next按钮是否可用
		setPageComplete(false);

		Composite topLevel = new Composite(parent, SWT.NONE);
//		RowLayout topLayout = new RowLayout(SWT.VERTICAL);
//		topLayout.pack = false;
//		topLevel.setLayout(topLayout);
		
		//用户输入规模
		userInput = new Button(topLevel, SWT.RADIO);
		userInput.setText("用户自己输入代码行数");
		userInput.setBounds(10, 10, 200, 30);
		userInput.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				setPageComplete(true);
				inputStack.topControl = sizeText;
				dataArea.layout();
			}
		});
		//根据历史数据估算规模
		history = new Button(topLevel, SWT.RADIO);
		history.setText("根据数据库的历史数据得出");
		history.setBounds(10, 50, 200, 30);
		history.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				setPageComplete(true);
				inputStack.topControl = predefinedInput;
				dataArea.layout();
			}
		});

		dataArea = new Composite(topLevel, SWT.NONE);
		inputStack = new StackLayout();
		dataArea.setLayout(inputStack);
		dataArea.setBounds(30, 100, 400, 30);
		//用户手动输入规模
		sizeText = new Text(dataArea, SWT.BORDER);
		sizeText.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				setPageComplete(true);
			}
		});
		//通过拖动条选择规模
		predefinedInput = new Composite(dataArea, SWT.NONE);
		scaleHistory = new Scale(predefinedInput, SWT.NULL);
		scaleHistory.setMinimum(0);
		scaleHistory.setMaximum(1500000);
		scaleHistory.setIncrement(1000);
		scaleHistory.setPageIncrement(300000);
		scaleHistory.setSize(300, 30);
		scaleHistory.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				setPageComplete(true);
				sizeValue = scaleHistory.getSelection() + scaleHistory.getMinimum();
				textLevel.setText(getLevel(sizeValue) + ":" + sizeValue);
			}
		});
		scaleHistory.setSelection(100);
		textLevel = new Label(predefinedInput, SWT.NONE);
		textLevel.setBounds(305, 5,100, 30);
		
		setControl(topLevel);
	}
	
	
	//用户来设定next按钮能否使用
	public boolean canFlipToNextPage() {
		try{
			getSize();
		}catch(NumberFormatException e){
			return false;
		}
        return isPageComplete() && getNextPage() != null;
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
			return Integer.parseInt(sizeText.getText());
		else
			return sizeValue;
	}

}
