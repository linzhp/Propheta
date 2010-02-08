package gui.widgets.tree.contextMenu;

import java.io.File;

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
		File file = new File(filePath);
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR);
		while(file.exists())
		{
			messageBox.setMessage("文件已存在，请选择另一个文件名");
			messageBox.open();
			filePath = fileDialog.open();
			file = new File(filePath);
		}
		ExportData exportData = new ExportData(filePath);
		try {
			exportData.createSchema();
			exportData.copyData(node.getId());
		} catch (Exception e) {
			messageBox.setMessage(e.getMessage());
			messageBox.open();
			e.printStackTrace();
		}
	}
}
