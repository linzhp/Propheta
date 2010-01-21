package estimation.detailedEstimate;

import gui.tabs.NewParamTabAction;
import gui.tabs.ParameterArea;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;

public class DetailedEstimateAction extends NewParamTabAction implements
		ISelectionChangedListener {
	public DetailedEstimateAction()
	{
		super("详细估算");
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		// TODO 判断什么时候该禁用

	}

	@Override
	protected Composite createContents(Composite parent) {
		return new COCOMOEstimate(parent, getNode());
	}

	@Override
	protected String getTabTitle() {
		return getNode().getName()+"详细估算";
	}

	@Override
	protected Class<? extends ParameterArea> pageClass() {
		return COCOMOEstimate.class;
	}
}
