package estimation.quickEstimate;

import gui.GUI;
import gui.tabs.ShowParamTabAction;
import gui.tabs.ParameterArea;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;

import data.database.dataAccess.NodeBasicInfoAccess;
import data.database.dataAccess.QuickEstimationAccess;
import data.database.dataEntities.QuickEstimationRecord;

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
		
		if(qeInput.getDataType() != null){
			QEShowResult qeShowResult = new QEShowResult(qeInput, true);
			qeShowResult.run();
		}		
	}

}
