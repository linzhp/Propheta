package newProject.PIEstimate;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class FactorPage extends WizardPage {
	public static final String PAGE_NAME = "Factor";
	private String factor = "businessArea";
	private String factorValue = "telecom";
	private Button[] button = new Button[5];
	private Combo[] combo = new Combo[5];

	public FactorPage() {
		super(PAGE_NAME, "生产率快速估算: 估算因子", null);

	}

	public void createControl(Composite parent) {
		setDescription("请选取对您项目影响最大的因子: ");

		Composite topLevel = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);

		topLevel.setLayout(layout);

		button[0] = new Button(topLevel, SWT.RADIO);
		button[0].setText("业务领域                ");
		button[0].setSelection(true);
		button[0].addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				factor = "businessArea";
				factorValue = combo[0].getData(combo[0].getText()).toString();
				for (int i = 0; i < 5; i++) {
					if (i != 0)
						combo[i].setVisible(false);
					else
						combo[i].setVisible(true);
				}
			}
		});
		combo[0] = new Combo(topLevel, SWT.READ_ONLY);
		combo[0].setItems(new String[] { "电信", "金融", "零售业", "保险", "制造业",
				"公共管理", "能源" });
		combo[0].setData("电信", "telecom");
		combo[0].setData("金融", "finance");
		combo[0].setData("零售业", "retail");
		combo[0].setData("保险", "general");
		combo[0].setData("制造业", "manufacturing");
		combo[0].setData("公共管理", "publicAdmin");
		combo[0].setData("能源", "energy");
		combo[0].setVisible(true);

		button[1] = new Button(topLevel, SWT.RADIO);
		button[1].setText("开发类型");
		button[1].addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				factor = "developmentType";
				factorValue = combo[0].getData(combo[0].getText()).toString();
				for (int i = 0; i < 5; i++) {
					if (i != 1)
						combo[i].setVisible(false);
					else
						combo[i].setVisible(true);
				}
			}
		});
		combo[1] = new Combo(topLevel, SWT.READ_ONLY);
		combo[1].setItems(new String[] { "新开发", "二次开发", "优化" });
		combo[1].setData("新开发", "newDevelopment");
		combo[1].setData("二次开发", "reDevelopment");
		combo[1].setData("优化", "enhancement");
		combo[1].setVisible(false);

		button[2] = new Button(topLevel, SWT.RADIO);
		button[2].setText("语言");
		button[2].addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				factor = "language";
				factorValue = combo[0].getData(combo[0].getText()).toString();
				for (int i = 0; i < 5; i++) {
					if (i != 2)
						combo[i].setVisible(false);
					else
						combo[i].setVisible(true);
				}
			}
		});
		combo[2] = new Combo(topLevel, SWT.READ_ONLY);
		combo[2].setItems(new String[] { "ASP", "C#", "VB", "JAVA", "C++", "C",
				"COBOL" });
		combo[2].setData("ASP", "ASP");
		combo[2].setData("C#", "C#");
		combo[2].setData("VB", "VB");
		combo[2].setData("JAVA", "Java");
		combo[2].setData("C++", "C++");
		combo[2].setData("C", "C");
		combo[2].setData("COBOL", "Cobol");
		combo[2].setVisible(false);

		/*button[3] = new Button(topLevel, SWT.RADIO);
		button[3].setText("项目规模");
		button[3].addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				factor = "projectSize";
				factorValue = combo[0].getData(combo[0].getText()).toString();
				for (int i = 0; i < 6; i++) {
					if (i != 3)
						combo[i].setVisible(false);
					else
						combo[i].setVisible(true);
				}
			}
		});
		combo[3] = new Combo(topLevel, SWT.READ_ONLY);
		combo[3].setItems(new String[] { "0-4 KLOC", "4-16 KLOC", "16-64 KLOC",
				"64-256 KLOC", "256-1024 KLOC", "大于1024 KLOC" });
		combo[3].setData("0-4 KLOC", "0-4");
		combo[3].setData("4-16 KLOC", "4-16");
		combo[3].setData("16-64 KLOC", "16-64");
		combo[3].setData("64-256 KLOC", "64-256");
		combo[3].setData("256-1024 KLOC", "256-1024");
		combo[3].setData("大于1024 KLOC", ">1024");
		combo[3].setVisible(false);*/

		button[3] = new Button(topLevel, SWT.RADIO);
		button[3].setText("地区");
		button[3].addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				factor = "region";
				factorValue = combo[0].getData(combo[0].getText()).toString();
				for (int i = 0; i < 5; i++) {
					if (i != 3)
						combo[i].setVisible(false);
					else
						combo[i].setVisible(true);
				}
			}
		});
		combo[3] = new Combo(topLevel, SWT.READ_ONLY);
		combo[3].setItems(new String[] { "浙江", "天津", "上海", "辽宁", "北京", "江苏",
				"安徽", "重庆", "其它" });
		combo[3].setData("浙江", "Zhejiang");
		combo[3].setData("天津", "Tianjin");
		combo[3].setData("上海", "Shanghai");
		combo[3].setData("辽宁", "Liaoning");
		combo[3].setData("北京", "Beijing");
		combo[3].setData("江苏", "Jiangsu");
		combo[3].setData("安徽", "Anhui");
		combo[3].setData("重庆", "Chongqing");
		combo[3].setData("其它", "other");
		combo[3].setVisible(false);

		button[4] = new Button(topLevel, SWT.RADIO);
		button[4].setText("团队规模");
		button[4].addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				factor = "teamSize";
				factorValue = combo[0].getData(combo[0].getText()).toString();
				for (int i = 0; i < 5; i++) {
					if (i != 4)
						combo[i].setVisible(false);
					else
						combo[i].setVisible(true);
				}
			}
		});
		combo[4] = new Combo(topLevel, SWT.READ_ONLY);
		combo[4].setItems(new String[] { "1人", "2人", "3人", "4人", "5人", "6人",
				"7人", "8人", "9人", "10人", "11人", "12人", "13人", "14人", "15人" });
		combo[4].setData("1人", "1");
		combo[4].setData("2人", "2");
		combo[4].setData("3人", "3");
		combo[4].setData("4人", "4");
		combo[4].setData("5人", "5");
		combo[4].setData("6人", "6");
		combo[4].setData("7人", "7");
		combo[4].setData("8人", "8");
		combo[4].setData("9人", "9");
		combo[4].setData("10人", "10");
		combo[4].setData("11人", "11");
		combo[4].setData("12人", "12");
		combo[4].setData("13人", "13");
		combo[4].setData("14人", "14");
		combo[4].setData("15人", "15");
		combo[4].setVisible(false);

		// 给每个combo增加监听，改变factorValue的取值
		for (final Combo comboItem : combo) {
			comboItem.select(0);
			comboItem.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					factorValue = comboItem.getData(comboItem.getText())
							.toString();
					System.out.println(factor);
					System.out.println(factorValue);
				}
			});
		}

		setControl(topLevel);
		setPageComplete(true);
	}

	public String getFactor() {
		return factor;
	}

	public String getFactorValue() {
		return factorValue;
	}

}
