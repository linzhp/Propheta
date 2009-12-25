package estimation.quickEstimate;

import java.util.HashMap;

import gui.ParameterArea;
import estimation.detailedEstimate.ParameterScale;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;

public class QuickEstimate extends ParameterArea{

	//公共变量
	private Composite factorDataArea;
	private StackLayout factorStack;
	private GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
	
	// CSBSG变量
	private Button btnCSBSG, btnUserInput,btnScaleSize;
	private Composite comCSBSG;
	private Label labelCSBSGSize;
	private ParameterScale CSizeScale;
	private HashMap<String, String> CSBSGFactors = new HashMap<String, String>();
	private Button btnCSBSGTeamSize, btnCSBSGDuration, btnCSBSGDevType, btnCSBSGLanguage, btnCSBSGBusArea ;
	private Combo cmbCSBSGDevType, cmbCSBSGLanguage, cmbCSBSGBusArea;
	private Spinner spnCSBSGTeamSize, spnCSBSGDuration, spnCSBSGSize;
	
	// ISBSG变量
	
	
	private Button btnISBSG;
	private Composite comISBSG;
	private HashMap<String, String> ISBSGFactors = new HashMap<String, String>();
	private Button btnISBSGTeamSize, btnISBSGDevType, btnISBSGDevTech, btnISBSGDevPlat, btnISBSGLanType;
	private Combo cmbISBSGDevType, cmbISBSGDevTech, cmbISBSGDevPlat, cmbISBSGLanType;
	private Spinner spnISBSGTeamSize, spnISBSGSize;
	

	public QuickEstimate(Composite parent){
		super(parent);
		createEstimation(form.getBody());
	}
	public String getDataType()
	{
		
		if(btnCSBSG.getSelection())
			return "csbsg";
		else
			return "isbsg";
	}
	public HashMap<String, String> getCSBSGFactors() {
		return CSBSGFactors;
	}

	public HashMap<String, String> getISBSGFactors() {
		return ISBSGFactors;
	}
	
	public int getCSBSGSize()
	{
		if(btnUserInput.getSelection())
			return spnCSBSGSize.getSelection();
		else
			return Integer.parseInt(labelCSBSGSize.getText());
	}
	
//	public int getISBSGSize()
//	{
//		return spnISBSGSize.getSelection();
//	}
	
	private Composite createEstimation(Composite parent)
	{
		Composite comEstimation = toolkit.createComposite(parent);
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
		
		//生成参数输入区
		GridData gd = new GridData();
		gd.horizontalSpan = 3;
		factorDataArea = toolkit.createComposite(comEstimation);
		factorDataArea.setLayoutData(gd);
		factorStack = new StackLayout();
		factorDataArea.setLayout(factorStack);
		
		//生成CSBSG参数输入区
		createComCSBSG();
		//生成ISBSG参数输入区
		createComISBSG();
		
		return comEstimation;
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
		
		createCSBSGOtherItem();
		createCSBSGSizeItem();
	}
	
	private void createComISBSG()
	{
		GridLayout layout = new GridLayout(2, false);
		layout.verticalSpacing = 8;
		layout.horizontalSpacing = 15;
		comISBSG = toolkit.createComposite(factorDataArea);
		comISBSG.setLayout(layout);
		createISBSGSizeItem();
		createISBSGTeamSizeItem();
		createISBSGDevTypeItem();
		createISBSGDevTechItem();
		createISBSGDevPlatItem();
		createISBSGLanTypeItem();
	}
	
	private void createCSBSGSizeItem()
	{
		Composite comSize = toolkit.createComposite(comCSBSG);
		comSize.setLayout(new GridLayout(2, false));
		
		Label labelSize = toolkit.createLabel(comSize, "规模（SLOC）：");
		toolkit.createLabel(comSize, null);
		
		btnUserInput = toolkit.createButton(comSize, "用户输入", SWT.RADIO);
		btnUserInput.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				spnCSBSGSize.setVisible(true);
				CSizeScale.setVisible(false);
				labelCSBSGSize.setVisible(false);
			}
		});
		btnUserInput.setSelection(true);
		
		spnCSBSGSize = new Spinner(comSize, SWT.BORDER);
		spnCSBSGSize.setLayoutData(gd);
		spnCSBSGSize.setMaximum(Spinner.LIMIT);
		spnCSBSGSize.setSelection(1000);
		
		btnScaleSize = toolkit.createButton(comSize, "参考历史数据", SWT.RADIO);
		btnScaleSize.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				CSizeScale.setVisible(true);
				labelCSBSGSize.setVisible(true);
				spnCSBSGSize.setVisible(false);
			}
		});
		labelCSBSGSize = toolkit.createLabel(comSize,"64000",SWT.BORDER);
		labelCSBSGSize.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		labelCSBSGSize.setVisible(false);
		
		GridData interGd = new GridData();
		interGd.horizontalSpan = 2;
		
		String[] levels = {"XL","VL","L","N","H","VH","XH"};
		CSizeScale = new ParameterScale(comSize, levels, 3);
		CSizeScale.setLayoutData(interGd);
		CSizeScale.setVisible(false);
		CSizeScale.addListener(new Listener() {
			public void handleEvent(Event event) {
				int sizeValue = CSizeScale.getIndex();
				labelCSBSGSize.setText("" + (int)Math.pow(4, sizeValue)*1000);
				labelCSBSGSize.getParent().layout();
			}
		});
	}
	private void createCSBSGOtherItem()
	{
		Composite comCSBSGOther = toolkit.createComposite(comCSBSG);
		comCSBSGOther.setLayout(new GridLayout(2, false));
		createCSBSGTeamSizeItem(comCSBSGOther);
		createCSBSGDurationItem(comCSBSGOther);
		createCSBSGDevTypeItem(comCSBSGOther);
		createCSBSGLanguageItem(comCSBSGOther);
		createCSBSGBusAreaItem(comCSBSGOther);
	}
	private void createCSBSGTeamSizeItem(Composite parent) {
		btnCSBSGTeamSize = toolkit.createButton(parent, "团队规模（人）：", SWT.CHECK);
		btnCSBSGTeamSize.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				if (btnCSBSGTeamSize.getSelection()) {
					spnCSBSGTeamSize.setVisible(true);
					CSBSGFactors.put("teamSize", spnCSBSGTeamSize.getText());
				} else {
					spnCSBSGTeamSize.setVisible(false);
					CSBSGFactors.remove("teamSize");
				}
			}
		});
		
		spnCSBSGTeamSize = new Spinner(parent, SWT.BORDER);
		//spnCSBSGTeamSize.setLayoutData(gd);
		spnCSBSGTeamSize.setMaximum(Spinner.LIMIT);
		spnCSBSGTeamSize.setSelection(5);
		spnCSBSGTeamSize.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				CSBSGFactors.put("teamSize", spnCSBSGTeamSize.getText());
			}
		});
		spnCSBSGTeamSize.setVisible(false);
	}

	private void createCSBSGDurationItem(Composite parent) {
		btnCSBSGDuration = toolkit.createButton(parent, "项目周期（天）：", SWT.CHECK);
		btnCSBSGDuration.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				if (btnCSBSGDuration.getSelection()){
					spnCSBSGDuration.setVisible(true);
					CSBSGFactors.put("duration", spnCSBSGDuration.getText());
				}else {
					spnCSBSGDuration.setVisible(false);
					CSBSGFactors.remove("duration");
				}
			}
		});
		spnCSBSGDuration = new Spinner(parent, SWT.BORDER);
		spnCSBSGDuration.setLayoutData(gd);
		spnCSBSGDuration.setMaximum(Spinner.LIMIT);
		spnCSBSGDuration.setSelection(180);
		spnCSBSGDuration.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				CSBSGFactors.put("duration", spnCSBSGDuration.getText());
			}
		});
		spnCSBSGDuration.setVisible(false);
	}

	private void createCSBSGDevTypeItem(Composite parent) {
		btnCSBSGDevType = toolkit.createButton(parent, "开发类型：", SWT.CHECK);
		btnCSBSGDevType.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				if (btnCSBSGDevType.getSelection()){
					cmbCSBSGDevType.setVisible(true);
					CSBSGFactors.put("developmentType", cmbCSBSGDevType.getData(cmbCSBSGDevType.getText())
							.toString());
				}else {
					cmbCSBSGDevType.setVisible(false);
					CSBSGFactors.remove("developmentType");
				}
			}
		});
		String[] texts = { "新开发", "二次开发", "优化", "其它" };
		String[] values = { "NewDevelopment", "ReDevelopment", "Enhancement", "Other" };
		cmbCSBSGDevType = new Combo(parent, SWT.READ_ONLY);
		cmbCSBSGDevType.setLayoutData(gd);
		cmbCSBSGDevType.setItems(texts);
		for (int i = 0; i < texts.length; i++) {
			cmbCSBSGDevType.setData(texts[i], values[i]);
		}
		cmbCSBSGDevType.select(0);
		cmbCSBSGDevType.setVisible(false);
		cmbCSBSGDevType.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				CSBSGFactors.put("developmentType", cmbCSBSGDevType.getData(cmbCSBSGDevType.getText())
						.toString());
			}
		});
	}

	private void createCSBSGLanguageItem(Composite parent) {
		btnCSBSGLanguage = toolkit.createButton(parent, "语言：", SWT.CHECK);
		btnCSBSGLanguage.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				if (btnCSBSGLanguage.getSelection()){
					cmbCSBSGLanguage.setVisible(true);
					CSBSGFactors.put("language", cmbCSBSGLanguage.getData(cmbCSBSGLanguage.getText()).toString());
				}else{
					cmbCSBSGLanguage.setVisible(false);
					CSBSGFactors.remove("language");
				}
			}
		});
		String[] texts = { "ASP", "C#", "VB", "JAVA", "C++", "C", "COBOL" };
		String[] values = { "ASP", "C#", "VB", "Java", "C++", "C", "Cobol" };
		cmbCSBSGLanguage = new Combo(parent, SWT.READ_ONLY);
		cmbCSBSGLanguage.setLayoutData(gd);
		cmbCSBSGLanguage.setItems(texts);
		for (int i = 0; i < texts.length; i++) {
			cmbCSBSGLanguage.setData(texts[i], values[i]);
		}
		cmbCSBSGLanguage.select(0);
		cmbCSBSGLanguage.setVisible(false);
		cmbCSBSGLanguage.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				CSBSGFactors.put("language", cmbCSBSGLanguage.getData(cmbCSBSGLanguage.getText()).toString());
			}
		});
	}

	private void createCSBSGBusAreaItem(Composite parent) {
		btnCSBSGBusArea = toolkit.createButton(parent, "业务领域： ", SWT.CHECK);
		btnCSBSGBusArea.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				if (btnCSBSGBusArea.getSelection()){
					cmbCSBSGBusArea.setVisible(true);
					CSBSGFactors.put("businessArea", cmbCSBSGBusArea.getData(cmbCSBSGBusArea.getText()).toString());
				}else{
					cmbCSBSGBusArea.setVisible(false);
					CSBSGFactors.remove("businessArea");
				}
			}
		});
		String[] texts = { "电信", "金融", "流通", "保险", "交通", "媒体", "卫生", "制造",
				"政府", "能源" };
		String[] values = { "Telecom", "Finance", "Retail", "General",
				"Transport", "Media", "HealthCare", "Manufacturing",
				"PublicAdmin", "Energy" };
		cmbCSBSGBusArea = new Combo(parent, SWT.READ_ONLY);
		cmbCSBSGBusArea.setLayoutData(gd);
		cmbCSBSGBusArea.setItems(texts);
		for (int i = 0; i < texts.length; i++) {
			cmbCSBSGBusArea.setData(texts[i], values[i]);
		}
		cmbCSBSGBusArea.select(0);
		cmbCSBSGBusArea.setVisible(false);
		cmbCSBSGBusArea.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				CSBSGFactors.put("businessArea", cmbCSBSGBusArea.getData(cmbCSBSGBusArea.getText()).toString());
			}
		});
	}
	
	private void createISBSGSizeItem()
	{
		toolkit.createLabel(comISBSG, "规模（功能点数）：");
		spnISBSGSize = new Spinner(comISBSG, SWT.BORDER);
		spnISBSGSize.setLayoutData(gd);
		spnISBSGSize.setMaximum(Spinner.LIMIT);
		spnISBSGSize.setSelection(200);
	}
	
	private void createISBSGTeamSizeItem()
	{
		btnISBSGTeamSize = toolkit.createButton(comISBSG, "团队规模（人）：", SWT.CHECK);
		btnISBSGTeamSize.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				if (btnISBSGTeamSize.getSelection()) {
					spnISBSGTeamSize.setVisible(true);
					ISBSGFactors.put("teamSize", spnISBSGTeamSize.getText());
				} else {
					spnISBSGTeamSize.setVisible(false);
					ISBSGFactors.remove("teamSize");
				}
			}
		});
		
		spnISBSGTeamSize = new Spinner(comISBSG, SWT.BORDER);
		spnISBSGTeamSize.setMaximum(Spinner.LIMIT);
		spnISBSGTeamSize.setLayoutData(gd);
		spnISBSGTeamSize.setSelection(5);
		spnISBSGTeamSize.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				ISBSGFactors.put("teamSize", spnISBSGTeamSize.getText());
			}
		});
		spnISBSGTeamSize.setVisible(false);
	}
	
	private void createISBSGDevTypeItem(){
		btnISBSGDevType = toolkit.createButton(comISBSG, "开发类型：", SWT.CHECK);
		btnISBSGDevType.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				if (btnISBSGDevType.getSelection()){
					cmbISBSGDevType.setVisible(true);
					ISBSGFactors.put("developmentType", cmbISBSGDevType.getData(cmbISBSGDevType.getText())
							.toString());
				}else {
					cmbISBSGDevType.setVisible(false);
					ISBSGFactors.remove("developmentType");
				}
			}
		});
		String[] texts = { "新开发", "二次开发", "优化", "其它" };
		String[] values = { "NewDevelopment", "ReDevelopment", "Enhancement", "Other" };
		cmbISBSGDevType = new Combo(comISBSG, SWT.READ_ONLY);
		cmbISBSGDevType.setLayoutData(gd);
		cmbISBSGDevType.setItems(texts);
		for (int i = 0; i < texts.length; i++) {
			cmbISBSGDevType.setData(texts[i], values[i]);
		}
		cmbISBSGDevType.select(0);
		cmbISBSGDevType.setVisible(false);
		cmbISBSGDevType.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ISBSGFactors.put("developmentType", cmbISBSGDevType.getData(cmbISBSGDevType.getText())
						.toString());
			}
		});
	}
	private void createISBSGDevTechItem(){
		btnISBSGDevTech = toolkit.createButton(comISBSG, "开发技术： ", SWT.CHECK);
		btnISBSGDevTech.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				if (btnISBSGDevTech.getSelection()){
					cmbISBSGDevTech.setVisible(true);
					ISBSGFactors.put("developmentTechniques", cmbISBSGDevTech.getData(cmbISBSGDevTech.getText()).toString());
				}else{
					cmbISBSGDevTech.setVisible(false);
					ISBSGFactors.remove("developmentTechniques");
				}
			}
		});
		String[] texts = { "面向对象分析设计", "事件建模", "业务领域建模", "回归测试", "面向对象与事件建模",
				"回归测试与业务领域建模" };
		String[] values = {
				"Object Oriented Analysis;Object Oriented Design",
				"Event Modelling",
				"Business Area Modelling",
				"Regression Testing",
				"Object Oriented Analysis;Object Oriented Design;Event Modelling",
				"Regression Testing;Business Area Modelling" };
		cmbISBSGDevTech = new Combo(comISBSG, SWT.READ_ONLY);
		cmbISBSGDevTech.setLayoutData(gd);
		cmbISBSGDevTech.setItems(texts);
		for (int i = 0; i < texts.length; i++) {
			cmbISBSGDevTech.setData(texts[i], values[i]);
		}
		cmbISBSGDevTech.select(0);
		cmbISBSGDevTech.setVisible(false);
		cmbISBSGDevTech.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ISBSGFactors.put("developmentTechniques", cmbISBSGDevTech.getData(cmbISBSGDevTech.getText()).toString());
			}
		});
	}
	private void createISBSGDevPlatItem(){
		btnISBSGDevPlat = toolkit.createButton(comISBSG, "开发平台：", SWT.CHECK);
		btnISBSGDevPlat.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				if (btnISBSGDevPlat.getSelection()){
					cmbISBSGDevPlat.setVisible(true);
					ISBSGFactors.put("developmentPlatform", cmbISBSGDevPlat.getData(cmbISBSGDevPlat.getText())
							.toString());
				}else {
					cmbISBSGDevPlat.setVisible(false);
					ISBSGFactors.remove("developmentPlatform");
				}
			}
		});
		String[] texts = { "大型机", "中型机", "个人计算机", "混合" };
		String[] values = { "MF", "MR", "PC", "Multi" };
		cmbISBSGDevPlat = new Combo(comISBSG, SWT.READ_ONLY);
		cmbISBSGDevPlat.setLayoutData(gd);
		cmbISBSGDevPlat.setItems(texts);
		for (int i = 0; i < texts.length; i++) {
			cmbISBSGDevPlat.setData(texts[i], values[i]);
		}
		cmbISBSGDevPlat.select(0);
		cmbISBSGDevPlat.setVisible(false);
		cmbISBSGDevPlat.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ISBSGFactors.put("developmentPlatform", cmbISBSGDevPlat.getData(cmbISBSGDevPlat.getText())
						.toString());
			}
		});
	}
	private void createISBSGLanTypeItem(){
		btnISBSGLanType = toolkit.createButton(comISBSG, "语言类型：", SWT.CHECK);
		btnISBSGLanType.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				if (btnISBSGLanType.getSelection()){
					cmbISBSGLanType.setVisible(true);
					ISBSGFactors.put("languageType", cmbISBSGLanType.getData(cmbISBSGLanType.getText())
							.toString());
				}else {
					cmbISBSGLanType.setVisible(false);
					ISBSGFactors.remove("languageType");
				}
			}
		});
		String[] texts = { "第二代语言", "第三代语言", "第四代语言", "应用代" };
		String[] values = { "2GL", "3GL", "4GL", "ApG" };
		cmbISBSGLanType = new Combo(comISBSG, SWT.READ_ONLY);
		cmbISBSGLanType.setLayoutData(gd);
		cmbISBSGLanType.setItems(texts);
		for (int i = 0; i < texts.length; i++) {
			cmbISBSGLanType.setData(texts[i], values[i]);
		}
		cmbISBSGLanType.select(0);
		cmbISBSGLanType.setVisible(false);
		cmbISBSGLanType.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ISBSGFactors.put("languageType", cmbISBSGLanType.getData(cmbISBSGLanType.getText())
						.toString());
			}
		});
	}
}

