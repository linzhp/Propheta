package gui.actions;

import data.database.dataEntities.EstimateNode;
import gui.GUI;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

public class RemoveNodeAction extends Action {

	public RemoveNodeAction() {
		super("删除");
	}

	public void run() {
		EstimateNode node = GUI.getTreeArea().getSelectedNode();
		boolean result = MessageDialog.openConfirm(Display.getCurrent()
				.getActiveShell(), "确认", "确定要删除节点?");
		if (result == true) {
			node.getParent().removeChild(node);
			GUI.getTreeArea().getTreeViewer().refresh();
		}
	}
}
