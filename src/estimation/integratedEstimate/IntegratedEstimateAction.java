package estimation.integratedEstimate;

import data.database.dataAccess.CocomoEstimationAccess;
import data.database.dataAccess.NodeBasicInfoAccess;
import data.database.dataAccess.QuickEstimationAccess;
import data.database.dataEntities.CocomoEstimationRecord;
import data.database.dataEntities.QuickEstimationRecord;
import estimation.integratedEstimate.COCOMOEstimate;
import estimation.quickEstimate.QuickEstimateResults;
import gui.GUI;
import gui.NewParamTabAction;
import gui.widgets.ParameterArea;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;

public class IntegratedEstimateAction extends NewParamTabAction implements
		ISelectionChangedListener {
	public IntegratedEstimateAction()
	{
		super("集成估算");
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		
	}

	@Override
	protected Composite createContents(Composite parent) {
		return new COCOMOEstimate(parent, node.getId());
	}

	@Override
	protected String getTabTitle() {
		return node.getName()+"集成估算";
	}

	@Override
	protected Class<? extends ParameterArea> pageClass() {
		return COCOMOEstimate.class;
	}
	
	public void run()
	{
		//生成标签
		CTabFolder parent = GUI.getTopContentArea();
		node = GUI.getTreeArea().getSelectedNode();
		boolean opened = false;
		for(CTabItem tab:parent.getItems())
		{
			ParameterArea tabContent = (ParameterArea)tab.getControl();
			if(tabContent.getnodeID() == node.getId() && tabContent.getClass() == pageClass())
			{
				parent.setSelection(tab);
				opened = true;
				break;
			}
		}
		if(opened == false) 
		{
			GUI.createNewTab(getTabTitle(), createContents(parent));			
		}
		parent.setFocus();
		
		//如果存储了集成估算结果，则生成显示结果的tab
		NodeBasicInfoAccess nbi_access = new NodeBasicInfoAccess();
		nbi_access.initConnection();
		String estType = nbi_access.getNodeByID(node.getId()).getEstType();
		nbi_access.disposeConnection();
		
		if(estType.contains("cocomoMultiple")){
			CocomoEstimationAccess cer_access = new CocomoEstimationAccess();
			cer_access.initConnection();
			CocomoEstimationRecord cer = cer_access.getCocomoEstimationByNodeID(node.getId());
			cer_access.disposeConnection();
			Double PM = cer.getPM();
			Double devTime = cer.getDevTime();

			COCOMOEstimateResults.createCocomoResultsTab(node.getName(), PM, devTime);
		}
		else if(estType.contains("quickMultiple"))
		{
			QuickEstimationAccess qer_access = new QuickEstimationAccess();
			qer_access.initConnection();
			Double effort = qer_access.getQuickEstimationByNodeID(node.getId()).getFormulaEffort();
			
			COCOMOEstimateResults.createQuickResultsTab(node.getName(), effort);
		}
	}
}
