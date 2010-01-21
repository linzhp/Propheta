package estimation.quickEstimate;

import gui.GUI;
import gui.NewParamTabAction;
import gui.widgets.ParameterArea;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;

import data.database.dataAccess.NodeBasicInfoAccess;
import data.database.dataAccess.QuickEstimationAccess;
import data.database.dataEntities.QuickEstimationRecord;

public class QuickEstimateAction extends NewParamTabAction {


	public QuickEstimateAction()
	{
		super("快速估算");
	}
	
	@Override
	protected Composite createContents(Composite parent){
		return new QuickEstimate(parent, node.getId());
		
	}
	
	@Override
	protected Class<? extends ParameterArea> pageClass()
	{
		return QuickEstimate.class;
	}

	@Override
	protected String getTabTitle() {
		return node.getName()+"快速估算";
	}
	
	@Override
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
		
		NodeBasicInfoAccess nbi_access = new NodeBasicInfoAccess();
		nbi_access.initConnection();
		int projectSize = nbi_access.getNodeByID(node.getId()).getSLOC();
		nbi_access.disposeConnection();
		
		QuickEstimationAccess qer_access = new QuickEstimationAccess();
		qer_access.initConnection();
		QuickEstimationRecord qer = qer_access.getQuickEstimationByNodeID(node.getId());
		qer_access.disposeConnection();
		String dataType = qer.getDataType();
		
		
		if(dataType != null){
			Double formulaEffort = qer.getFormulaEffort();
			Double historyEffort = qer.getHistoryEffort();
			Double meanProductivity = qer.getMeanProductivity();
			Double stanDevProductivity = qer.getStanDevProductivity();

			QuickEstimateResults.createResultsTab(node.getName(),projectSize, dataType, formulaEffort, historyEffort,
					meanProductivity, stanDevProductivity);
		}
		
		
	}

}
