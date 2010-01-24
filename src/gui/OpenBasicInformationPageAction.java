package gui;

import gui.tabs.ShowParamTabAction;
import gui.tabs.ParameterArea;

import org.eclipse.swt.widgets.Composite;

public class OpenBasicInformationPageAction extends ShowParamTabAction{

	public OpenBasicInformationPageAction(){
		super("打开");
	}
	

	@Override
	protected Composite createContents(Composite parent) {
	
		return new NodeBasicInformationPage(getNode(),parent);
	}


	@Override
	protected String getTabTitle() {
		return getNode().getName()+" 节点信息";
	}


	@Override
	protected Class<? extends ParameterArea> pageClass() {
		return NodeBasicInformationPage.class;
	}
}
