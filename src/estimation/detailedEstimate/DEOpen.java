package estimation.detailedEstimate;

import gui.tabs.ShowParamTabAction;
import gui.tabs.ParameterArea;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;

public class DEOpen extends ShowParamTabAction implements
		ISelectionChangedListener {
	public DEOpen()
	{
		super("详细估算");
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		// TODO 判断什么时候该禁用

	}

	@Override
	protected Composite createContents(Composite parent) {
		return new DEInput(parent, getNode());
	}

	@Override
	protected String getTabTitle() {
		return getNode().getName()+"详细估算";
	}

	@Override
	protected Class<? extends ParameterArea> pageClass() {
		return DEInput.class;
	}
}
