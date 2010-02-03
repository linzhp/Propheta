package gui.widgets.tree.contextMenu;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import data.database.dataAccess.ExportData;

import estimation.entity.EstimateNode;
import gui.GUI;
import gui.widgets.tree.TreeArea;

public class ExportAction extends Action {
	public ExportAction(){
		super("导出");
	}
	
	@Override
	public void run() {
		TreeArea treeArea = GUI.getTreeArea();
		EstimateNode node=treeArea.getSelectedNode();
		Shell shell = treeArea.getShell();
		FileDialog fileDialog = new FileDialog(shell,SWT.SAVE);
		fileDialog.setFileName(node.getName()+".db3");
		String filePath = fileDialog.open();
		ExportData exportData = new ExportData(filePath);
		try {
			exportData.createSchema();
		} catch (Exception e) {
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR);
			messageBox.setMessage(e.getMessage());
			messageBox.open();
			e.printStackTrace();
		}
	}
}
