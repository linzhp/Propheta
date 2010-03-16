package calibration;

import org.eclipse.swt.widgets.Composite;

import gui.tabs.ParameterArea;

public class CalibInput extends ParameterArea {
	public static final int ID = -2;
	public CalibInput(Composite parent){
		super(parent, null);
		form.setText("参数校准");
	}
	
	@Override
	public int getTabID(){
		return ID;
	}
}
