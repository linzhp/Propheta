package estimation.quickEstimate;

import java.util.HashMap;

import org.eclipse.jface.wizard.WizardPage;
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
import org.eclipse.swt.widgets.*;

//下步工作，将scale的值与两个text绑定
public class PIPage extends WizardPage {
	public static final String PAGE_NAME = "PI";

	// 规模估算变量
	private Group groupSize;
	private StackLayout sizeStack;
	private Composite comSizeScale;
	private Composite sizeDataArea;
	private Button userInput, history;
	private Scale scaleHistory;
	private Text sizeText;
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
	private Button btnTeamSize, btnDuration;
	private Button[] button = new Button[3];
	private Combo[] combo = new Combo[3];
	private Text textTeamSize, textDuration;

	public PIPage() {
		super(PAGE_NAME, "快速估算: 生产率", null);
		setDescription("请选择一种生产率估算方式：");
	}

	public void createControl(Composite parent) {
		setPageComplete(false);

		Composite topLevel = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		topLevel.setLayout(layout);

		// 供用户输入规模
		groupSize = new Group(topLevel, SWT.NONE);
		createGroupSize();

		// 供用户输入数据库类型的查询参数
		groupFactor = new Group(topLevel, SWT.NONE);
		createGroupFactor();

		setControl(topLevel);
		setPageComplete(true);
	}

	private void createGroupSize() {
		groupSize.setText("规模估算");
		groupSize.setLayout(new GridLayout(1, false));
		groupSize.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createSizeButton();
		createSizeDataArea();
	}

	private void createSizeButton() {
		userInput = new Button(groupSize, SWT.RADIO);
		userInput.setText("用户输入代码行数");
		userInput.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				setPageComplete(true);
				// 将text sizeText置顶
				sizeStack.topControl = sizeText;
				sizeDataArea.layout();
			}
		});

		// 根据历史数据估算规模
		history = new Button(groupSize, SWT.RADIO);
		history.setText("根据数据库的历史数据得出");
		// history.setBounds(10, 50, 200, 30);
		history.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				setPageComplete(true);
				// 将composite comSizeScale置顶
				sizeStack.topControl = comSizeScale;
				sizeDataArea.layout();
			}
		});
	}

	private void createSizeDataArea() {
		sizeDataArea = new Composite(groupSize, SWT.NONE);
		sizeDataArea
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		sizeStack = new StackLayout();
		sizeDataArea.setLayout(sizeStack);

		// 用户手动输入规模
		sizeText = new Text(sizeDataArea, SWT.BORDER);
		sizeText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(true);
			}
		});
		// 通过拖动条选择规模
		// comSizeScale的作用是将scale与textlevel放置在一块，形成一个整体
		comSizeScale = new Composite(sizeDataArea, SWT.NONE);
		comSizeScale.setLayout(new GridLayout(2, false));
		scaleHistory = new Scale(comSizeScale, SWT.NULL);
		scaleHistory.setMinimum(0);
		scaleHistory.setMaximum(1500000);
		scaleHistory.setIncrement(1000);
		scaleHistory.setPageIncrement(300000);
		scaleHistory.setSize(300, 30);
		scaleHistory.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				setPageComplete(true);
				sizeValue = scaleHistory.getSelection()
						+ scaleHistory.getMinimum();
				textLevel.setText(getLevel(sizeValue) + ":" + sizeValue);
				textLevel.getParent().layout();
			}
		});
		scaleHistory.setSelection(100);
		textLevel = new Label(comSizeScale, SWT.NONE);
		textLevel.setSize(100, 30);
		// textLevel.setBounds(305, 5, 100, 30);
	}

	private void createGroupFactor() {
		groupFactor.setText("生产率估算");
		// groupSize.setBounds(10, 10, 300, 100);
		groupFactor.setLayout(new GridLayout(1, false));
		// 设置宽度，使其与外部的控件一样长
		groupFactor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createFactorButton();
		createFactorDataArea();
	}

	private void createFactorButton() {
		// 用户选择根据CSBSG历史数据估算规模
		btnCSBSG = new Button(groupFactor, SWT.RADIO);
		btnCSBSG.setText("根据CSBSG数据库的历史数据得出                ");
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
		btnISBSG = new Button(groupFactor, SWT.RADIO);
		btnISBSG.setText("根据ISBSG数据库的历史数据得出                 ");
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
		factorDataArea = new Composite(groupFactor, SWT.NONE);
		factorStack = new StackLayout();
		factorDataArea.setLayout(factorStack);
		comCSBSG = new Composite(factorDataArea, SWT.NONE);
		comCSBSG.setLayout(new GridLayout(2, false));

		createTeamSizeItem();
		createDurationItem();
		createDevTypeItem();
		createLanguageItem();
		createBusAreaItem();
	}

	private void createTeamSizeItem() {
		btnTeamSize = new Button(comCSBSG, SWT.CHECK);
		btnTeamSize.setText("团队规模（人）");
		btnTeamSize.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				setPageComplete(true);
				if (btnTeamSize.getSelection()) {
					textTeamSize.setVisible(true);
				} else {
					textTeamSize.setVisible(false);
					factors.remove("teamSize");
				}
			}
		});
		textTeamSize = new Text(comCSBSG, SWT.BORDER);
		textTeamSize.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(true);
				factors.put("teamSize", textTeamSize.getText());
			}

		});
		textTeamSize.setVisible(false);
	}

	private void createDurationItem() {
		btnDuration = new Button(comCSBSG, SWT.CHECK);
		btnDuration.setText("项目周期（天）");
		btnDuration.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				setPageComplete(true);
				if (btnDuration.getSelection())
					textDuration.setVisible(true);
				else {
					textDuration.setVisible(false);
					factors.remove("duration");
				}
			}
		});
		textDuration = new Text(comCSBSG, SWT.BORDER);
		textDuration.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(true);
				factors.put("duration", textDuration.getText());
			}
		});
		textDuration.setVisible(false);
	}

	private void createDevTypeItem() {
		button[0] = new Button(comCSBSG, SWT.CHECK);
		button[0].setText("开发类型");
		button[0].addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				if (button[0].getSelection())
					combo[0].setVisible(true);
				else {
					combo[0].setVisible(false);
					factors.remove("developmentType");
				}
			}
		});
		combo[0] = new Combo(comCSBSG, SWT.READ_ONLY);
		combo[0].setItems(new String[] { "新开发", "二次开发", "优化" });
		combo[0].setData("新开发", "NewDevelopment");
		combo[0].setData("二次开发", "ReDevelopment");
		combo[0].setData("优化", "Enhancement");
		combo[0].setVisible(false);
		combo[0].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String key = combo[0].getText();
				factors
						.put("developmentType", combo[0].getData(key)
								.toString());
			}
		});
	}

	private void createLanguageItem() {
		button[1] = new Button(comCSBSG, SWT.CHECK);
		button[1].setText("语言");
		button[1].addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				if (button[1].getSelection())
					combo[1].setVisible(true);
				else
					combo[1].setVisible(false);
			}
		});
		String[] texts = { "ASP", "C#", "VB", "JAVA", "C++", "C", "COBOL" };
		String[] values = { "ASP", "C#", "VB", "Java", "C++", "C", "Cobol" };
		combo[1] = new Combo(comCSBSG, SWT.READ_ONLY);
		combo[1].setItems(texts);
		for (int i = 0; i < texts.length; i++) {
			combo[1].setData(texts[i], values[i]);
		}
		combo[1].setVisible(false);
		combo[1].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String key = combo[1].getText();
				factors.put("language", combo[1].getData(key).toString());
			}
		});
	}

	private void createBusAreaItem() {
		button[2] = new Button(comCSBSG, SWT.CHECK);
		button[2].setText("业务领域                ");
		button[2].addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				if (button[2].getSelection())
					combo[2].setVisible(true);
				else
					combo[2].setVisible(false);
			}
		});
		String[] texts = { "电信", "金融", "流通", "保险", "交通", "媒体", "卫生",
				"制造", "政府", "能源" };
		String[] values = { "Telecom", "Finance", "Retail", "General",
				"Transport", "Media", "HealthCare", "Manufacturing",
				"PublicAdmin", "Energy" };
		combo[2] = new Combo(comCSBSG, SWT.READ_ONLY);
		combo[2].setItems(texts);
		for (int i = 0; i < texts.length; i++) {
			combo[2].setData(texts[i], values[i]);
		}
		combo[2].setVisible(false);
		combo[2].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String key = combo[2].getText();
				factors.put("businessArea", combo[2].getData(key).toString());
			}
		});
	}

	public HashMap<String, String> getFactors() {
		return factors;
	}

	public boolean canFinish() {
		try {
			getSize();
		} catch (NumberFormatException e) {
			return false;
		}
		if (btnCSBSG.getSelection())
			try {
				if (factors.containsKey("teamSize"))
					Integer.parseInt(factors.get("teamSize"));
				if (factors.containsKey("duration"))
					Integer.parseInt(factors.get("duration"));
			} catch (NumberFormatException e) {
				return false;
			}
		else
			try {
				if (factors.containsKey("teamSize"))
					Integer.parseInt(factors.get("teamSize"));
				if (factors.containsKey("duration"))
					Integer.parseInt(factors.get("duration"));
			} catch (NumberFormatException e) {
				return false;
			}
		return true;
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

	public int getSize() {
		if (userInput.getSelection())
			return Integer.parseInt(sizeText.getText());
		else
			return sizeValue;
	}

}
