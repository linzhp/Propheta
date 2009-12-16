package estimation.sizeEstimate;

import gui.GUI;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;

public class COCOMOSizeAction extends Action implements
		ISelectionChangedListener {
	public COCOMOSizeAction()
	{
		super("COCOMO规模估算");
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		// TODO 判断什么时候该禁用

	}

	@Override
	public void run(){
		Composite content = new COCOMOSize(GUI.getTopContentArea());
		GUI.createNewTab("COCOMO规模估算", content);		
	}
}
