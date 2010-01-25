package estimation.integratedEstimate;

import data.database.dataAccess.CocomoEstimationAccess;
import data.database.dataAccess.NodeBasicInfoAccess;
import data.database.dataAccess.QuickEstimationAccess;
import data.database.dataEntities.CocomoEstimationRecord;
import data.database.dataEntities.QuickEstimationRecord;
import estimation.integratedEstimate.COCOMOEstimate;
import gui.tabs.ShowParamTabAction;
import gui.tabs.ParameterArea;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;

public class IntegratedEstimateAction extends ShowParamTabAction implements
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
		return new COCOMOEstimate(parent, getNode());
	}

	@Override
	protected String getTabTitle() {
		return getNode().getName()+"集成估算";
	}

	@Override
	protected Class<? extends ParameterArea> pageClass() {
		return COCOMOEstimate.class;
	}
	
	public void run()
	{
		//生成标签
		super.run();
		
		//如果存储了集成估算结果，则生成显示结果的tab
		NodeBasicInfoAccess nbi_access = new NodeBasicInfoAccess();
		nbi_access.initConnection();
		String estType = nbi_access.getNodeByID(getNode().getId()).getEstType();
		nbi_access.disposeConnection();
		
		if(estType.contains("cocomoMultiple")){
			CocomoEstimationAccess cer_access = new CocomoEstimationAccess();
			cer_access.initConnection();
			CocomoEstimationRecord cer = cer_access.getCocomoEstimationByNodeID(getNode().getId());
			cer_access.disposeConnection();
			Double PM = cer.getPM();
			Double devTime = cer.getDevTime();

			COCOMOEstimateResults.createCocomoResultsTab(getNode().getName(), PM, devTime);
		}
		else if(estType.contains("quickMultiple"))
		{
			QuickEstimationAccess qer_access = new QuickEstimationAccess();
			qer_access.initConnection();
			Double effort = qer_access.getQuickEstimationByNodeID(getNode().getId()).getFormulaEffort();
			
			COCOMOEstimateResults.createQuickResultsTab(getNode().getName(), effort);
		}
	}
}
