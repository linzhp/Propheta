package newProject.quickEstimate;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.swt.graphics.Point;

public class QuickEstimateNode implements IWizardNode {
	private static QuickEstimateWizard wizard;

	@Override
	public void dispose() {
		wizard=null;
	}

	@Override
	public Point getExtent() {
		return null;
	}

	@Override
	public IWizard getWizard() {
		if(wizard==null)
			wizard=new QuickEstimateWizard();
		return wizard;
	}

	@Override
	public boolean isContentCreated() {
		return wizard!=null;
	}

}
