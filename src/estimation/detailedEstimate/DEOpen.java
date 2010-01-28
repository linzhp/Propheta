package estimation.detailedEstimate;

import gui.tabs.ShowParamTabAction;
import gui.tabs.ParameterArea;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;

import data.database.dataAccess.CocomoEstimationAccess;

public class DEOpen extends ShowParamTabAction implements
		ISelectionChangedListener {
	private DEInput deInput;
	public DEOpen()
	{
		super("详细估算");
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		// TODO 判断什么时候该禁用

	}

	@Override
	protected Composite createContents(Composite parent) {
		deInput = new DEInput(parent, getNode());
		return deInput;
	}

	@Override
	protected String getTabTitle() {
		return getNode().getName()+"详细估算";
	}

	@Override
	protected Class<? extends ParameterArea> pageClass() {
		return DEInput.class;
	}
	
	public void run()
	{
		super.run();
		
		CocomoEstimationAccess cer_access = new CocomoEstimationAccess();
		cer_access.initConnection();
		String EMType = cer_access.getCocomoEstimationByNodeID(getNode().getId()).getEMType();
		cer_access.disposeConnection();
		
		if(EMType != null){
			DEShowResult deShowResult = new DEShowResult(deInput, true);
			deShowResult.run();
		}		
	}
}
