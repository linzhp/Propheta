package estimation.detailedEstimate;

import gui.GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ColumnLayout;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

public class COCOMOParameters{

	private FormToolkit toolkit;
	private ScrolledForm form;
	private String[] levels;

	public COCOMOParameters(ScrolledForm form){
		this.form = form;
		toolkit = GUI.getToolkit();
		levels = new String[] {"极低","很低","低","正常","高","很高","极高"};
		Composite parent = form.getBody();
		parent.setLayout(new ColumnLayout());
		createSize(parent);
		createEffortMultipliers(parent);
		createScaleFactors(parent);

//		toolkit.dispose();
	}
	
	public Composite createSize(Composite parent){
		Composite pane = toolkit.createComposite(parent);
		pane.setLayout(new RowLayout(SWT.HORIZONTAL));
		toolkit.createLabel(pane, "规模（SLOC）：");
		Spinner spinner = new Spinner(pane, SWT.BORDER);
		spinner.setMaximum(Spinner.LIMIT);
		spinner.setSelection(1000);
		return pane;		
	}
	
	public void createScaleFactors(Composite parent){
		String[] sfs = {"PREC","FLEX","RESL","TEAM","PMAT"};
		Section section = toolkit.createSection(parent,
				Section.DESCRIPTION | ExpandableComposite.TWISTIE
						| ExpandableComposite.TITLE_BAR);
		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		section.setText("比例因子");
		Composite sectionClient = toolkit.createComposite(section);
		buildSectionContent(sfs, sectionClient);
		
		section.setClient(sectionClient);
	}
	
	public void createEffortMultipliers(Composite parent){
		Composite radioArea = toolkit.createComposite(parent);
		radioArea.setLayout(new RowLayout(SWT.HORIZONTAL));
		Button earlyDesignRadio = toolkit.createButton(radioArea, "前期设计", SWT.RADIO);
		Button postArchRadio = toolkit.createButton(radioArea, "后体系结构", SWT.RADIO);
		
		String[] sectionNames = {"产品因素","平台因素","人员因素","项目因素"};
		int numSections = sectionNames.length;
		StackLayout[] layouts = new StackLayout[numSections];
		Composite[] earlyDesignFactors = new Composite[numSections];
		Composite[] postArchFactors = new Composite[numSections];
		//初始化各个小节
		for(int i=0;i<numSections;i++){
			Section section = toolkit.createSection(parent, 
					Section.DESCRIPTION | ExpandableComposite.TWISTIE| ExpandableComposite.TITLE_BAR);
			section.setText(sectionNames[i]);
			Composite sectionClient = toolkit.createComposite(section);
			layouts[i] = new StackLayout();
			sectionClient.setLayout(layouts[i]);
			earlyDesignFactors[i] = toolkit.createComposite(sectionClient);
			postArchFactors[i] = toolkit.createComposite(sectionClient);
			section.setClient(sectionClient);
		}
		//两种模式切换，为了充分利用ColumnLayout的特性，所有section都是form的孩子，Stack之间的切换只能在section内部
		earlyDesignRadio.addSelectionListener(new RadioListener(earlyDesignFactors, layouts));
		postArchRadio.addSelectionListener(new RadioListener(postArchFactors, layouts));
		postArchRadio.setSelection(true);
		
		String[][] earlyDesignDrivers = {{"RCPX","RUSE"},{"PDIF"},{"PERS","PREX"},{"FCIL","SCED"}};
		String[][] postArchDrivers = {{"RELY","DATA","CPLX","RUSE","DOCU"},{"TIME","STOR","PVOL"},
				{"ACAP","PCAP","PCON","APEX","PLEX","LTEX"},{"TOOL","SITE","SCED"}};
		
		fillSections(postArchDrivers, postArchFactors);
		fillSections(earlyDesignDrivers, earlyDesignFactors);
	}
	
	private void fillSections(String[][] drivers, Composite[] sections){
		for(int i=0;i<sections.length;i++){
			buildSectionContent(drivers[i], sections[i]);
		}
	}
	
	private void buildSectionContent(String[] drivers, Composite parent){
		parent.setLayout(new GridLayout(2, false));
		for(String d:drivers){
			toolkit.createLabel(parent, d);
			ParameterScale scale = new ParameterScale(parent, levels, 2);
			toolkit.adapt(scale);
		}
		
	}

	private final class RadioListener implements SelectionListener {
		private final Composite[] factorsPane;
		private final StackLayout[] layouts;

		private RadioListener(Composite[] factorsPane, StackLayout[] layouts) {
			this.factorsPane = factorsPane;
			this.layouts = layouts;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			for(int i=0;i<layouts.length;i++){
				layouts[i].topControl = factorsPane[i]; 
				factorsPane[i].getParent().layout();
			}
			
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			widgetSelected(e);
			
		}
	}
}

