package estimation.quickEstimate;

import java.util.HashMap;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class PIFactorPage extends WizardPage {
	public static final String PAGE_NAME = "Factor";
	private Button btnTeamSize, btnDuration;
	private Button[] button = new Button[3];
	private Combo[] combo = new Combo[3];
	private Text textTeamSize, textDuration;
	private HashMap<String, String> factors = new HashMap<String, String>();

	public PIFactorPage() {
		super(PAGE_NAME, "生产率快速估算: 估算因子", null);

	}

	public void createControl(Composite parent) {
		setDescription("请选取对您项目影响最大的因子: ");

		Composite topLevel = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);

		topLevel.setLayout(layout);

		btnTeamSize = new Button(topLevel, SWT.CHECK);
		btnTeamSize.setText("团队规模（人）");
		btnTeamSize.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				if (btnTeamSize.getSelection()) {
					textTeamSize.setVisible(true);
				} else {
					textTeamSize.setVisible(false);
					factors.remove("teamSize");
				}
			}
		});
		textTeamSize = new Text(topLevel, SWT.BORDER);
		textTeamSize.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				factors.put("teamSize", textTeamSize.getText());
			}

		});
		textTeamSize.setVisible(false);

		btnDuration = new Button(topLevel, SWT.CHECK);
		btnDuration.setText("项目周期（天）");
		btnDuration.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				if (btnDuration.getSelection())
					textDuration.setVisible(true);
				else {
					textDuration.setVisible(false);
					factors.remove("duration");
				}
			}
		});
		textDuration = new Text(topLevel, SWT.BORDER);
		textDuration.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				factors.put("duration", textDuration.getText());
			}
		});
		textDuration.setVisible(false);

		button[0] = new Button(topLevel, SWT.CHECK);
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
		combo[0] = new Combo(topLevel, SWT.READ_ONLY);
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

		button[1] = new Button(topLevel, SWT.CHECK);
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
		combo[1] = new Combo(topLevel, SWT.READ_ONLY);
		combo[1].setItems(new String[] { "ASP", "C#", "VB", "JAVA", "C++", "C",
				"COBOL" });
		combo[1].setData("ASP", "ASP");
		combo[1].setData("C#", "C#");
		combo[1].setData("VB", "VB");
		combo[1].setData("JAVA", "Java");
		combo[1].setData("C++", "C++");
		combo[1].setData("C", "C");
		combo[1].setData("COBOL", "Cobol");
		combo[1].setVisible(false);
		combo[1].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String key = combo[1].getText();
				factors.put("language", combo[1].getData(key).toString());
			}
		});

		button[2] = new Button(topLevel, SWT.CHECK);
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

		combo[2] = new Combo(topLevel, SWT.READ_ONLY);
		combo[2].setItems(new String[] { "电信", "金融", "零售业", "保险", "交通运输", "传媒",
				"卫生保健", "制造业", "公共管理", "能源" });
		combo[2].setData("电信", "Telecom");
		combo[2].setData("金融", "Finance");
		combo[2].setData("零售业", "Retail");
		combo[2].setData("保险", "General");
		combo[2].setData("交通运输", "Transport");
		combo[2].setData("传媒", "Media");
		combo[2].setData("卫生保健", "HealthCare");
		combo[2].setData("制造业", "Manufacturing");
		combo[2].setData("公共管理", "PublicAdmin");
		combo[2].setData("能源", "Energy");
		combo[2].setVisible(false);
		combo[2].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String key = combo[2].getText();
				factors.put("businessArea", combo[2].getData(key).toString());
			}
		});

		setControl(topLevel);
		setPageComplete(true);
	}

	public HashMap<String, String> getFactors() {
		return factors;
	}

	public boolean canFinish() {
		try {
			if (factors.containsKey("teamSize"))
				Integer.parseInt(factors.get("teamSize"));
			if (factors.containsKey("Duration"))
				Integer.parseInt(factors.get("Duration"));
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

}
