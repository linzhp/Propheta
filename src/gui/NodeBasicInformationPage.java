package gui;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

/**
 * 节点基本信息页面
 * @author Administrator
 *
 */
public class NodeBasicInformationPage extends BasePage{

	//页面控件
	private Label labelNodeName, labelTeamSize,labelDuration,labelDevelopType,labelLanguage,labelBusinessArea;
	private Text texNodeName;
	private Spinner spnTeamSize,spnDuration;
	private Combo cmbDevelopType,cmbLanguage,cmbBusinessArea;
	
	
	/**
	 * 构造器
	 * @param nodeID 关联的节点ID
	 */
	public NodeBasicInformationPage(int nodeID, Composite parent){
		super(parent);
		this.setPageType(PageType.NodeBasicInformationPage);
		this.setNodeID(nodeID);
		
		createContents(this);
	}
	
	
	private void createContents(Composite parent){
		
	}
}
