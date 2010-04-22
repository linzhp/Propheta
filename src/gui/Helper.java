package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

public class Helper {
	public static Label createLabel(Composite parent, String text){
		Label l = new Label(parent, SWT.NONE);
		l.setText(text);
		return l;
	}

	public static Spinner createSpinner(Composite parent, int selectedValue, int decimal){
		Spinner spn=new Spinner(parent, SWT.BORDER);
		spn.setMaximum(Spinner.LIMIT);
		spn.setSelection(selectedValue);
		spn.setDigits(decimal);
		GridData gd=new GridData();
		gd.horizontalAlignment=SWT.FILL;
		spn.setLayoutData(gd);
		return spn;
	}
}
