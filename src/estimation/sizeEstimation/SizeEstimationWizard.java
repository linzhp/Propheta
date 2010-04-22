package estimation.sizeEstimation;

import org.eclipse.jface.wizard.Wizard;

public abstract class SizeEstimationWizard extends Wizard{
	protected int size;
	
	public int getSize() {
		return size;
	}
}
