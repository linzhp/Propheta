package gui;

import gui.tabs.NewParamTabAction;
import gui.widgets.ParameterArea;

import org.eclipse.swt.widgets.Composite;

public class OpenBasicInformationPageAction extends NewParamTabAction{

	public OpenBasicInformationPageAction(){
		super("打开");
	}
	

	@Override
	protected Composite createContents(Composite parent) {
	
		return new NodeBasicInformationPage(GUI.getTreeArea().getSelectedNode().getId(),parent);
	}


	@Override
	protected String getTabTitle() {
		return node.getName()+" 节点信息";
	}


	@Override
	protected Class<? extends ParameterArea> pageClass() {
		return NodeBasicInformationPage.class;
	}
}
