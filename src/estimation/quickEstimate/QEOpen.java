package estimation.quickEstimate;

import gui.tabs.ShowParamTabAction;
import gui.tabs.ParameterArea;

import org.eclipse.swt.widgets.Composite;

public class QEOpen extends ShowParamTabAction {


	public QEOpen()
	{
		super("快速估算");
	}
	
	@Override
	protected Composite createContents(Composite parent){
		return new QEInput(parent, getNode());
		
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
	
}
