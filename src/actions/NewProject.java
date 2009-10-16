package actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;

public class NewProject extends Action {
	public NewProject()
	{
		super("新建项目");
	}
	
	@Override
	public void run(){
		MessageDialog.openInformation(null, null, "haha");
	}
}
