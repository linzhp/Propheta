package estimation.integratedEstimate;

import java.util.ArrayList;
import java.util.HashMap;

import entity.EstimateNode;
import estimation.ParameterScale;
import gui.GUI;
import gui.ParameterArea;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
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
	private Button ok;

	public COCOMOEstimate(Composite parent, int nodeID){
		super(parent, nodeID);
		scales = new HashMap<String, ParameterScale>();
		createButtonArea(form.getBody());
		createSCEDFactor(form.getBody());
		createScaleFactors(form.getBody());
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
	
	public String getSCED()
	{
		return scales.get("SCED").getLevel();
	}
	
	private void createButtonArea(Composite parent) {
		Composite buttonArea = toolkit.createComposite(parent);
		GridLayout layout = new GridLayout(2, false);
		layout.horizontalSpacing=30;
		buttonArea.setLayout(layout);
		toolkit.createLabel(buttonArea, "请选择集成的子项目");
		ok = toolkit.createButton(buttonArea, "确定", SWT.PUSH);
		ok.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		ok.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				COCOMOEstimateResults results = new COCOMOEstimateResults(COCOMOEstimate.this);
				results.show();
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		ArrayList<EstimateNode> children = GUI.getTreeArea().getSelectedNode().getChildren();
		for(EstimateNode child: children)
		{
			toolkit.createButton(buttonArea, child.getName(), SWT.CHECK);
			toolkit.createLabel(buttonArea, child.getEstType());
		}
	}
	
	private void createSCEDFactor(Composite parent){
		Composite comSCEDFactor = toolkit.createComposite(parent);
		comSCEDFactor.setLayout(new GridLayout(2, false));
		toolkit.createLabel(comSCEDFactor, "SCED");
		ParameterScale scale = new ParameterScale(comSCEDFactor, levels, 3);
		toolkit.adapt(scale);
		scales.put("SCED", scale);
	}
	private void createScaleFactors(Composite parent){
		Section section = toolkit.createSection(parent,
				ExpandableComposite.TWISTIE| ExpandableComposite.TITLE_BAR);
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
	
	private void buildSectionContent(String[] drivers, Composite parent){
		parent.setLayout(new GridLayout(2, false));
		for(String d:drivers){
			toolkit.createLabel(parent, d);
			ParameterScale scale = new ParameterScale(parent, levels, 3);
			toolkit.adapt(scale);
			scales.put(d, scale);
		}
	}
}

