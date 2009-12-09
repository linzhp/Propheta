package estimation.detailedEstimate;

import java.util.HashMap;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class COCOMOPage extends WizardPage {
	public static final String PAGE_NAME = "COCOMO";
	private HashMap<String, String> factorsSF = new HashMap<String, String>();
	private HashMap<String, String> factorsEM = new HashMap<String, String>();
	private ParameterScale[] scalesSF = new ParameterScale[5];
	private ParameterScale[] scalesEM = new ParameterScale[17];
	private Text textSize;
	private Composite composite;
	private ExpandBar bar;
	private GridLayout interLayout;
	private Label label;
	private final String[] labels={"VL","L","N","H","VH","XH"};
	private final String[] descriptions={"很低","低","中等","高","很高","极高"};

	public COCOMOPage() {
		super(PAGE_NAME, "COCOMO 工作量估算", null);

	}

	public void createControl(Composite parent) {
		setDescription("请输入估算所需的信息: ");

		Composite topLevel = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2,false);
		topLevel.setLayout(layout);
		
		Label labelSize = new Label(topLevel,SWT.NONE);
		labelSize.setText("用户输入代码行数：       ");
		textSize = new Text(topLevel, SWT.BORDER);
		textSize.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(true);
			}
		});
		
		bar = new ExpandBar(topLevel, SWT.V_SCROLL);
		GridData gdBar = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gdBar.horizontalSpan=2;//水平跨度
		bar.setLayoutData(gdBar);
        
		// First item
		initialComposite();
		initialLabel("先例性：");
		initialLabel("开发灵活性：");
		initialScaleSF(0, "PREC");
		initialScaleSF(1, "FLEX");
		initialLabel("团队合作能力：");
		initialLabel("过程成熟度：");
		initialScaleSF(2, "TEAM");
		initialScaleSF(3, "PMAT");
		initialLabel("体系结构风险化解：");
		label = new Label(composite,SWT.NONE);
		initialScaleSF(4, "RESL");
		ExpandItem item0 = new ExpandItem (bar, SWT.NONE, 0);
		//比例因子后面加n个字符串，用来控制bar的宽度
		configBarItem(item0, "比例因子                                           ");
		
		// Second item
		initialComposite();
		initialLabel("软件可靠性：");
		initialLabel("数据库规模：");
		initialScaleEM(0, "RELY");
		initialScaleEM(1, "DATA");
		initialLabel("过程文档：");
		initialLabel("产品复杂度：");
		initialScaleEM(2, "DOCU");
		initialScaleEM(3, "CPLX");
		initialLabel("可复用开发：");
		label = new Label(composite,SWT.NONE);
		initialScaleEM(4, "RUSE");
		ExpandItem item1 = new ExpandItem (bar, SWT.NONE, 1);
		configBarItem(item1, "工作量因子：平台");
		
		// Third item
		initialComposite();
		initialLabel("执行时间约束：");
		initialLabel("主存储约束：");
		initialScaleEM(5, "TIME");
		initialScaleEM(6, "STOR");
		initialLabel("平台易变性：");
		label = new Label(composite,SWT.NONE);
		initialScaleEM(7, "PVOL");
		
		ExpandItem item2 = new ExpandItem (bar, SWT.NONE, 2);
		configBarItem(item2, "工作量因子：产品");

		// Fourth item
		initialComposite();
		initialLabel("分析员能力：");
		initialLabel("程序员能力：");
		initialScaleEM(8, "ACAP");
		initialScaleEM(9, "PCAP");
		initialLabel("人员连续性：");
		initialLabel("应用类型经验：");
		initialScaleEM(10, "PCON");
		initialScaleEM(11, "APEX");
		initialLabel("语言与工具经验：");
		initialLabel("平台经验：");
		initialScaleEM(12, "LTEX");
		initialScaleEM(13, "PLEX");
		ExpandItem item3 = new ExpandItem (bar, SWT.NONE, 3);
		configBarItem(item3, "工作量因子：人员");
		
		// Fifth item
		initialComposite();
		initialLabel("工具的先进性：");
		initialLabel("多点开发：");
		initialScaleEM(14, "TOOL");
		initialScaleEM(15, "SITE");
		initialLabel("进度要求：");
		label = new Label(composite,SWT.NONE);
		initialScaleEM(16, "SCED");
		ExpandItem item4 = new ExpandItem (bar, SWT.NONE, 4);
		configBarItem(item4, "工作量因子：项目");
		
		item0.setExpanded(true);
		
		setControl(topLevel);
		setPageComplete(true);
	}
	//初始化composite
	private void initialComposite()
	{
		composite = new Composite (bar, SWT.NONE);
		interLayout = new GridLayout (2, false);
		interLayout.horizontalSpacing = 30;
		composite.setLayout(interLayout);	
	}
	//设置bar的item
	private void configBarItem(ExpandItem item, String desc)
	{
		item.setText(desc);
		item.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		item.setControl(composite);
	}
	//设置标签
	private void initialLabel(String text)
	{
		label = new Label(composite,SWT.NONE);
		label.setText(text);
	}
	//设置SF因子的scale控件
	private void initialScaleSF(int index, String name)
	{
		scalesSF[index] = new ParameterScale(composite,labels,descriptions,2);
		scalesSF[index].setName(name);
	}
	//设置EM因子的scale控件
	private void initialScaleEM(int index, String name)
	{
		scalesEM[index] = new ParameterScale(composite,labels,descriptions,2);
		scalesEM[index].setName(name);
	}
	
	public HashMap<String, String> getFactorsSF() {
		for(ParameterScale scale: scalesSF)
		{
			factorsSF.put(scale.getName(), scale.getLevel());
			
		}
		return factorsSF;
	}
	
	public HashMap<String, String> getFactorsEM() {
		for(ParameterScale scale: scalesEM)
		{
			factorsEM.put(scale.getName(), scale.getLevel());
		}
		return factorsEM;
	}
	public boolean canFinish() {
		try {
			getSize();
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public int getSize()
	{
		return Integer.parseInt(textSize.getText());
	}

}
