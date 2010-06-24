package estimation.integratedEstimate;

import gui.tabs.ShowParamTabAction;
import gui.tabs.ParameterArea;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;

public class IEOpen extends ShowParamTabAction implements
		ISelectionChangedListener {
	private IEInput ieInput;

	public IEOpen() {
		super("集成估算");
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {

	}

	@Override
	protected Composite createContents(Composite parent) {
		ieInput = new IEInput(parent, getNode());
		return ieInput;
	}

	@Override
	protected String getTabTitle() {
		return getNode().getName() + "集成估算";
	}

	@Override
	protected Class<? extends ParameterArea> pageClass() {
		return IEInput.class;
	}

	public void run() {
		// 生成标签
		super.run();
		
		IEShowResult ieShowResult = new IEShowResult(ieInput, true);
		ieShowResult.run();
	}
}
