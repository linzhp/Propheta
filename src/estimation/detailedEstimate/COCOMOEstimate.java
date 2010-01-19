package estimation.detailedEstimate;

import java.util.HashMap;

import estimation.ParameterScale;
import gui.ParameterArea;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

public class COCOMOEstimate extends ParameterArea{

	private static String[] levels = {"XL","VL","L","N","H","VH","XH"};
	private HashMap<String, ParameterScale> scales;
	private String[] scaleFactors;
	private String[][] earlyDesignDrivers;
	private String[][] postArchDrivers;
	private Button earlyDesignRadio;
	private Button postArchRadio;
	private Button ok;

	public COCOMOEstimate(Composite parent, int nodeID){
		super(parent, nodeID);
		scales = new HashMap<String, ParameterScale>();
		createButtonArea(form.getBody());
		createScaleFactors(form.getBody());
		createEffortMultipliers(form.getBody());
	}
	
	public HashMap<String, String> getScaleFactors()
	{
		HashMap<String, String> result = new HashMap<String, String>();
		for(String sf : scaleFactors)
		{
			result.put(sf, scales.get(sf).getLevel());
		}
		return result;
	}
	
	public HashMap<String, String> getEffortMultipliers()
	{
		HashMap<String, String> result = new HashMap<String, String>();
		if(earlyDesignRadio.getSelection()){
			for(String[] section : earlyDesignDrivers){
				for(String s : section){
					result.put(s, scales.get(s).getLevel());
				}
			}
		}else if(postArchRadio.getSelection()){
			for(String[] section : postArchDrivers){
				for(String s : section){
					result.put(s, scales.get(s).getLevel());
				}
			}
		}
		return result;
	}
	
	public String getEMtype()
	{
		if (earlyDesignRadio.getSelection())
			return "Early";
		else
			return "Post";
	}
	
	private void createScaleFactors(Composite parent){
		Section section = toolkit.createSection(parent,
				ExpandableComposite.TWISTIE| ExpandableComposite.TITLE_BAR|ExpandableComposite.EXPANDED);
		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		section.setText("比例因子");
		Composite sectionClient = toolkit.createComposite(section);
		scaleFactors = new String[]{"PREC","FLEX","RESL","TEAM","PMAT"};
		buildSectionContent(scaleFactors, sectionClient);
		
		section.setClient(sectionClient);
	}
	
	private void createEffortMultipliers(Composite parent){
		
		String[] sectionNames = {"工作量乘数：产品因素","工作量乘数：平台因素","工作量乘数：人员因素","工作量乘数：项目因素"};
		int numSections = sectionNames.length;
		StackLayout[] layouts = new StackLayout[numSections];
		Composite[] earlyDesignFactors = new Composite[numSections];
		Composite[] postArchFactors = new Composite[numSections];
		//初始化各个小节
		for(int i=0;i<numSections;i++){
			Section section = toolkit.createSection(parent, 
					Section.TWISTIE | ExpandableComposite.TITLE_BAR);
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
		
		earlyDesignDrivers = new String[][] {{"RCPX","RUSE"},{"PDIF"},{"PERS","PREX"},{"FCIL","SCED"}};
		postArchDrivers = new String[][] {{"RELY","DATA","CPLX","RUSE","DOCU"},{"TIME","STOR","PVOL"},
				{"ACAP","PCAP","PCON","APEX","PLEX","LTEX"},{"TOOL","SITE","SCED"}};
		
		fillSections(postArchDrivers, postArchFactors);
		fillSections(earlyDesignDrivers, earlyDesignFactors);
	}

	private void createButtonArea(Composite parent) {
		Composite buttonArea = toolkit.createComposite(parent);
		RowLayout layout = new RowLayout(SWT.HORIZONTAL);
		layout.spacing = 20;
		buttonArea.setLayout(layout);
		earlyDesignRadio = toolkit.createButton(buttonArea, "前期设计", SWT.RADIO);
		postArchRadio = toolkit.createButton(buttonArea, "后体系结构", SWT.RADIO);
		ok = toolkit.createButton(buttonArea, "确定", SWT.PUSH);
		ok.setEnabled(false);
		ok.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				COCOMOEstimateResults results = new COCOMOEstimateResults(COCOMOEstimate.this);
				results.show();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
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
			ParameterScale scale = new ParameterScale(parent, levels, 3);
			toolkit.adapt(scale);
			scales.put(d, scale);
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
			for(int i=0;i<layouts.length;i++){
				layouts[i].topControl = factorsPane[i]; 
				factorsPane[i].getParent().layout();
				ok.setEnabled(true);
			}
		}
		public void widgetDefaultSelected(SelectionEvent e) {
			widgetSelected(e);
		}
	}
}

