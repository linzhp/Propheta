package estimation.quickEstimate;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.swt.graphics.Point;

public class QuickEstimateNode implements IWizardNode {
	//用实例变量，防止内存泄漏
	private QuickEstimateWizard wizard;

	/**
	 * 这个方法会被WizardSelectionPage的dispose方法自动调用，
	 * 而后者又会被它所在的Wizard.dispose()自动调用
	 */
	@Override
	public void dispose() {
        if ( wizard != null )
        {
            wizard.dispose();
            wizard = null;
        }
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
