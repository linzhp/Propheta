package estimation.quickEstimate;

import gui.GUI;
import gui.NewParamTabAction;
import gui.ParameterArea;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;

public class QuickEstimateAction extends NewParamTabAction {


	public QuickEstimateAction()
	{
		super("快速估算");
	}
	
	@Override
	protected Composite createContents(Composite parent){
		return new QuickEstimate(parent, node.getId());
		
	}
	
	@Override
	protected Class<? extends ParameterArea> pageClass()
	{
		return QuickEstimate.class;
	}

	@Override
	protected String getTabTitle() {
		return node.getName()+"快速估算";
	}
	
	@Override
	public void run()
	{
		//生成标签
		CTabFolder parent = GUI.getTopContentArea();
		node = GUI.getTreeArea().getSelectedNode();
		boolean opened = false;
		for(CTabItem tab:parent.getItems())
		{
			ParameterArea tabContent = (ParameterArea)tab.getControl();
			if(tabContent.getnodeID() == node.getId() && tabContent.getClass() == pageClass())
			{
				parent.setSelection(tab);
				opened = true;
				break;
			}
		}
		if(opened == false)
		{
			GUI.createNewTab(getTabTitle(), createContents(parent));			
		}
		parent.setFocus();
		
//		createResultsTab(projectSize, formulaEffort, historyEffort,
//				meanProductivity, stanDevProductivity);
	}

}
