package estimation.integratedEstimate;

import java.util.ArrayList;
import java.util.HashMap;

import data.database.dataAccess.NodeBasicInfoAccess;
import data.database.dataEntities.Entity;
import data.database.dataEntities.EstimateNode;
import gui.tabs.ParameterArea;
import gui.widgets.ParameterScale;


import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

public class IEInput extends ParameterArea{

	private static String[] levels = {"XL","VL","L","N","H","VH","XH"};
	private HashMap<String, ParameterScale> scales = new HashMap<String, ParameterScale>();
	ArrayList<EstimateNode> selectedChildren = new ArrayList<EstimateNode>();
	private Composite comChildrenList;
	private Composite comButtonArea;
	private String[] scaleFactors;

	public IEInput(Composite parent, EstimateNode node){
		super(parent, node);
		//生成确定按钮
		IToolBarManager toolBarManager = form.getToolBarManager();
		toolBarManager.add(new IEShowResult(this, false));
		toolBarManager.update(true);
		
		createButtonArea(form.getBody());
		createSCEDFactor(form.getBody());
		createScaleFactors(form.getBody());
	}
	
	@Override
	public void refresh(){
		comChildrenList.dispose();
		createComChildrenList(comButtonArea);
		comButtonArea.layout();
		selectedChildren.clear();
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
	
	public ArrayList<EstimateNode> getSelectedChildren()
	{
		return selectedChildren;
	}
	
	private void createButtonArea(Composite parent) {
		comButtonArea = toolkit.createComposite(parent);
		comButtonArea.setLayout(new GridLayout(1, false));
		toolkit.createLabel(comButtonArea, "请选择集成的子项目:");
		
		createComChildrenList(comButtonArea);
	}
	
	private void createComChildrenList(Composite parent)
	{
		comChildrenList = toolkit.createComposite(parent);
		comChildrenList.setLayout(new GridLayout(2,false));
		//应该通过nodeID得到tab，而不是树形结构里的被选节点
		NodeBasicInfoAccess nbi = new NodeBasicInfoAccess();
		ArrayList<Entity> children = nbi.getNodesByParentID(node.getId());
		Button[] buttons = new Button[children.size()];
		for(int i=0; i<children.size(); i++)
		{
			EstimateNode node = (EstimateNode)children.get(i);
			buttons[i] = toolkit.createButton(comChildrenList, node.getName(), SWT.CHECK);
			buttons[i].addSelectionListener(new ButtonListener(selectedChildren, node, buttons[i]));
			if(node.getEstType().contains("none"))
				buttons[i].setEnabled(false);
			toolkit.createLabel(comChildrenList, node.getEstType());
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
	
	private final class ButtonListener implements SelectionListener {
		private ArrayList<EstimateNode> selectedChildren;
		private EstimateNode child;
		private Button button;

		private ButtonListener(ArrayList<EstimateNode> selectedChildren, EstimateNode child, Button button) {
			this.selectedChildren = selectedChildren;
			this.child = child;
			this.button = button;
		}
		public void widgetSelected(SelectionEvent e) {
			if (button.getSelection())
				selectedChildren.add(child);
			else
				selectedChildren.remove(child);
			}
		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			widgetSelected(e);
			
		}
	}
}

