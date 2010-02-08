package estimation.quickEstimate;

import gui.tabs.ShowParamTabAction;
import gui.tabs.ParameterArea;

import org.eclipse.swt.widgets.Composite;

import data.database.dataAccess.QuickEstimationAccess;

public class QEOpen extends ShowParamTabAction {

	private QEInput qeInput;
	public QEOpen()
	{
		super("快速估算");
	}
	
	@Override
	protected Composite createContents(Composite parent){
		qeInput = new QEInput(parent, getNode());
		return qeInput;
		
	}
	
	@Override
	protected Class<? extends ParameterArea> pageClass()
	{
		return QEInput.class;
	}

	@Override
	protected String getTabTitle() {
		return getNode().getName()+"快速估算";
	}
	
	public void run()
	{
		super.run();
		
		QuickEstimationAccess qer_access = new QuickEstimationAccess();
		String dataType = (String)qer_access.getQuickEstimationByNodeID(getNode().getId()).get("dataType");
		if(dataType != null){
			QEShowResult qeShowResult = new QEShowResult(qeInput, true);
			qeShowResult.run();
		}		
	}

}
