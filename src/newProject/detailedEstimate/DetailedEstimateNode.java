package newProject.detailedEstimate;


import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.swt.graphics.Point;

public class DetailedEstimateNode implements IWizardNode {
	private static DetailedEstimateWizard wizard;

	@Override
	public void dispose() {
		wizard=null;
	}

	@Override
	public Point getExtent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IWizard getWizard() {
		if(wizard==null)
			wizard=new DetailedEstimateWizard();
		return wizard;
	}

	@Override
	public boolean isContentCreated() {
		return wizard!=null;
	}

}
