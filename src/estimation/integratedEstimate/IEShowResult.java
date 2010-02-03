package estimation.integratedEstimate;

import org.eclipse.swt.widgets.Composite;

import gui.tabs.ShowResultTabAction;
import gui.tabs.TabContentArea;

public class IEShowResult extends ShowResultTabAction {
	private IEInput integratedEstimate;
	private boolean isOpen;
	
	public IEShowResult(IEInput integratedEstimate, boolean isOpen){
		super(integratedEstimate);
		this.integratedEstimate = integratedEstimate;
		this.isOpen = isOpen;
	}

	@Override
	protected Composite createContents(Composite parent) {
		return new IEResults(parent, integratedEstimate, isOpen);
	}

	@Override
	protected String getTabTitle() {
		return integratedEstimate.getNode().getName()+"集成估算结果";
	}

	@Override
	protected Class<? extends TabContentArea> pageClass() {
		return IEResults.class;
	}

}
