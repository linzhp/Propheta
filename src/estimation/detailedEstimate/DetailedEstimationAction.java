package estimation.detailedEstimate;

import gui.GUI;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.custom.CTabFolder;

public class DetailedEstimationAction extends Action implements
		ISelectionChangedListener {
	public DetailedEstimationAction()
	{
		super("详细估算");
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		// TODO 判断什么时候该禁用

	}

	@Override
	public void run(){
		CTabFolder folder = GUI.getTopContentArea();
		new COCOMOParameters(folder);
	}
}
