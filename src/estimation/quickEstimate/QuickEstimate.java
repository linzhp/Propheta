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
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Spinner;

public class QuickEstimate extends ParameterArea{

	// 规模估算变量
	private Group groupSize;
	private StackLayout sizeStack;
	private Composite comSizeSpinner;
	private Composite comSizeScale;
	private Composite sizeDataArea;
	private Button userInput, history;
	private Scale scaleHistory;
	private Spinner sizeSpinner;
	private Label textLevel;
	private int sizeValue;

	// 两个种数据的PI估算变量
	private Group groupFactor;
	Button btnCSBSG, btnISBSG;
	private Composite factorDataArea;
	private Composite comCSBSG;
	private Composite comISBSG;
	private StackLayout factorStack;
	private HashMap<String, String> factors = new HashMap<String, String>();
	private Button btnTeamSize, btnDuration, btnDevType, btnLanguage, btnBusArea ;
	private Combo cmbDevType, cmbLanguage, cmbBusArea;
	private Spinner spnTeamSize, spnDuration;

	public QuickEstimate(Composite parent){
		super(parent);
		
		createSize(form.getBody());
		createFactors(form.getBody());
	}
	
	public HashMap<String, String> getFactors() {
		return factors;
	}

	// 由规模大小定义其等级，根据数据库的实际情况来,需修改数据
	public String getLevel(int value) {
		String level;
		if (value < 10000)
			level = "很小";
		else if (value >= 10000 && value < 100000)
			level = "较小";
		else if (value >= 10000 && value < 250000)
			level = "中等";
		else if (value >= 250000 && value < 1000000)
			level = "较大";
		else
			level = "很大";
		return level;
	}
	
	public int getEstimatedSize()
	{
		if (userInput.getSelection())
			return sizeSpinner.getSelection();
		else
			return sizeValue;
	}
	
	private Composite createSize(Composite parent){
		Composite pane = toolkit.createComposite(parent);
		GridLayout paneLayout = new GridLayout(1, false);
		paneLayout.verticalSpacing = 20;
		pane.setLayout(paneLayout);
		
		Button ok = toolkit.createButton(pane, "确定", SWT.PUSH);
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
		
		groupSize = new Group(pane, SWT.NONE);
		groupSize.setText("规模参数");
		GridLayout groupLayout = new GridLayout(1, false);
		groupLayout.marginTop = 10;
		groupSize.setLayout(groupLayout);
		groupSize.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		groupSize.setBackground(toolkit.getColors().getBackground());
		
		createSizeButton();
		createSizeDataArea();
		return pane;		
	}
	
	private void createSizeButton() {
		userInput = toolkit.createButton(groupSize,"用户输入代码行数", SWT.RADIO);
		userInput.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				// 将text sizeText置顶
				sizeStack.topControl = comSizeSpinner;
				sizeDataArea.layout();
			}
		});

		// 根据历史数据估算规模
		history = toolkit.createButton(groupSize, "根据数据库的历史数据得出", SWT.RADIO);
		history.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				// 将composite comSizeScale置顶
				sizeStack.topControl = comSizeScale;
				sizeDataArea.layout();
			}
		});
	}

	private void createSizeDataArea() {
		sizeDataArea = toolkit.createComposite(groupSize);
		sizeDataArea
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		sizeStack = new StackLayout();
		sizeDataArea.setLayout(sizeStack);

		// 用户手动输入规模
		comSizeSpinner = toolkit.createComposite(sizeDataArea);
		comSizeSpinner.setLayout(new RowLayout(SWT.HORIZONTAL));
		toolkit.createLabel(comSizeSpinner, "规模（SLOC）：");
		sizeSpinner = new Spinner(comSizeSpinner, SWT.BORDER);
		sizeSpinner.setMaximum(Spinner.LIMIT);
		sizeSpinner.setSelection(1000);
		
		// 通过拖动条选择规模
		// comSizeScale的作用是将scale与textlevel放置在一块，形成一个整体
		comSizeScale = toolkit.createComposite(sizeDataArea);
		comSizeScale.setLayout(new GridLayout(2, false));
		scaleHistory = new Scale(comSizeScale, SWT.NULL);
		scaleHistory.setBackground(toolkit.getColors().getBackground());
		scaleHistory.setMinimum(0);
		scaleHistory.setMaximum(1500000);
		scaleHistory.setIncrement(1000);
		scaleHistory.setPageIncrement(300000);
		scaleHistory.setSize(300, 30);
		scaleHistory.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				sizeValue = scaleHistory.getSelection()
						+ scaleHistory.getMinimum();
				textLevel.setText(getLevel(sizeValue) + ":" + sizeValue);
				textLevel.getParent().layout();
			}
		});
		scaleHistory.setSelection(100);
		textLevel = toolkit.createLabel(comSizeScale, "", SWT.NONE);
		textLevel.setSize(100, 30);
	}

	private Composite createFactors(Composite parent) {
		Composite pane = toolkit.createComposite(parent);
		pane.setLayout(new GridLayout(1, false));
		
		groupFactor = new Group(pane, SWT.NONE);
		groupFactor.setText("生产率参数");
		GridLayout groupLayout = new GridLayout(1, false);
		groupLayout.marginTop = 10;
		groupFactor.setLayout(groupLayout);
		// 设置宽度，使其与外部的控件一样长
		groupFactor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		groupFactor.setBackground(toolkit.getColors().getBackground());
		
		createFactorButton();
		createFactorDataArea();
		return pane;
	}

	private void createFactorButton() {
		// 用户选择根据CSBSG历史数据估算规模
		btnCSBSG = toolkit.createButton(groupFactor,"根据CSBSG数据库的历史数据得出", SWT.RADIO);
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
		btnISBSG = toolkit.createButton(groupFactor, "根据ISBSG数据库的历史数据得出", SWT.RADIO);
		btnISBSG.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				factorStack.topControl = comCSBSG;
				factorDataArea.layout();
			}
		});
	}

	private void createFactorDataArea() {
		factorDataArea = toolkit.createComposite(groupFactor);
		factorStack = new StackLayout();
		factorDataArea.setLayout(factorStack);
		comCSBSG = toolkit.createComposite(factorDataArea);
		comCSBSG.setLayout(new GridLayout(2, false));

		createTeamSizeItem();
		createDurationItem();
		createDevTypeItem();
		createLanguageItem();
		createBusAreaItem();
	}

	private void createTeamSizeItem() {
		btnTeamSize = toolkit.createButton(comCSBSG, "团队规模（人）", SWT.CHECK);
		btnTeamSize.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				if (btnTeamSize.getSelection()) {
					spnTeamSize.setVisible(true);
					factors.put("teamSize", spnTeamSize.getText());
				} else {
					spnTeamSize.setVisible(false);
					factors.remove("teamSize");
				}
			}
		});
		
		spnTeamSize = new Spinner(comCSBSG, SWT.BORDER);
		spnTeamSize.setMaximum(Spinner.LIMIT);
		spnTeamSize.setSelection(5);
		spnTeamSize.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				factors.put("teamSize", spnTeamSize.getText());
			}
		});
		spnTeamSize.setVisible(false);
	}

	private void createDurationItem() {
		btnDuration = toolkit.createButton(comCSBSG, "项目周期（天）", SWT.CHECK);
		btnDuration.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				if (btnDuration.getSelection()){
					spnDuration.setVisible(true);
					factors.put("duration", spnDuration.getText());
				}else {
					spnDuration.setVisible(false);
					factors.remove("duration");
				}
			}
		});
		spnDuration = new Spinner(comCSBSG, SWT.BORDER);
		spnDuration.setMaximum(Spinner.LIMIT);
		spnDuration.setSelection(180);
		spnDuration.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				factors.put("duration", spnDuration.getText());
			}
		});
		spnDuration.setVisible(false);
	}

	private void createDevTypeItem() {
		btnDevType = toolkit.createButton(comCSBSG, "开发类型", SWT.CHECK);
		btnDevType.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				if (btnDevType.getSelection()){
					cmbDevType.setVisible(true);
					factors.put("developmentType", cmbDevType.getData(cmbDevType.getText())
							.toString());
				}else {
					cmbDevType.setVisible(false);
					factors.remove("developmentType");
				}
			}
		});
		cmbDevType = new Combo(comCSBSG, SWT.READ_ONLY);
		cmbDevType.setItems(new String[] { "新开发", "二次开发", "优化" });
		cmbDevType.setData("新开发", "NewDevelopment");
		cmbDevType.setData("二次开发", "ReDevelopment");
		cmbDevType.setData("优化", "Enhancement");
		cmbDevType.select(0);
		cmbDevType.setVisible(false);
		cmbDevType.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				factors.put("developmentType", cmbDevType.getData(cmbDevType.getText())
						.toString());
			}
		});
	}

	private void createLanguageItem() {
		btnLanguage = toolkit.createButton(comCSBSG, "语言", SWT.CHECK);
		btnLanguage.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				if (btnLanguage.getSelection()){
					cmbLanguage.setVisible(true);
					factors.put("language", cmbLanguage.getData(cmbLanguage.getText()).toString());
				}else{
					cmbLanguage.setVisible(false);
					factors.remove("language");
				}
			}
		});
		String[] texts = { "ASP", "C#", "VB", "JAVA", "C++", "C", "COBOL" };
		String[] values = { "ASP", "C#", "VB", "Java", "C++", "C", "Cobol" };
		cmbLanguage = new Combo(comCSBSG, SWT.READ_ONLY);
		cmbLanguage.setItems(texts);
		for (int i = 0; i < texts.length; i++) {
			cmbLanguage.setData(texts[i], values[i]);
		}
		cmbLanguage.select(0);
		cmbLanguage.setVisible(false);
		cmbLanguage.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				factors.put("language", cmbLanguage.getData(cmbLanguage.getText()).toString());
			}
		});
	}

	private void createBusAreaItem() {
		btnBusArea = toolkit.createButton(comCSBSG, "业务领域 ", SWT.CHECK);
		btnBusArea.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				if (btnBusArea.getSelection()){
					cmbBusArea.setVisible(true);
					factors.put("businessArea", cmbBusArea.getData(cmbBusArea.getText()).toString());
				}else{
					cmbBusArea.setVisible(false);
					factors.remove("businessArea");
				}
			}
		});
		String[] texts = { "电信", "金融", "零售业", "保险", "交通运输", "传媒", "卫生保健",
				"制造业", "公共管理", "能源" };
		String[] values = { "Telecom", "Finance", "Retail", "General",
				"Transport", "Media", "HealthCare", "Manufacturing",
				"PublicAdmin", "Energy" };
		cmbBusArea = new Combo(comCSBSG, SWT.READ_ONLY);
		cmbBusArea.setItems(texts);
		for (int i = 0; i < texts.length; i++) {
			cmbBusArea.setData(texts[i], values[i]);
		}
		cmbBusArea.select(0);
		cmbBusArea.setVisible(false);
		cmbBusArea.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				factors.put("businessArea", cmbBusArea.getData(cmbBusArea.getText()).toString());
			}
		});
	}
}

