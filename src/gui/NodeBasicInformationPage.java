package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import dataManager.dataAccess.NodeBasicInfoAccess;
import dataManager.dataEntities.NodeBasicInformation;
import estimation.ParameterScale;

/**
 * 节点基本信息页面
 * @author Administrator
 *
 */
public class NodeBasicInformationPage extends ParameterArea{

	//页面控件
	private Label labelNodeName, labelTeamSize,labelDuration,labelSLOC,labelFP,labelBusinessArea,
	              labelDevelopType,labelDevelopPlatform,labelDevelopTechnique,labelLanguageType,labelLanguage;
	private Text texNodeName, textSLOC;
	private Spinner spnTeamSize,spnDuration,spnSLOC,spnFP;
	private Combo cmbBusinessArea,cmbDevelopType,cmbDevelopPlatform,cmbDevelopTechnique,cmbLanguageType,cmbLanguage;
	private Composite SLOCComposite, buttonComposite;
	private Button setSLOCButton,manualSLOCButton,historicalSLOCButton,saveButton;
	private ParameterScale SLOCScale;
	
	//变量
	private boolean isNodeBasicInformationChanged=false;  //节点信息是否被重新设置，如被重新设置，则提醒用户保存节点信息
	
	public void setIsNodeBasicInformationChanged(boolean isChanged){
		this.isNodeBasicInformationChanged=isChanged;
	}
	public boolean getIsNodeBasicInformationChanged(){
		return this.isNodeBasicInformationChanged;
	}
	
	
	/**
	 * 构造器
	 * @param nodeID 关联的节点ID
	 */
	public NodeBasicInformationPage(int nodeID, Composite parent){
		super(parent,nodeID);
		this.setFormText("节点基本信息");
		
		//构造页面控件
		createContents(this.form.getBody());
		
		//读取存储的节点信息并显示
		NodeBasicInformation nbi=new NodeBasicInformation();
		NodeBasicInfoAccess nia_access=new NodeBasicInfoAccess();
		nia_access.initConnection();
		nbi=nia_access.getNodeByID(nodeID);
		nia_access.disposeConnection();
		
		bindNodeBaiscInformation(nbi);
	}
	
	
	
	/**
	 * 初始化界面控件
	 * @param parent
	 */
	private void createContents(Composite parent){
		GridLayout layout=new GridLayout(4,false);
		parent.setLayout(layout);
		
		//节点名称
		labelNodeName=toolkit.createLabel(parent, "节点名称:", SWT.NONE);		
		texNodeName=toolkit.createText(parent, "", SWT.BORDER);
		texNodeName.setEditable(false);
		GridData gd=new GridData();
		gd.horizontalSpan=3;
		texNodeName.setLayoutData(gd);
		
		//团队规模
		labelTeamSize=toolkit.createLabel(parent, "团队规模:", SWT.NONE);	
		spnTeamSize=createSpinner(parent,Spinner.LIMIT,5);
			
		
		//项目周期
		labelDuration=toolkit.createLabel(parent, "项目周期:", SWT.NONE);		
		spnDuration=createSpinner(parent,Spinner.LIMIT,180);
		
		//代码行数
		labelSLOC=toolkit.createLabel(parent, "代码行数:", SWT.NONE);		
		
		
		
		//代码行输入面板
		SLOCComposite=toolkit.createComposite(parent,SWT.NONE);
		gd=new GridData();
		gd.horizontalSpan=1;
		gd.horizontalAlignment=SWT.FILL;
		SLOCComposite.setLayoutData(gd);
		SLOCComposite.setLayout(new GridLayout(2,false));
		
		textSLOC=toolkit.createText(SLOCComposite, "1000",SWT.BORDER);
		textSLOC.setEditable(false);
		gd=new GridData();
		gd.horizontalAlignment=SWT.FILL;
		textSLOC.setLayoutData(gd);
		
		setSLOCButton=toolkit.createButton(SLOCComposite, "设置", SWT.NONE);
		setSLOCButton.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				setIsNodeBasicInformationChanged(true);
				saveButton.setEnabled(true);
				
				//设置SLOC wizard
				
			}});
		
		
		/*
		manualSLOCButton=toolkit.createButton(SLOCComposite, "用户输入", SWT.RADIO);
		manualSLOCButton.setSelection(true);
		manualSLOCButton.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {				
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				spnSLOC.setEnabled(true);
				textSLOC.setEnabled(false);
				SLOCScale.setEnabled(false);
			}			
		});
		
		spnSLOC=createSpinner(SLOCComposite,Spinner.LIMIT,1000);
		
		historicalSLOCButton=toolkit.createButton(SLOCComposite, "参考历史数据", SWT.RADIO);
		historicalSLOCButton.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {				
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				spnSLOC.setEnabled(false);
				textSLOC.setEnabled(true);
				SLOCScale.setEnabled(true);
			}			
		});
		
		textSLOC=toolkit.createText(SLOCComposite, "1000",SWT.BORDER);
		textSLOC.setEditable(false);
		textSLOC.setEnabled(false);
		gd=new GridData();
		gd.horizontalAlignment=SWT.FILL;
		textSLOC.setLayoutData(gd);
				
		String[] levels = {"XL","VL","L","N","H","VH","XH"};
		SLOCScale= new ParameterScale(SLOCComposite, levels, 0);
		SLOCScale.setEnabled(false);
		toolkit.adapt(SLOCScale);
		gd=new GridData();
		gd.horizontalSpan=2;
		gd.horizontalAlignment=SWT.FILL;
		SLOCScale.setLayoutData(gd);
		
		SLOCScale.addListener(new Listener() {
			public void handleEvent(Event event) {
				int sizeValue = SLOCScale.getIndex();
				textSLOC.setText(String.valueOf((int)Math.pow(4, sizeValue)*1000));
				
				setIsNodeBasicInformationChanged(true);
				saveButton.setEnabled(true);
			}
		});
		*/
		
	
		//FP
		labelFP=toolkit.createLabel(parent, "功能点数目:", SWT.NONE);			
		spnFP=createSpinner(parent,Spinner.LIMIT,200);
		
		//业务领域
		labelBusinessArea=toolkit.createLabel(parent, "业务领域:", SWT.NONE);	
		String[] texts = new String[]{ "电信", "金融", "流通", "保险", "交通", "媒体", "卫生", "制造",
				"政府", "能源" };
		String[] values =new String[]{ "Telecom", "Finance", "Retail", "General","Transport", "Media",
				"HealthCare", "Manufacturing","PublicAdmin", "Energy" };
		cmbBusinessArea=createCombo(parent,texts,values,0);
		
		//开发类型
		labelDevelopType=toolkit.createLabel(parent, "开发类型:", SWT.NONE);
		texts = new String[]{"新开发", "二次开发", "优化", "其它" };
		values =new String[]{"NewDevelopment", "ReDevelopment", "Enhancement", "Other"};
		cmbDevelopType=createCombo(parent,texts,values,0);
		
		//开发平台
		labelDevelopPlatform=toolkit.createLabel(parent, "开发平台:", SWT.NONE);
		texts = new String[]{"大型机", "中型机", "个人计算机", "混合"};
		values =new String[]{"MF", "MR", "PC", "Multi"};
		cmbDevelopPlatform=createCombo(parent,texts,values,0);
		
		//开发技术
		labelDevelopTechnique=toolkit.createLabel(parent, "开发技术:", SWT.NONE);
		texts = new String[]{"面向对象分析设计", "事件建模", "业务领域建模", "回归测试", "面向对象与事件建模",
				"回归测试与业务领域建模", "其它"};
		values =new String[]{"Object Oriented Analysis;Object Oriented Design","Event Modelling",
				"Business Area Modelling","Regression Testing","Object Oriented Analysis;Object Oriented Design;Event Modelling",
				"Regression Testing;Business Area Modelling","Other"};
		cmbDevelopTechnique=createCombo(parent,texts,values,0);
		
		//语言类型
		labelLanguageType=toolkit.createLabel(parent, "语言类型:", SWT.NONE);	
		texts = new String[]{"第二代语言", "第三代语言", "第四代语言", "应用代"};
		values =new String[]{"2GL", "3GL", "4GL", "ApG"};
		cmbLanguageType=createCombo(parent,texts,values,0);
		
		//开发语言
		labelLanguage=toolkit.createLabel(parent, "开发语言:", SWT.NONE);
		texts = new String[]{"ASP", "C#", "VB", "JAVA", "C++", "C", "COBOL"};
		values =new String[]{"ASP", "C#", "VB", "Java", "C++", "C", "Cobol"};
		cmbLanguage=createCombo(parent,texts,values,0);
		
		
		//操作按钮面板
		buttonComposite=new Composite(parent, SWT.NONE);
		gd=new GridData();
		gd.horizontalSpan=3;
		buttonComposite.setLayoutData(gd);
		buttonComposite.setLayout(new FillLayout());
		
		//保存按钮
		saveButton=new Button(buttonComposite,SWT.PUSH);
		saveButton.setText("保存");
		saveButton.setEnabled(false);
		saveButton.addSelectionListener(new SelectionListener(){
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {				
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				saveNodeBasicInformation();
				setIsNodeBasicInformationChanged(false);
				saveButton.setEnabled(false);
			}
		});
		
	}
	
	
	/**
	 * 构建Spinner控件
	 * @param text
	 * @param maxValue
	 * @param selectedValue
	 * @return
	 */
	private  Spinner createSpinner(Composite parent, int maxValue, int selectedValue){
		Spinner spn=new Spinner(parent, SWT.BORDER);
		spn.setMaximum(maxValue);
		spn.setSelection(selectedValue);
		GridData gd=new GridData();
		gd.horizontalAlignment=SWT.FILL;
		spn.setLayoutData(gd);
		spn.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				setIsNodeBasicInformationChanged(true);
				saveButton.setEnabled(true);
			}	
		});
		
		return spn;
	}
	
	
	/**
	 * 构建Combo控件
	 * @param parent
	 * @param texts
	 * @param values
	 * @param selectedIndex
	 * @return
	 */
	private Combo createCombo(Composite parent, String[] texts, String[] values, int selectedIndex){
		Combo cb=new Combo(parent, SWT.READ_ONLY);
		cb.setItems(texts);
		for(int i=0;i<texts.length;i++){
			cb.setData(texts[i], values[i]);
		}
		//cb.select(selectedIndex);
		
		GridData gd=new GridData();
		gd.horizontalAlignment=SWT.FILL;
		cb.setLayoutData(gd);
		
		cb.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {				
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				setIsNodeBasicInformationChanged(true);
				saveButton.setEnabled(true);
			}			
		});
		
		return cb;
	}
	
	
	/**
	 * 获取节点信息
	 * @return
	 */
	public NodeBasicInformation getNodeBasicInformation(){
		NodeBasicInformation nbi=new NodeBasicInformation();
		NodeBasicInfoAccess nbi_access=new NodeBasicInfoAccess();
		nbi_access.initConnection();
		nbi=nbi_access.getNodeByID(nodeID);
		nbi_access.disposeConnection();
		
		nbi.setTeamSize(this.spnTeamSize.getSelection());
		nbi.setDuration(this.spnDuration.getSelection());
		nbi.setFunctionPoints(this.spnFP.getSelection());
		nbi.setBusinessArea(this.cmbBusinessArea.getText());
		nbi.setDevelopmentType(this.cmbDevelopType.getText());
		nbi.setDevelopmentPlatform(this.cmbDevelopPlatform.getText());
		nbi.setDevelopmentTechniques(this.cmbDevelopTechnique.getText());
		nbi.setLanguageType(this.cmbLanguageType.getText());
		nbi.setLanguage(this.cmbLanguage.getText());
		
		//SLOC
		if(this.spnSLOC.getEnabled()==true){
			nbi.setSLOC(this.spnSLOC.getSelection());
		}else{
			nbi.setSLOC(Integer.parseInt(this.textSLOC.getText()));
		}
		
		return nbi;
	}
	
	
	/**
	 * 显示节点信息
	 * @param nbi
	 */
	public void bindNodeBaiscInformation(NodeBasicInformation nbi){
		if(nbi!=null){
			this.texNodeName.setText(nbi.getName());
			this.spnTeamSize.setSelection((int)nbi.getTeamSize());
			this.spnDuration.setSelection((int)nbi.getDuration());
			
			//SLOC
			//this.spnSLOC.setSelection(nbi.getSLOC());
			
			this.spnFP.setSelection(nbi.getFunctionPoints());
			if(nbi.getBusinessArea()!=null){
				this.cmbBusinessArea.setText(nbi.getBusinessArea());
			}
			if(nbi.getDevelopmentType()!=null){
				this.cmbDevelopType.setText(nbi.getDevelopmentType());
			}
			if(nbi.getDevelopmentPlatform()!=null){
				this.cmbDevelopPlatform.setText(nbi.getDevelopmentPlatform());
			}
			if(nbi.getDevelopmentTechniques()!=null){
				this.cmbDevelopTechnique.setText(nbi.getDevelopmentTechniques());
			}
			if(nbi.getLanguageType()!=null){
				this.cmbLanguageType.setText(nbi.getLanguageType());
			}
			if(nbi.getLanguage()!=null){
				this.cmbLanguage.setText(nbi.getLanguage());
			}
		}
	}
	
	
	public void saveNodeBasicInformation(){
		NodeBasicInformation nbi=getNodeBasicInformation();
		NodeBasicInfoAccess nbi_access=new NodeBasicInfoAccess();
		nbi_access.initConnection();
		nbi_access.updateNode(nbi);
		nbi_access.disposeConnection();
	}
}
