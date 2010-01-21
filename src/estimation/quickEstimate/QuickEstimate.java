package estimation.quickEstimate;

import java.util.HashMap;

import gui.widgets.ParameterArea;
import data.database.dataAccess.NodeBasicInfoAccess;
import data.database.dataEntities.NodeBasicInformation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.jface.action.Action;

public class QuickEstimate extends ParameterArea{
	//公共变量
	private Composite comEstimation, factorDataArea;
	private StackLayout factorStack;
	private NodeBasicInformation nbi;
	private NodeBasicInfoAccess nbi_access;
	private int CSBSGSize, ISBSGSize;
	
	// CSBSG变量
	private Button btnCSBSG;
	private Composite comCSBSG;
	private HashMap<String, String> CSBSGFactors = new HashMap<String, String>();
	private Button btnCSBSGTeamSize, btnCSBSGDuration, btnCSBSGDevType, btnCSBSGLanguage, btnCSBSGBusArea ;
	
	// ISBSG变量
	private Button btnISBSG;
	private Composite comISBSG;
	private HashMap<String, String> ISBSGFactors = new HashMap<String, String>();
	private Button btnISBSGTeamSize, btnISBSGDevType, btnISBSGDevTech, btnISBSGDevPlat, btnISBSGLanType;

	public QuickEstimate(Composite parent, int nodeID){
		super(parent, nodeID);
		createEstimation(form.getBody());
	}
	
	public void refresh(){
		comEstimation.dispose();
		createEstimation(form.getBody());
		btnCSBSG.setSelection(true);
		factorStack.topControl = comCSBSG;
		factorDataArea.layout();
		form.getBody().layout();
		CSBSGFactors.clear();
		ISBSGFactors.clear();
	}
	public String getDataType()
	{
		if(btnCSBSG.getSelection())
			return "csbsg";
		else
			return "isbsg";
	}
	
	public int getCSBSGSize()
	{
		return CSBSGSize;
	}
	
	public int getISBSGSize()
	{
		return ISBSGSize;
	}
	public HashMap<String, String> getCSBSGFactors() {
		return CSBSGFactors;
	}

	public HashMap<String, String> getISBSGFactors() {
		return ISBSGFactors;
	}
	
	private Composite createEstimation(Composite parent)
	{
		comEstimation = toolkit.createComposite(parent);
		GridLayout layout = new GridLayout(3, false);
		layout.horizontalSpacing = 20;
		comEstimation.setLayout(layout);
		
		//生成ISBSG与CSBSG数据选择按钮
		createRadioButtons(comEstimation);
		
		//生成确定按钮
		Button ok = toolkit.createButton(comEstimation, "确定", SWT.PUSH);
		ok.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		ok.setEnabled(true);
		ok.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				QuickEstimateResults results = new QuickEstimateResults(QuickEstimate.this);
				results.show();
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		createfactorsDataArea(comEstimation);
		
		return comEstimation;
	}
	private void createfactorsDataArea(Composite parent)
	{
		nbi = new NodeBasicInformation();
		nbi_access = new NodeBasicInfoAccess();
		nbi_access.initConnection();
		nbi = nbi_access.getNodeByID(nodeID);
		CSBSGSize = nbi.getSLOC();
		ISBSGSize = nbi.getFunctionPoints();
		
		//生成参数输入区
		GridData gd = new GridData();
		gd.horizontalSpan = 3;
		factorDataArea = toolkit.createComposite(parent);
		factorDataArea.setLayoutData(gd);
		factorStack = new StackLayout();
		factorDataArea.setLayout(factorStack);
		
		//生成CSBSG参数输入区
		createComCSBSG();
		//生成ISBSG参数输入区
		createComISBSG();
		
		nbi_access.disposeConnection();
	}
	
	private void createRadioButtons(Composite parent) {
		// 用户选择根据CSBSG历史数据估算规模
		btnCSBSG = toolkit.createButton(parent,"参考CSBSG数据", SWT.RADIO);
		btnCSBSG.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				factorStack.topControl = comCSBSG;
				factorDataArea.layout();
			}
		});

		// 用户选择根据ISBSG历史数据估算规模
		btnISBSG = toolkit.createButton(parent, "参考ISBSG数据", SWT.RADIO);
		btnISBSG.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				factorStack.topControl = comISBSG;
				factorDataArea.layout();
			}
		});
	}
	
	private void createComCSBSG()
	{
		GridLayout layout = new GridLayout(2, false);
		layout.verticalSpacing = 8;
		layout.horizontalSpacing = 15;
		comCSBSG = toolkit.createComposite(factorDataArea);
		comCSBSG.setLayout(layout);
		createCSBSGItems();
	}
	
	private void createComISBSG()
	{
		GridLayout layout = new GridLayout(2, false);
		layout.verticalSpacing = 8;
		layout.horizontalSpacing = 15;
		comISBSG = toolkit.createComposite(factorDataArea);
		comISBSG.setLayout(layout);
		createISBSGItems();
	}
	
	//CSBSG选项
	private void createCSBSGItems()
	{
		Composite comCSBSGItems = toolkit.createComposite(comCSBSG);
		comCSBSGItems.setLayout(new GridLayout(2, false));
		createCSBSGTeamSizeItem(comCSBSGItems);
		createCSBSGDurationItem(comCSBSGItems);
		createCSBSGDevTypeItem(comCSBSGItems);
		createCSBSGLanguageItem(comCSBSGItems);
		createCSBSGBusAreaItem(comCSBSGItems);
	}
	private void createISBSGItems()
	{
		createISBSGTeamSizeItem(comISBSG);
		createISBSGDevTypeItem(comISBSG);
		createISBSGDevTechItem(comISBSG);
		createISBSGDevPlatItem(comISBSG);
		createISBSGLanTypeItem(comISBSG);
	}
	private void createCSBSGTeamSizeItem(Composite parent) {
		btnCSBSGTeamSize = toolkit.createButton(parent, "团队规模（人）：", SWT.CHECK);
		btnCSBSGTeamSize.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				if (btnCSBSGTeamSize.getSelection()) {
					CSBSGFactors.put("teamSize", String.valueOf(nbi.getTeamSize()));
				} else {
					CSBSGFactors.remove("teamSize");
				}
			}
		});
		toolkit.createLabel(parent, String.valueOf(nbi.getTeamSize()));
	}

	private void createCSBSGDurationItem(Composite parent) {
		btnCSBSGDuration = toolkit.createButton(parent, "项目周期（天）：", SWT.CHECK);
		btnCSBSGDuration.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				if (btnCSBSGDuration.getSelection()){
					CSBSGFactors.put("duration", String.valueOf(nbi.getDuration()));
				}else {
					CSBSGFactors.remove("duration");
				}
			}
		});
		toolkit.createLabel(parent, String.valueOf(nbi.getDuration()));
	}
	
	private void createCSBSGDevTypeItem(Composite parent) {
		btnCSBSGDevType = toolkit.createButton(parent, "开发类型：", SWT.CHECK);
		btnCSBSGDevType.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				if (btnCSBSGDevType.getSelection()){
					CSBSGFactors.put("developmentType", nbi.getDevelopmentType());
				}else {
					CSBSGFactors.remove("developmentType");
				}
			}
		});
		String[] texts = { "新开发", "二次开发", "优化", "其它" };
		String[] values = { "NewDevelopment", "ReDevelopment", "Enhancement", "Other" };
		HashMap<String, String> factors = createHashMap(texts, values);
		toolkit.createLabel(parent, factors.get(nbi.getDevelopmentType()));
	}

	private void createCSBSGLanguageItem(Composite parent) {
		btnCSBSGLanguage = toolkit.createButton(parent, "语言：", SWT.CHECK);
		btnCSBSGLanguage.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				if (btnCSBSGLanguage.getSelection()){
					CSBSGFactors.put("language", nbi.getLanguage());
				}else{
					CSBSGFactors.remove("language");
				}
			}
		});
		String[] texts = { "ASP", "C#", "VB", "JAVA", "C++", "C", "COBOL" };
		String[] values = { "ASP", "C#", "VB", "Java", "C++", "C", "Cobol" };
		HashMap<String, String> factors = createHashMap(texts, values);
		toolkit.createLabel(parent, factors.get(nbi.getLanguage()));
	}
	
	private void createCSBSGBusAreaItem(Composite parent) {
		btnCSBSGBusArea = toolkit.createButton(parent, "业务领域： ", SWT.CHECK);
		btnCSBSGBusArea.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				if (btnCSBSGBusArea.getSelection()){
					CSBSGFactors.put("businessArea", nbi.getBusinessArea());
				}else{
					CSBSGFactors.remove("businessArea");
				}
			}
		});
		String[] texts = new String[]{ "电信", "金融", "流通", "保险", "交通", "媒体", "卫生", "制造",
				"政府", "能源" };
		String[] values =new String[]{ "Telecom", "Finance", "Retail", "General","Transport", "Media",
				"HealthCare", "Manufacturing","PublicAdmin", "Energy" };
		HashMap<String, String> factors = createHashMap(texts, values);
		toolkit.createLabel(parent, factors.get(nbi.getBusinessArea()));
	}
	
	//ISBSG 选项
	private void createISBSGTeamSizeItem(Composite parent)
	{
		btnISBSGTeamSize = toolkit.createButton(parent, "团队规模（人）：", SWT.CHECK);
		btnISBSGTeamSize.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				if (btnISBSGTeamSize.getSelection()) {
					ISBSGFactors.put("teamSize", String.valueOf(nbi.getTeamSize()));
				} else {
					ISBSGFactors.remove("teamSize");
				}
			}
		});
		toolkit.createLabel(parent, String.valueOf(nbi.getTeamSize()));
	}
	
	private void createISBSGDevTypeItem(Composite parent){
		btnISBSGDevType = toolkit.createButton(parent, "开发类型：", SWT.CHECK);
		btnISBSGDevType.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				if (btnISBSGDevType.getSelection()){
					ISBSGFactors.put("developmentType", nbi.getDevelopmentType());
				}else {
					ISBSGFactors.remove("developmentType");
				}
			}
		});
		String[] texts = { "新开发", "二次开发", "优化", "其它" };
		String[] values = { "NewDevelopment", "ReDevelopment", "Enhancement", "Other" };
		HashMap<String, String> factors = createHashMap(texts, values);
		toolkit.createLabel(parent, factors.get(nbi.getDevelopmentType()));

	}
	private void createISBSGDevTechItem(Composite parent){
		btnISBSGDevTech = toolkit.createButton(parent, "开发技术： ", SWT.CHECK);
		btnISBSGDevTech.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				if (btnISBSGDevTech.getSelection()){
					ISBSGFactors.put("developmentTechniques", nbi.getDevelopmentTechniques());
				}else{
					ISBSGFactors.remove("developmentTechniques");
				}
			}
		});
		String[] texts = { "面向对象分析设计", "事件建模", "业务领域建模", "回归测试", "面向对象与事件建模",
				"回归测试与业务领域建模", "其它"};
		String[] values = {
				"Object Oriented Analysis;Object Oriented Design",
				"Event Modelling",
				"Business Area Modelling",
				"Regression Testing",
				"Object Oriented Analysis;Object Oriented Design;Event Modelling",
				"Regression Testing;Business Area Modelling",
				"Other"};//表示不包含OO、Event、Business、Regression的项
		HashMap<String, String> factors = createHashMap(texts, values);
		toolkit.createLabel(parent, factors.get(nbi.getDevelopmentTechniques()));
	}
	private void createISBSGDevPlatItem(Composite parent){
		btnISBSGDevPlat = toolkit.createButton(parent, "开发平台：", SWT.CHECK);
		btnISBSGDevPlat.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				if (btnISBSGDevPlat.getSelection()){
					ISBSGFactors.put("developmentPlatform", nbi.getDevelopmentPlatform());
				}else {
					ISBSGFactors.remove("developmentPlatform");
				}
			}
		});
		String[] texts = { "大型机", "中型机", "个人计算机", "混合" };
		String[] values = { "MF", "MR", "PC", "Multi" };
		HashMap<String, String> factors = createHashMap(texts, values);
		toolkit.createLabel(parent, factors.get(nbi.getDevelopmentPlatform()));
	}
	private void createISBSGLanTypeItem(Composite parent){
		btnISBSGLanType = toolkit.createButton(parent, "语言类型：", SWT.CHECK);
		btnISBSGLanType.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				if (btnISBSGLanType.getSelection()){
					ISBSGFactors.put("languageType", nbi.getLanguageType());
				}else {
					ISBSGFactors.remove("languageType");
				}
			}
		});
		String[] texts = { "第二代语言", "第三代语言", "第四代语言", "应用代" };
		String[] values = { "2GL", "3GL", "4GL", "ApG" };
		HashMap<String, String> factors = createHashMap(texts, values);
		toolkit.createLabel(parent, factors.get(nbi.getLanguageType()));
	}
	
	private HashMap<String, String> createHashMap(String[] texts, String values[])
	{
		HashMap<String, String> factors = new HashMap<String, String>();
		for(int i=0; i<texts.length; i++)
			factors.put(values[i], texts[i]);
		return factors;
	}
}

