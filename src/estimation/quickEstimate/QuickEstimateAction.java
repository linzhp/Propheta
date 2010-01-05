package estimation.quickEstimate;

import gui.GUI;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Composite;

public class QuickEstimateAction extends Action {

	public QuickEstimateAction()
	{
		super("快速估算");
	}
	
	@Override
	public void run(){
		Composite content = new QuickEstimate(GUI.getTopContentArea());
		GUI.createNewTab("快速估算", content);		
	}
}
