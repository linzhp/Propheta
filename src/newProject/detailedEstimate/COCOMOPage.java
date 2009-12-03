package newProject.detailedEstimate;

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
		ExpandBar bar = new ExpandBar(topLevel, SWT.V_SCROLL);
		GridData gdBar = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gdBar.horizontalSpan=2;//水平跨度
		bar.setLayoutData(gdBar);
        
		String[] labels={"VL","L","N","H","VH","XH"};
		String[] descriptions={"很低","低","中等","高","很高","极高"};
		
		// First item
		Composite composite = new Composite (bar, SWT.NONE);
		GridLayout interLayout = new GridLayout(2, false);
		interLayout.horizontalSpacing = 30;
		composite.setLayout(interLayout);
		
		Label label= new Label(composite,SWT.NONE);
		label.setText("先例性：");
		label = new Label(composite,SWT.NONE);
		label.setText("开发灵活性：");
		scalesSF[0] = new ParameterScale(composite,labels,descriptions,2);
		scalesSF[0].setName("PREC");
		scalesSF[1] = new ParameterScale(composite,labels,descriptions,2);
		scalesSF[1].setName("FLEX");
		label = new Label(composite,SWT.NONE);
		label.setText("团队合作能力：");
		label = new Label(composite,SWT.NONE);
		label.setText("过程成熟度：");
		scalesSF[2] = new ParameterScale(composite,labels,descriptions,2);
		scalesSF[2].setName("TEAM");
		scalesSF[3] = new ParameterScale(composite,labels,descriptions,2);
		scalesSF[3].setName("PMAT");
		label = new Label(composite,SWT.NONE);
		label.setText("体系结构风险化解：");
		label = new Label(composite,SWT.NONE);
		scalesSF[4] = new ParameterScale(composite,labels,descriptions,2);
		scalesSF[4].setName("RESL");
		
		ExpandItem item0 = new ExpandItem (bar, SWT.NONE, 0);
		//比例因子后面加n个字符串，用来控制bar的宽度
		item0.setText("比例因子                                           ");
		item0.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		item0.setControl(composite);
		
		// Second item
		composite = new Composite (bar, SWT.NONE);
		interLayout = new GridLayout (2, false);
		interLayout.horizontalSpacing = 30;
		composite.setLayout(interLayout);	
		
		label= new Label(composite,SWT.NONE);
		label.setText("软件可靠性：");
		label = new Label(composite,SWT.NONE);
		label.setText("数据库规模：");
		scalesEM[0] = new ParameterScale(composite,labels,descriptions,2);
		scalesEM[0].setName("RELY");
		scalesEM[1] = new ParameterScale(composite,labels,descriptions,2);
		scalesEM[1].setName("DATA");
		label = new Label(composite,SWT.NONE);
		label.setText("过程文档：");
		label = new Label(composite,SWT.NONE);
		label.setText("产品复杂度：");
		scalesEM[2] = new ParameterScale(composite,labels,descriptions,2);
		scalesEM[2].setName("DOCU");
		scalesEM[3] = new ParameterScale(composite,labels,descriptions,2);
		scalesEM[3].setName("CPLX");
		label = new Label(composite,SWT.NONE);
		label.setText("可复用开发：");
		label = new Label(composite,SWT.NONE);
		scalesEM[4] = new ParameterScale(composite,labels,descriptions,2);
		scalesEM[4].setName("RUSE");
		
		ExpandItem item1 = new ExpandItem (bar, SWT.NONE, 1);
		item1.setText("工作量因子：平台");
		item1.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		item1.setControl(composite);
		
		// Third item,nullpoint
		composite = new Composite (bar, SWT.NONE);
		interLayout = new GridLayout (2, false);
		interLayout.horizontalSpacing = 30;
		composite.setLayout(interLayout);	
		
		label= new Label(composite,SWT.NONE);
		label.setText("执行时间约束：");
		label = new Label(composite,SWT.NONE);
		label.setText("主存储约束：");
		scalesEM[5] = new ParameterScale(composite,labels,descriptions,2);
		scalesEM[5].setName("TIME");
		//System.out.println(scales[10].getName());
		scalesEM[6] = new ParameterScale(composite,labels,descriptions,2);
		scalesEM[6].setName("STOR");
		label = new Label(composite,SWT.NONE);
		label.setText("平台易变性：");
		label = new Label(composite,SWT.NONE);
		scalesEM[7] = new ParameterScale(composite,labels,descriptions,2);
		scalesEM[7].setName("PVOL");
		
		ExpandItem item2 = new ExpandItem (bar, SWT.NONE, 2);
		item2.setText("工作量因子：产品");
		item2.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		item2.setControl(composite);

		// Fourth item
		composite = new Composite (bar, SWT.NONE);
		interLayout = new GridLayout (2, false);
		interLayout.horizontalSpacing = 30;
		composite.setLayout(interLayout);	
		
		label= new Label(composite,SWT.NONE);
		label.setText("分析员能力：");
		label = new Label(composite,SWT.NONE);
		label.setText("程序员能力：");
		scalesEM[8] = new ParameterScale(composite,labels,descriptions,2);
		scalesEM[8].setName("ACAP");
		scalesEM[9] = new ParameterScale(composite,labels,descriptions,2);
		scalesEM[9].setName("PCAP");
		label = new Label(composite,SWT.NONE);
		label.setText("人员连续性：");
		label = new Label(composite,SWT.NONE);
		label.setText("应用类型经验：");
		scalesEM[10] = new ParameterScale(composite,labels,descriptions,2);
		scalesEM[10].setName("PCON");
		scalesEM[11] = new ParameterScale(composite,labels,descriptions,2);
		scalesEM[11].setName("APEX");
		label = new Label(composite,SWT.NONE);
		label.setText("语言与工具经验：");
		label = new Label(composite,SWT.NONE);
		label.setText("平台经验：");
		scalesEM[12] = new ParameterScale(composite,labels,descriptions,2);
		scalesEM[12].setName("LTEX");
		scalesEM[13] = new ParameterScale(composite,labels,descriptions,2);
		scalesEM[13].setName("PLEX");
		
		ExpandItem item3 = new ExpandItem (bar, SWT.NONE, 3);
		item3.setText("工作量因子：人员");
		item3.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		item3.setControl(composite);
		
		// Fifth item
		composite = new Composite (bar, SWT.NONE);
		interLayout = new GridLayout (2, false);
		interLayout.horizontalSpacing = 30;
		composite.setLayout(interLayout);	
		
		label= new Label(composite,SWT.NONE);
		label.setText("工具的先进性：");
		label = new Label(composite,SWT.NONE);
		label.setText("多点开发：");
		scalesEM[14] = new ParameterScale(composite,labels,descriptions,2);
		scalesEM[14].setName("TOOL");
		scalesEM[15] = new ParameterScale(composite,labels,descriptions,2);
		scalesEM[15].setName("SITE");
		label = new Label(composite,SWT.NONE);
		label.setText("进度要求：");
		label = new Label(composite,SWT.NONE);
		scalesEM[16] = new ParameterScale(composite,labels,descriptions,2);
		scalesEM[16].setName("SCED");
		
		ExpandItem item4 = new ExpandItem (bar, SWT.NONE, 4);
		item4.setText("工作量因子：项目");
		item4.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		item4.setControl(composite);
		
		item0.setExpanded(true);
		
		setControl(topLevel);
		setPageComplete(true);
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
