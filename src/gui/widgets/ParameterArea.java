package gui.widgets;

import gui.tabs.TabContentArea;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.widgets.*;


public abstract class ParameterArea extends TabContentArea{
	protected FormToolkit toolkit;
	protected ScrolledForm form;
	
	public ParameterArea(Composite parent, int nodeID){
		super(parent, nodeID);
		toolkit = new FormToolkit(Display.getCurrent());
		setLayout(new FillLayout());
		form = toolkit.createScrolledForm(this);
		form.setText("估算参数设置");
		Composite body = form.getBody();
		body.setLayout(new ColumnLayout());
	}
	
	
	/**
	 * 设置页面标题
	 * @param formText
	 */
	public void setFormText(String formText){
		this.form.setText(formText);
	}
	
	/**
	 * 当一个标签被选中需要刷新内容时，请Override这个方法
	 */
	public void refresh(){}
	
	@Override
	public void dispose(){
		toolkit.dispose();
		super.dispose();
	}
}
