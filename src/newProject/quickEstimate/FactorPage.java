package newProject.quickEstimate;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class FactorPage extends WizardPage{
	public static final String PAGE_NAME = "Factor";
	  private Button button1, button2, button3, button4, button5, button6;
	  public FactorPage()
	  {
	    super(PAGE_NAME, "生产率快速估算: 选取估算因子", null);  
	
	  }
	  public void createControl(Composite parent)  
	  {
		  Composite topLevel = new Composite(parent, SWT.NONE);
		  GridLayout layout = new GridLayout(1, false);
		    
		    topLevel.setLayout(layout);
		    
		    Label label = new Label(topLevel, SWT.CENTER);
		    label.setText("请选取对您项目影响最大的因子?");
		    
		    button1 = new Button(topLevel, SWT.CHECK);
		    button1.setText("业务领域");
		    
		    button2 = new Button(topLevel, SWT.CHECK);
		    button2.setText("开发类型");
		    
		    button3 = new Button(topLevel, SWT.CHECK);
		    button3.setText("语言");
		    
		    button4 = new Button(topLevel, SWT.CHECK);
		    button4.setText("项目规模");
		    
		    button5 = new Button(topLevel, SWT.CHECK);
		    button5.setText("地区");
		    
		    button6 = new Button(topLevel, SWT.CHECK);
		    button6.setText("团队规模");
		    
		    setControl(topLevel);  
		    setPageComplete(true);    
	  }

}
