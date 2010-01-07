package estimation;

import gui.GUI;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class ParameterScale extends Composite {
	private Scale scale;
	private String name;
	private String level = "N";
	private HashMap<Integer, String> selectMap= new HashMap<Integer, String>();

	public ParameterScale(Composite parent, String[] labels, int currentPostion) {
		super(parent, SWT.NONE);
		int length=labels.length;
		GridLayout gl=new GridLayout(length,false);
		gl.horizontalSpacing=24;//各元素的水平间距
		gl.verticalSpacing=0;//各元素的垂直间距
		setLayout(gl);
		scale=new Scale(this, SWT.NONE);
		FormToolkit toolkit = GUI.getToolkit();
		scale.setBackground(toolkit.getColors().getBackground());
//		scale.setSize(length, 10);
		scale.setMinimum(0);
		scale.setMaximum(length-1);
		scale.setIncrement(1);
		
		scale.setPageIncrement(1);
		scale.setSelection(currentPostion);
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		gd.horizontalSpan=length;//水平跨度
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = GridData.FILL;
		scale.setLayoutData(gd);
		
		for(int i=0;i<length;i++) {
			Label label=new Label(this, SWT.SINGLE);
			label.setText(labels[i]);
			selectMap.put(i, labels[i]);
			label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		}
	}
	
	public String getLevel()
	{
		level = selectMap.get(scale.getSelection());
		return level;
	}

	public int getIndex()
	{
		return scale.getSelection();
	}
	
	public void addSelectionListener (SelectionListener listener) {
		scale.addSelectionListener(listener);
	}
	
	public void addListener(Listener listener)
	{
		scale.addListener(SWT.Selection, listener);
	}
	
	public void setName(String value)
	{
		this.name = value;
	}
	
	public String getParamName()
	{
		return this.name;
	}

}
