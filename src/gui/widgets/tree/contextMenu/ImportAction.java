package gui.widgets.tree.contextMenu;

import data.database.ImportData;
import estimation.entity.EstimateNode;
import gui.GUI;
import gui.widgets.tree.TreeArea;

import java.io.File;
import java.sql.SQLException;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class ImportAction extends Action {
	public ImportAction(){
		super("导入");
	}
	
	@Override
	public void run() {
		TreeArea treeArea = GUI.getTreeArea();
		EstimateNode node=treeArea.getSelectedNode();
		Shell shell = treeArea.getShell();
		FileDialog fileDialog = new FileDialog(shell,SWT.SAVE);
		fileDialog.setFileName(node.getName()+".db3");
		String filePath = fileDialog.open();
		if(filePath == null)
			return;
		File file = new File(filePath);
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR);
		while(!file.exists())
		{
			messageBox.setMessage("文件不存在，请重新选择");
			messageBox.open();
			filePath = fileDialog.open();
			if(filePath == null)
				return;
			file = new File(filePath);
		}
		ImportData importData = new ImportData(filePath);
		try {
			importData.copyData(node.getId());
			treeArea.getTreeViewer().refresh();
		} catch (SQLException e) {
			messageBox.setMessage(e.getMessage());
			messageBox.open();
			e.printStackTrace();
		}
	}
}
