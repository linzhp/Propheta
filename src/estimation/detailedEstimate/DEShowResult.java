package estimation.detailedEstimate;

import org.eclipse.swt.widgets.Composite;

import gui.tabs.ShowResultTabAction;
import gui.tabs.TabContentArea;

public class DEShowResult extends ShowResultTabAction {
	private DEInput params;
	public DEShowResult(DEInput params){
		super(params.getNode());
		this.params = params;
	}

	@Override
	protected Composite createContents(Composite parent) {
		return new DEResults(parent, params);
	}

	@Override
	protected String getTabTitle() {
		return params.getNode().getName()+"COCOMO详細估算结果";
	}

	@Override
	protected Class<? extends TabContentArea> pageClass() {
		return DEResults.class;
	}

}
