package estimation.detailedEstimate;

import gui.GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
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

	public COCOMOParameters(ScrolledForm form){
		this.form = form;
		toolkit = GUI.getToolkit();
		Composite parent = form.getBody();
		parent.setLayout(new ColumnLayout());
		createSize(parent);
		createScaleFactors(parent);
		createEffortMultipliers(parent);

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
	
	public Composite createScaleFactors(Composite parent){
		String[] sfs = {"PREC","FLEX","RESL","TEAM","PMAT"};
		String[] levels={"很低","低","正常","高","很高","极高"};
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
		Composite pane = toolkit.createComposite(section);
		pane.setLayout(new GridLayout(2, false));
		for(String sf:sfs){
			toolkit.createLabel(pane, sf);
			ParameterScale scale = new ParameterScale(pane, levels, 2);
			toolkit.adapt(scale);
		}
		
		section.setClient(pane);
		return section;
	}
	
	public Composite createEffortMultipliers(Composite parent){
		Composite pane = toolkit.createComposite(parent);
		return pane;
		
	}
	
}

