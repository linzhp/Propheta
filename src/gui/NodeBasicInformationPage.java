package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

/**
 * 节点基本信息页面
 * @author Administrator
 *
 */
public class NodeBasicInformationPage extends ParameterArea{

	//页面控件
	private Label labelNodeName, labelTeamSize,labelDuration,labelSLOC,labelFP,labelBusinessArea,
	              labelDevelopType,labelDevelopPlatform,labelDevelopTechnique,labelLanguageType,labelLanguage;
	private Text texNodeName;
	private Spinner spnTeamSize,spnDuration,spnSLOC,spnFP;
	private Combo cmbBusinessArea,cmbDevelopType,cmbDevelopPlatform,cmbDevelopTechnique,cmbLanguageType,cmbLanguage;
	
	
	/**
	 * 构造器
	 * @param nodeID 关联的节点ID
	 */
	public NodeBasicInformationPage(int nodeID, Composite parent){
		super(parent,nodeID);
		this.setFormText("节点基本信息");
		
		createContents(this.form.getBody());
	}
	
	
	/**
	 * 初始化界面控件
	 * @param parent
	 */
	private void createContents(Composite parent){
		parent.setLayout(new GridLayout(4,false));
		
		labelNodeName=new Label(parent,SWT.NONE);
		labelNodeName.setText("节点名称:");
		
		texNodeName=new Text(parent,SWT.BORDER);
		texNodeName.setEnabled(false);
		GridData gd=new GridData();
		gd.horizontalSpan=3;
		texNodeName.setLayoutData(gd);
		texNodeName.setText("unknown");
		
		labelTeamSize=new Label(parent,SWT.NONE);
		labelTeamSize.setText("团队规模:");
		
		spnTeamSize=new Spinner(parent,SWT.BORDER);
		spnTeamSize.setMaximum(Spinner.LIMIT);
		spnTeamSize.setSelection(5);
		
		labelDuration=new Label(parent,SWT.NONE);
		labelDuration.setText("项目周期:");
		
		spnDuration=new Spinner(parent,SWT.BORDER);
		spnDuration.setMaximum(Spinner.LIMIT);
		spnDuration.setSelection(180);
		
		labelSLOC=new Label(parent,SWT.NONE);
		labelSLOC.setText("代码行数:");
		
		spnSLOC=new Spinner(parent,SWT.BORDER);
		spnSLOC.setMaximum(Spinner.LIMIT);
		spnSLOC.setSelection(1000);
		
		labelFP=new Label(parent,SWT.NONE);
		labelFP.setText("功能点数目:");
		
		spnFP=new Spinner(parent,SWT.BORDER);
		spnFP.setMaximum(Spinner.LIMIT);
		spnFP.setSelection(180);
		
		labelBusinessArea=new Label(parent,SWT.NONE);
		labelBusinessArea.setText("业务领域:");
		
		cmbBusinessArea=new Combo(parent, SWT.READ_ONLY);
		String[] texts = new String[]{ "电信", "金融", "流通", "保险", "交通", "媒体", "卫生", "制造",
				"政府", "能源" };
		String[] values =new String[]{ "Telecom", "Finance", "Retail", "General",
				"Transport", "Media", "HealthCare", "Manufacturing",
				"PublicAdmin", "Energy" };
		cmbBusinessArea.setItems(texts);
		for (int i = 0; i < texts.length; i++) {
			cmbBusinessArea.setData(texts[i], values[i]);
		}
		cmbBusinessArea.select(0);
		
		
		labelDevelopType=new Label(parent,SWT.NONE);
		labelDevelopType.setText("开发类型:");
		
		cmbDevelopType=new Combo(parent, SWT.READ_ONLY);
		texts = new String[]{"新开发", "二次开发", "优化", "其它" };
		values =new String[]{"NewDevelopment", "ReDevelopment", "Enhancement", "Other"};
		cmbDevelopType.setItems(texts);
		for (int i = 0; i < texts.length; i++) {
			cmbDevelopType.setData(texts[i], values[i]);
		}
		cmbDevelopType.select(0);
		
		labelDevelopPlatform=new Label(parent,SWT.NONE);
		labelDevelopPlatform.setText("开发平台:");
		
		cmbDevelopPlatform=new Combo(parent, SWT.READ_ONLY);
		texts = new String[]{"大型机", "中型机", "个人计算机", "混合"};
		values =new String[]{"MF", "MR", "PC", "Multi"};
		cmbDevelopPlatform.setItems(texts);
		for (int i = 0; i < texts.length; i++) {
			cmbDevelopPlatform.setData(texts[i], values[i]);
		}
		cmbDevelopPlatform.select(0);
		
		labelDevelopTechnique=new Label(parent,SWT.NONE);
		labelDevelopTechnique.setText("开发技术:");
		
		cmbDevelopTechnique=new Combo(parent, SWT.READ_ONLY);
		texts = new String[]{"面向对象分析设计", "事件建模", "业务领域建模", "回归测试", "面向对象与事件建模",
				"回归测试与业务领域建模", "其它"};
		values =new String[]{"Object Oriented Analysis;Object Oriented Design",
				"Event Modelling",
				"Business Area Modelling",
				"Regression Testing",
				"Object Oriented Analysis;Object Oriented Design;Event Modelling",
				"Regression Testing;Business Area Modelling",
				"Other"};
		cmbDevelopTechnique.setItems(texts);
		for (int i = 0; i < texts.length; i++) {
			cmbDevelopTechnique.setData(texts[i], values[i]);
		}
		cmbDevelopTechnique.select(0);
		
		labelLanguageType=new Label(parent,SWT.NONE);
		labelLanguageType.setText("语言类型:");
		
		cmbLanguageType=new Combo(parent, SWT.READ_ONLY);
		texts = new String[]{"第二代语言", "第三代语言", "第四代语言", "应用代"};
		values =new String[]{"2GL", "3GL", "4GL", "ApG"};
		cmbLanguageType.setItems(texts);
		for (int i = 0; i < texts.length; i++) {
			cmbLanguageType.setData(texts[i], values[i]);
		}
		cmbLanguageType.select(0);
		
		labelLanguage=new Label(parent,SWT.NONE);
		labelLanguage.setText("开发语言:");
		
		cmbLanguage=new Combo(parent, SWT.READ_ONLY);
		texts = new String[]{"ASP", "C#", "VB", "JAVA", "C++", "C", "COBOL"};
		values =new String[]{"ASP", "C#", "VB", "Java", "C++", "C", "Cobol"};
		cmbLanguage.setItems(texts);
		for (int i = 0; i < texts.length; i++) {
			cmbLanguage.setData(texts[i], values[i]);
		}
		cmbLanguage.select(0);
	}
}
