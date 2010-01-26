package estimation.quickEstimate;


import gui.tabs.ShowResultTabAction;
import gui.tabs.TabContentArea;

import org.eclipse.swt.widgets.Composite;

public class QEShowResult extends ShowResultTabAction {
	private QEInput quickEstimate;
	boolean isOpen;

	public QEShowResult(QEInput estimate, boolean isOpen) {
		super(estimate.getNode());
		this.quickEstimate = estimate;
		this.isOpen = isOpen;
	}

	@Override
	protected Composite createContents(Composite parent) {
		Composite resultView = new QEResults(parent, quickEstimate, isOpen);
		return resultView;
	}

	@Override
	protected String getTabTitle() {
		return getNode().getName()+"快速估算结果";
	}

	@Override
	protected Class<? extends TabContentArea> pageClass() {
		return QEResults.class;
	}
}
