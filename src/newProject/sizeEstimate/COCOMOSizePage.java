package newProject.sizeEstimate;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class COCOMOSizePage extends WizardPage {
	public static final String PAGE_NAME = "COCOMOSize";
	  private Button button;
	  public COCOMOSizePage()
	  {
	    super(PAGE_NAME, "COCOMOSizePage", null);  
	  }
	  public void createControl(Composite parent)  
	  {
	    Composite topLevel = new Composite(parent, SWT.NONE);
	    topLevel.setLayout(new GridLayout(2, false));
	    Label l = new Label(topLevel, SWT.CENTER);
	    l.setText("Use default directory?");
	    button = new Button(topLevel, SWT.CHECK);
	    setControl(topLevel);  
	    setPageComplete(true);   
	  }
	  public boolean useDefaultDirectory()  
	  {
	    return button.getSelection();
	  }
}
