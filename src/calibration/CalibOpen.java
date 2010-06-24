package calibration;

import gui.tabs.ShowParamTabAction;
import gui.tabs.TabContentArea;

import org.eclipse.swt.widgets.Composite;

public class CalibOpen extends ShowParamTabAction {
	public CalibOpen(){
		super("校准");
	}

	@Override
	protected Composite createContents(Composite parent) {
		return new CalibInput(parent);
	}

	@Override
	protected String getTabTitle() {
		return "全局参数调整";
	}

	@Override
	protected Class<? extends TabContentArea> pageClass() {
		return CalibInput.class;
	}
}
