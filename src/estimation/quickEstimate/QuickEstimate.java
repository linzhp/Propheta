package estimation.quickEstimate;

import java.util.HashMap;

import gui.ParameterArea;

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
import org.eclipse.swt.widgets.Spinner;

public class QuickEstimate extends ParameterArea{

	//公共变量
	private Composite factorDataArea;
	private StackLayout factorStack;
	private GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
	
	// CSBSG变量
	Button btnCSBSG;
	private Composite comCSBSG;
	private HashMap<String, String> CSBSGFactors = new HashMap<String, String>();
	private Button btnCSBSGTeamSize, btnCSBSGDuration, btnCSBSGDevType, btnCSBSGLanguage, btnCSBSGBusArea ;
	private Combo cmbCSBSGDevType, cmbCSBSGLanguage, cmbCSBSGBusArea;
	private Spinner spnCSBSGTeamSize, spnCSBSGDuration, spnCSBSGSize;
	
	// ISBSG变量
	Button btnISBSG;
	private Composite comISBSG;
	private HashMap<String, String> ISBSGFactors = new HashMap<String, String>();

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
		return spnCSBSGSize.getSelection();
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
		ok.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
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
		layout.horizontalSpacing = 30;
		comCSBSG = toolkit.createComposite(factorDataArea);
		comCSBSG.setLayout(layout);
		createCSBSGSizeItem();
		createCSBSGTeamSizeItem();
		createCSBSGDurationItem();
		createCSBSGDevTypeItem();
		createCSBSGLanguageItem();
		createCSBSGBusAreaItem();
	}
	
	private void createComISBSG()
	{
		GridLayout layout = new GridLayout(2, false);
		layout.verticalSpacing = 8;
		layout.horizontalSpacing = 30;
		comISBSG = toolkit.createComposite(factorDataArea);
		comISBSG.setLayout(layout);
	}
	
	private void createCSBSGSizeItem()
	{
		toolkit.createLabel(comCSBSG, "规模（SLOC）：");
		spnCSBSGSize = new Spinner(comCSBSG, SWT.BORDER);
		spnCSBSGSize.setMaximum(Spinner.LIMIT);
		spnCSBSGSize.setSelection(1000);
	}

	private void createCSBSGTeamSizeItem() {
		btnCSBSGTeamSize = toolkit.createButton(comCSBSG, "团队规模（人）：", SWT.CHECK);
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
		
		spnCSBSGTeamSize = new Spinner(comCSBSG, SWT.BORDER);
		spnCSBSGTeamSize.setMaximum(Spinner.LIMIT);
		spnCSBSGTeamSize.setSelection(5);
		spnCSBSGTeamSize.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				CSBSGFactors.put("teamSize", spnCSBSGTeamSize.getText());
			}
		});
		spnCSBSGTeamSize.setVisible(false);
	}

	private void createCSBSGDurationItem() {
		btnCSBSGDuration = toolkit.createButton(comCSBSG, "项目周期（天）：", SWT.CHECK);
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
		spnCSBSGDuration = new Spinner(comCSBSG, SWT.BORDER);
		spnCSBSGDuration.setMaximum(Spinner.LIMIT);
		spnCSBSGDuration.setSelection(180);
		spnCSBSGDuration.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				CSBSGFactors.put("duration", spnCSBSGDuration.getText());
			}
		});
		spnCSBSGDuration.setVisible(false);
	}

	private void createCSBSGDevTypeItem() {
		btnCSBSGDevType = toolkit.createButton(comCSBSG, "开发类型：", SWT.CHECK);
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
		cmbCSBSGDevType = new Combo(comCSBSG, SWT.READ_ONLY);
		cmbCSBSGDevType.setLayoutData(gd);
		cmbCSBSGDevType.setItems(new String[] { "新开发", "二次开发", "优化" });
		cmbCSBSGDevType.setData("新开发", "NewDevelopment");
		cmbCSBSGDevType.setData("二次开发", "ReDevelopment");
		cmbCSBSGDevType.setData("优化", "Enhancement");
		cmbCSBSGDevType.select(0);
		cmbCSBSGDevType.setVisible(false);
		cmbCSBSGDevType.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				CSBSGFactors.put("developmentType", cmbCSBSGDevType.getData(cmbCSBSGDevType.getText())
						.toString());
			}
		});
	}

	private void createCSBSGLanguageItem() {
		btnCSBSGLanguage = toolkit.createButton(comCSBSG, "语言：", SWT.CHECK);
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
		cmbCSBSGLanguage = new Combo(comCSBSG, SWT.READ_ONLY);
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

	private void createCSBSGBusAreaItem() {
		btnCSBSGBusArea = toolkit.createButton(comCSBSG, "业务领域： ", SWT.CHECK);
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
		cmbCSBSGBusArea = new Combo(comCSBSG, SWT.READ_ONLY);
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
}

