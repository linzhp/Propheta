package gui.tabs;

import estimation.entity.EstimateNode;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.widgets.*;


public abstract class ParameterArea extends TabContentArea{
	protected FormToolkit toolkit;
	protected ScrolledForm form;
	
	public ParameterArea(Composite parent, EstimateNode node){
		super(parent, node);
		toolkit = new FormToolkit(Display.getCurrent());
		setLayout(new FillLayout());
		form = toolkit.createScrolledForm(this);
		toolkit.decorateFormHeading(form.getForm());
		form.setText("估算参数设置");
		Composite body = form.getBody();
		body.setLayout(new ColumnLayout());
	}
	
	public EstimateNode getNode(){
		return node;
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