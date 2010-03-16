package estimation.detailedEstimate;

import java.util.HashMap;

import data.database.dataAccess.CocomoEstimationAccess;
import data.database.dataEntities.CocomoEstimationRecord;
import data.database.dataEntities.EstimateNode;
import gui.actions.PrintPDFAction;
import gui.tabs.ParameterArea;
import gui.widgets.ParameterScale;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

public class DEInput extends ParameterArea {

	private static String[] levels = { "XL", "VL", "L", "N", "H", "VH", "XH" };
	private HashMap<String, ParameterScale> scales;
	private String[] scaleFactors;
	private String[][] earlyDesignDrivers;
	private String[][] postArchDrivers;
	private Button earlyDesignRadio;
	private Button postArchRadio;
	private DEShowResult estimate;
	private CocomoEstimationRecord cer;
	private final static HashMap<String, Integer> levelIndex = new HashMap<String, Integer>();
	static {
		for (int i = 0; i < levels.length; i++) {
			levelIndex.put(levels[i], i);
		}
	}

	public DEInput(Composite parent, EstimateNode node) {
		super(parent, node);
		scales = new HashMap<String, ParameterScale>();
		CocomoEstimationAccess cer_access = new CocomoEstimationAccess();
		cer = cer_access.getCocomoEstimationByNodeID(getTabID());

		// 生成确定action,类似于按钮功能
		IToolBarManager toolBarManager = form.getToolBarManager();
		estimate = new DEShowResult(this, false);
		if(cer.get("EMType") == null)
			estimate.setEnabled(false);
		toolBarManager.add(estimate);
		toolBarManager.add(new PrintPDFAction());
		toolBarManager.update(true);

		createButtonArea(form.getBody());
		createScaleFactors(form.getBody());
		createEffortMultipliers(form.getBody());
		setIsInformationChanged(false);
	}

	public HashMap<String, String> getScaleFactors() {
		HashMap<String, String> result = new HashMap<String, String>();
		for (String sf : scaleFactors) {
			result.put(sf, scales.get("SF" + sf).getLevel());
		}
		return result;
	}

	public HashMap<String, String> getEffortMultipliers() {
		HashMap<String, String> result = new HashMap<String, String>();
		if (earlyDesignRadio.getSelection()) {
			for (String[] section : earlyDesignDrivers) {
				for (String s : section) {
					result.put(s, scales.get("early" + s).getLevel());
				}
			}
		} else if (postArchRadio.getSelection()) {
			for (String[] section : postArchDrivers) {
				for (String s : section) {
					result.put(s, scales.get("post" + s).getLevel());
				}
			}
		}
		return result;
	}

	public String getEMtype() {
		if (earlyDesignRadio.getSelection())
			return "Early";
		else
			return "Post";
	}

	private void createScaleFactors(Composite parent) {
		Section section = toolkit.createSection(parent,
				ExpandableComposite.TWISTIE | ExpandableComposite.TITLE_BAR
						| ExpandableComposite.EXPANDED);
		section.setText("比例因子");
		Composite sectionClient = toolkit.createComposite(section);
		scaleFactors = new String[] { "PREC", "FLEX", "RESL", "TEAM", "PMAT" };
		buildSectionContent("SF", scaleFactors, sectionClient);

		section.setClient(sectionClient);
	}

	private void createEffortMultipliers(Composite parent) {

		String[] sectionNames = { "工作量乘数：产品因素", "工作量乘数：平台因素", "工作量乘数：人员因素",
				"工作量乘数：项目因素" };
		int numSections = sectionNames.length;
		StackLayout[] layouts = new StackLayout[numSections];
		Composite[] earlyDesignFactors = new Composite[numSections];
		Composite[] postArchFactors = new Composite[numSections];
		// 初始化各个小节
		for (int i = 0; i < numSections; i++) {
			Section section = toolkit.createSection(parent, Section.TWISTIE
					| ExpandableComposite.TITLE_BAR);
			section.setText(sectionNames[i]);
			Composite sectionClient = toolkit.createComposite(section);
			layouts[i] = new StackLayout();
			sectionClient.setLayout(layouts[i]);
			earlyDesignFactors[i] = toolkit.createComposite(sectionClient);
			postArchFactors[i] = toolkit.createComposite(sectionClient);
			section.setClient(sectionClient);
		}
		// 两种模式切换，为了充分利用ColumnLayout的特性，所有section都是form的孩子，Stack之间的切换只能在section内部
		RadioListener earlyListener = new RadioListener(earlyDesignFactors,
				layouts);
		RadioListener postListener = new RadioListener(postArchFactors, layouts);
		earlyDesignRadio.addSelectionListener(earlyListener);
		postArchRadio.addSelectionListener(postListener);

		// 根据EMType的类型来设置界面,只用考虑early与post的两种情况
		String emType = (String)cer.get("EMType");
		if (emType != null) {
			if (emType.contains("Early")) {
				earlyDesignRadio.setSelection(true);
				earlyListener.widgetDefaultSelected(null);
			} else if (emType.contains("Post")) {
				postArchRadio.setSelection(true);
				postListener.widgetDefaultSelected(null);
			}
		}

		earlyDesignDrivers = new String[][] { { "RCPX", "RUSE" }, { "PDIF" },
				{ "PERS", "PREX" }, { "FCIL", "SCED" } };
		postArchDrivers = new String[][] {
				{ "RELY", "DATA", "CPLX", "RUSE", "DOCU" },
				{ "TIME", "STOR", "PVOL" },
				{ "ACAP", "PCAP", "PCON", "APEX", "PLEX", "LTEX" },
				{ "TOOL", "SITE", "SCED" } };

		fillSections("post", postArchDrivers, postArchFactors);
		fillSections("early", earlyDesignDrivers, earlyDesignFactors);
	}

	private void createButtonArea(Composite parent) {
		Composite buttonArea = toolkit.createComposite(parent);
		RowLayout layout = new RowLayout(SWT.HORIZONTAL);
		layout.spacing = 20;
		buttonArea.setLayout(layout);
		earlyDesignRadio = toolkit.createButton(buttonArea, "前期设计", SWT.RADIO);
		postArchRadio = toolkit.createButton(buttonArea, "后体系结构", SWT.RADIO);
	}

	private void fillSections(String type, String[][] drivers, Composite[] sections) {
		for (int i = 0; i < sections.length; i++) {
			buildSectionContent(type, drivers[i], sections[i]);
		}
	}

	//type值为SF,post,early
	private void buildSectionContent(String type, String[] drivers, Composite parent) {
		parent.setLayout(new GridLayout(2, false));
		for (String d : drivers) {
			// 在此处添加因子的初始设置
			toolkit.createLabel(parent, d);
			int index;
			HashMap<String, Object> factors = cer.attributes;
			Object level = factors.get(d);
			if (level == null || ((String)level).equals("null"))
				index = 3;
			else
				index = levelIndex.get(level);
			ParameterScale scale = new ParameterScale(parent, levels, index);
			scale.addListener(new Listener() {
				public void handleEvent(Event event) {
					setIsInformationChanged(true);
				}
			});
			toolkit.adapt(scale);
			scales.put(type + d, scale);
		}
	}

	private final class RadioListener implements SelectionListener {
		private Composite[] factorsPane;
		private StackLayout[] layouts;

		private RadioListener(Composite[] factorsPane, StackLayout[] layouts) {
			this.factorsPane = factorsPane;
			this.layouts = layouts;
		}

		public void widgetSelected(SelectionEvent e) {
			setIsInformationChanged(true);
			for (int i = 0; i < layouts.length; i++) {
				layouts[i].topControl = factorsPane[i];
				factorsPane[i].getParent().layout();
				estimate.setEnabled(true);
			}
		}

		public void widgetDefaultSelected(SelectionEvent e) {
			widgetSelected(e);
		}
	}
}
