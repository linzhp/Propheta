package estimation.integratedEstimate;

import estimation.integratedEstimate.COCOMOEstimate;
import gui.tabs.NewParamTabAction;
import gui.tabs.ParameterArea;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
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
}
