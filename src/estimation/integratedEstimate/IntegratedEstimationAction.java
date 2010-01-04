package estimation.integratedEstimate;

import estimation.integratedEstimate.COCOMOEstimate;
import gui.GUI;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;

public class IntegratedEstimationAction extends Action implements
		ISelectionChangedListener {
	public IntegratedEstimationAction()
	{
		super("集成估算");
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		// TODO 判断什么时候该禁用

	}

	@Override
	public void run(){
		Composite content = new COCOMOEstimate(GUI.getTopContentArea());
		GUI.createNewTab("集成估算", content);		
	}
}
