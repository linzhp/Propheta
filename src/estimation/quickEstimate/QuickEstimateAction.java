package estimation.quickEstimate;

import gui.NewParamTabAction;
import gui.widgets.ParameterArea;

import org.eclipse.swt.widgets.Composite;

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

}
