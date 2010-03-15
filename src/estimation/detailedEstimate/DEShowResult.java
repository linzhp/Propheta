package estimation.detailedEstimate;

import org.eclipse.swt.widgets.Composite;

import gui.tabs.ShowResultTabAction;
import gui.tabs.TabContentArea;

public class DEShowResult extends ShowResultTabAction {
	private DEInput params;
	private boolean isOpen;
	
	public DEShowResult(DEInput params, boolean isOpen){
		super(params);
		this.params = params;
		this.isOpen = isOpen;
		this.setText("估算");
	}

	@Override
	protected Composite createContents(Composite parent) {
		return new DEResults(parent, params, isOpen);
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
