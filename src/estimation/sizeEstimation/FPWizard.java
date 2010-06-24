package estimation.sizeEstimation;

public class FPWizard extends SizeEstimationWizard {
	private FunctionPointPage fpPage;
	
	public FPWizard(){
		fpPage = new FunctionPointPage();
	}
	
	@Override
	public void addPages(){
		addPage(fpPage);
	}

	@Override
	public boolean performFinish() {
		size = fpPage.getSize();
		return true;
	}

}
