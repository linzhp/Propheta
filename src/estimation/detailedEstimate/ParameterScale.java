package estimation.detailedEstimate;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;




public class ParameterScale extends Composite {
	private Scale scale;
	private String name;
	private String level = "N";
	private final static HashMap<Integer, String> selectMap= new HashMap<Integer, String>();
	static{
		selectMap.put(0, "VL");
		selectMap.put(1, "L");
		selectMap.put(2, "N");
		selectMap.put(3, "H");
		selectMap.put(4, "VH");
		selectMap.put(5, "XH");
	}

	public ParameterScale(Composite parent, String[] labels, String[] descriptions, int currentPostion) {
		super(parent, SWT.NONE);
		int length=labels.length;
		GridLayout gl=new GridLayout(length,false);
		gl.horizontalSpacing=24;//各元素的水平间距
		gl.verticalSpacing=0;//各元素的垂直间距
		setLayout(gl);
		scale=new Scale(this, SWT.NONE);
		scale.setSize(length, 10);
		scale.setMinimum(0);
		scale.setMaximum(length-1);
		scale.setIncrement(1);
		scale.setPageIncrement(1);
		scale.setSelection(currentPostion);
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		gd.horizontalSpan=length;//水平跨度
		scale.setLayoutData(gd);
		
		for(int i=0;i<length;i++) {
			Label label=new Label(this, SWT.SINGLE);
			label.setText(labels[i]);
			label.setToolTipText(descriptions[i]);
			label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		}
	}
	
	public int getSelection(){
		return scale.getSelection();
	}
	
	public String getLevel()
	{
		level = selectMap.get(getSelection());
		return level;
	}
	/**
	 * @return the scale
	 */
	public Scale getScale() {
		return scale;
	}

	/**
	 * @param scale the scale to set
	 */
	public void setScale(Scale scale) {
		this.scale = scale;
	}

	public void addSelectionListener (SelectionListener listener) {
		scale.addSelectionListener(listener);
	}
	
	public void setName(String value)
	{
		this.name = value;
	}
	
	public String getName()
	{
		return this.name;
	}

}
