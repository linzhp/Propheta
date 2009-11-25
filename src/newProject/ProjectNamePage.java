package newProject;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
public class ProjectNamePage extends WizardPage{

	private Text textProjectName;
	
	
	protected ProjectNamePage() {
		super("ProjectName","输入项目名称",null);
		setMessage("请输入新建估算项目的名称");
		setTitle("项目名称");
	}

	@Override
	public void createControl(Composite parent) {
		setPageComplete(false);
		
		Composite top = new Composite(parent, NONE);
		top.setLayout(new GridLayout(1,true));

		Label label=new Label(top, SWT.NONE);
		label.setText("项目名称");
		textProjectName=new Text(top,SWT.SINGLE|SWT.BORDER);
		textProjectName.setSize(100, 20);
		
		top.layout();
		setControl(top);
		setPageComplete(false);
	}
	
	
	public boolean canFlipToNextPage(){
		return true;
	}
	
	
	public String getProjectName(){
		return textProjectName.getText().trim();
	}
	
	

}
