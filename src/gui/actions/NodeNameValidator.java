package gui.actions;

import gui.GUI;

import org.eclipse.jface.dialogs.IInputValidator;

public class NodeNameValidator implements IInputValidator {

	@Override
	public String isValid(String text) {
		String nodeName = text.trim();
		if (nodeName.length() == 0) {
			return "请输入节点名称";
		} else {
			// 判断是否存在同名兄弟节点
			if (GUI.getTreeArea().getSelectedNode().getParent()
					.isChildExist(nodeName) == true) {
				return "已存在名称为 " + nodeName + "  的节点!";
			} else {
				return null;
			}
		}
	}

}
