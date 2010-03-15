package gui.actions;

import data.database.dataEntities.EstimateNode;
import gui.GUI;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;

public class RenameNodeAction extends Action {

	public RenameNodeAction() {
		super("重命名");
	}

	public void run() {
		EstimateNode node = GUI.getTreeArea().getSelectedNode();
		InputDialog input = new InputDialog(null, "请输入新的节点名称", "新节点名称", "",
				new NodeNameValidator());
		if (input.open() == Window.OK) {
			node.renameNode(input.getValue().trim());
			GUI.getTreeArea().getTreeViewer().refresh();
		}
	}

}
