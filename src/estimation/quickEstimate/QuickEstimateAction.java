package estimation.quickEstimate;

import gui.GUI;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;

public class QuickEstimateAction extends Action implements
		ISelectionChangedListener {
	public QuickEstimateAction()
	{
		super("快速估算");
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		// TODO 判断什么时候该禁用

	}

	@Override
	public void run(){
		Composite content = new QuickEstimate(GUI.getTopContentArea());
		GUI.createNewTab("快速估算", content);		
	}
}
