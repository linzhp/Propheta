package estimation.sizeEstimate;

import gui.NewParamTabAction;
import gui.ParameterArea;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;

public class COCOMOSizeAction extends NewParamTabAction implements
		ISelectionChangedListener {
	public COCOMOSizeAction()
	{
		super("COCOMO规模估算");
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		// TODO 判断什么时候该禁用

	}

	@Override
	protected Composite createContents(Composite parent) {
		return new COCOMOSize(parent, node.getId());
	}

	@Override
	protected String getTabTitle() {
		return node.getName()+"COCOMO规模估算";
	}

	@Override
	protected Class<? extends ParameterArea> pageClass() {
		return COCOMOSize.class;
	}
}
